package com.app.kumase_getupdo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.kumase_getupdo.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.api.RetrofitClient;
import com.jbs.general.model.response.singup.MainResponseSignUp;
import com.jbs.general.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private String loginForm = "regular";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("199439339193-ajvmeoird1nioivrelad21t8966io0j8.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //initialize views and variables
        initialization();
    }

    private void initialization() {
        //Manage Password Visibility
        managePasswordEditText(binding.etPassword);

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });

        binding.cardGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        binding.mbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClickDisabled()) {
                    return;
                }
                hideKeyboard(view);

                if (isFieldsValid()) {
                    callLoginApi(binding.etEmail.getText().toString().trim(),
                            binding.etPassword.getText().toString().trim(),
                            loginForm);
                }
            }
        });
    }

    private void callLoginApi(String email, String password, String loginForm) {
        if (isNetworkAvailable()) {
            showLoader();
            RetrofitClient.getInstance().getApi().callLogin(email,
                    password,
                    loginForm).enqueue(new Callback<MainResponseSignUp>() {
                @Override
                public void onResponse(@NonNull Call<MainResponseSignUp> call, @NonNull Response<MainResponseSignUp> response) {
                    hideLoader();
                    if (response.body() == null) {
                        showSnackbarShort(getString(R.string.error_something_went_wrong));
                        return;
                    }
                    MainResponseSignUp mainResponseSignUp = response.body();
                    if (mainResponseSignUp.isSuccess()) {
                        preferenceUtils.saveAutoLogin(true);
                        preferenceUtils.setString(Constants.PreferenceKeys.USER_ID, mainResponseSignUp.getData().getId());
                        preferenceUtils.setString(Constants.PreferenceKeys.USER_DATA, new Gson().toJson(mainResponseSignUp.getData()));

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        showSnackbarShort(getString(R.string.error_something_went_wrong) + "\n" + mainResponseSignUp.getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MainResponseSignUp> call, @NonNull Throwable t) {
                    hideLoader();
                    Timber.tag("onFailure?callLogin").e(t);
                }
            });
        } else {
            hideLoader();
            showSnackbarShort(getString(R.string.error_please_connect_to_internet));
        }
    }

    public boolean isFieldsValid() {
        if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            showSnackbarShort("Please Enter valid Email");
            binding.etEmail.requestFocus();
            binding.etEmail.setSelection(binding.etEmail.getText().toString().length());
            return false;
        }
        if (!isEmailValid(binding.etEmail.getText().toString().trim())) {
            showSnackbarShort("Please Enter valid Email");
            binding.etEmail.requestFocus();
            binding.etEmail.setSelection(binding.etEmail.getText().toString().length());
            return false;
        }
        if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            showSnackbarShort("Please Enter valid Password");
            binding.etPassword.requestFocus();
            binding.etPassword.setSelection(binding.etPassword.getText().toString().length());
            return false;
        }
        /*if (binding.etPassword.getText().toString().trim().length() < 6) {
            showSnackbarShort("Please must be at least 6 characters long");
            binding.etPassword.requestFocus();
            binding.etPassword.setSelection(binding.etPassword.getText().toString().length());
            return false;
        }*/
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            loginForm = "google";
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e("GoogleId", account.getEmail() + "\n" + account.getDisplayName() + "\n" + account.getFamilyName() );
                callLoginApi(account.getEmail(), "", loginForm);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("GoogleSignin", "Google sign in failed", e);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}