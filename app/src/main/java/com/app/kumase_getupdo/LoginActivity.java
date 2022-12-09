package com.app.kumase_getupdo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity {

    private MaterialButton btnLogin;
    private TextInputEditText etEmail, etPassword;
    private TextView tvSignup, tvNoAccount;
    private FirebaseAuth mAuth;
    FirebaseUser fuser;
    String userid;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvSignup = findViewById(R.id.tvSignup);
        tvNoAccount = findViewById(R.id.tvNoAccount);
        mAuth = FirebaseAuth.getInstance();
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String _email = etEmail.getText().toString();
                final String _password = etPassword.getText().toString();
                if (TextUtils.isEmpty(_email)){
                    etEmail.setError("Please Enter Your Email Id...");
                    etEmail.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(_password)){
                    etPassword.setError("Please Enter Your Password ...");
                    etPassword.requestFocus();
                    return;
                }else {
                    loadingDialog.show();
                    mAuth.signInWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                fuser = mAuth.getCurrentUser();
                                userid = fuser.getUid();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        loadingDialog.dismiss();
                                        if (snapshot.child(userid).exists()){
                                                SessionManager.saveAutoLogin(LoginActivity.this, true);
                                                SessionManager.writeString(LoginActivity.this, "userId", snapshot.child(userid).child("userId").getValue(String.class));
                                                SessionManager.writeBoolean(LoginActivity.this, "IS_TEACHER", false);
                                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                                finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        loadingDialog.dismiss();
                                        Log.e("Login?onCancelled", error.getMessage());
                                    }
                                });
                            }else {
                                loadingDialog.dismiss();
                                Log.e("Login?task", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });
    }
}