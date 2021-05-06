package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signinPtr;
    private FirebaseAuth mAuth;
    private Button signupBtn;
    private TextView emailField;
    private TextView passwordField;
    private ProgressBar signupProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        signinPtr = findViewById(R.id.txt_signin_ptr);
        signupBtn = findViewById(R.id.signup_btn);
        emailField = findViewById(R.id.editTextTextEmailAddress);
        passwordField = findViewById(R.id.editTextTextPassword);
        signupProgress = findViewById(R.id.signup_progress);

        signinPtr.setOnClickListener(this);
        signupBtn.setOnClickListener(this);

        signupBtn.setEnabled(false);

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0){
                    if(passwordField.getText().length()>=6)
                        signupBtn.setEnabled(true);
                }
                else {
                    emailField.setError(getResources().getString(R.string.email_warning));
                    signupBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>=6){
                    if(emailField.getText().length()>0)
                        signupBtn.setEnabled(true);
                }
                else {
                    passwordField.setError(getResources().getString(R.string.password_warning));
                    signupBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Authentication", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            signupProgress.setVisibility(View.INVISIBLE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Authentication", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            signupProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.txt_signin_ptr: intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.signup_btn:
                signupProgress.setVisibility(View.VISIBLE);
                createUser(emailField.getText().toString(), passwordField.getText().toString());
            break;
        }
    }
}