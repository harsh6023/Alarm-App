package com.app.kumase_getupdo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.kumase_getupdo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private TextView tvLogin, tvHaveAccount;
    private TextInputEditText etUserName, etFullName, etEmail, etPassword, etConfirmPassword;
    private MaterialButton btnSignup;
    private CheckBox cbTermCondition;
    private Dialog loadingDialog;
    private String userid;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseUser fuser;
    private User userData = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvLogin = findViewById(R.id.tvLogin);
        tvHaveAccount = findViewById(R.id.tvHaveAccount);
        etUserName = findViewById(R.id.etUserName);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
        cbTermCondition = findViewById(R.id.cbTermCondition);
        mAuth = FirebaseAuth.getInstance();

        /*Initialize Loading Dialog*/
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        String user_pswrd = etPassword.getText().toString();
        String user_confirpswrd = etConfirmPassword.getText().toString();
        final String user_email = etEmail.getText().toString();
        final String user_name = etUserName.getText().toString();
        final String full_name = etFullName.getText().toString();

        if (TextUtils.isEmpty(user_name)) {
            etUserName.setError("Please Enter Your User Name...");
            etUserName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(full_name)) {
            etFullName.setError("Please Enter Your Full Name...");
            etFullName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(user_email)) {
            etEmail.setError("Please Enter Your Email Id...");
            etEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            etEmail.setError("Please Enter a Valid Email ...");
            etEmail.requestFocus();
            return;
        } else if (TextUtils.isEmpty(user_pswrd)) {
            etPassword.setError("Please Enter Your Password ...");
            etPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(user_confirpswrd)) {
            etConfirmPassword.setError("Please ReEnter Your Password ... ");
            etConfirmPassword.requestFocus();
            return;
        } else if (!user_pswrd.equals(user_confirpswrd)) {
            etConfirmPassword.setError("Password is Not match ...");
            etConfirmPassword.requestFocus();
            return;
        } else {
            loadingDialog.show();
            mAuth.createUserWithEmailAndPassword(user_email, user_pswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        fuser = mAuth.getCurrentUser();
                        userid = fuser.getUid();
                        reference = FirebaseDatabase.getInstance().getReference("User").child(fuser.getUid());
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                getValues();
                                reference.setValue(userData);
                                loadingDialog.dismiss();

                                Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                                SessionManager.saveAutoLogin(SignUpActivity.this, true);
                                SessionManager.writeString(SignUpActivity.this, "userId", userid);
                                startActivity(new Intent(SignUpActivity.this, DashboardActivity.class));
                                finish();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                loadingDialog.dismiss();
                                Log.e("Register?onCancelled", error.getMessage());
                            }
                        });
                    } else {
                        loadingDialog.dismiss();
                        Log.e("Register?task", task.getException().getMessage());
                    }
                }
            });
        }
    }

    private void getValues() {
        userData.setUserId(userid);
        userData.setUserName(etUserName.getText().toString());
        userData.setFullName(etFullName.getText().toString());
        userData.setUserEmail(etEmail.getText().toString());
        userData.setUserPassword(etPassword.getText().toString());
    }
}