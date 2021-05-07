package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView signupPtr;
    private FirebaseAuth mAuth;
    private Button signinBtn;
    private ProgressBar progressBar;
    private AppCompatActivity reference;
    private TextView emailField;
    private TextView pwdField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reference = this;
        mAuth = FirebaseAuth.getInstance();

        signupPtr = findViewById(R.id.txt_signup_ptr);
        signinBtn = findViewById(R.id.signin_btn);
        progressBar = findViewById(R.id.signin_progress);
        emailField = findViewById(R.id.editTextTextEmailAddress);
        pwdField = findViewById(R.id.editTextTextPassword);

        signinBtn.setOnClickListener(this);
        signupPtr.setOnClickListener(this);


    }

    private void signin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Authentication", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent main  = new Intent(reference, MainActivity.class);
                            startActivity(main);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Authentication", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.txt_signup_ptr: intent = new Intent(this, SignupActivity.class);

                startActivity(intent);
                break;
            case R.id.signin_btn:
                progressBar.setVisibility(View.VISIBLE);
                signin(emailField.getText().toString(),pwdField.getText().toString());
                intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            break;
        }
    }
}