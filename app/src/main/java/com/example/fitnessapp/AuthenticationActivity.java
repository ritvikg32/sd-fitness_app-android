package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button signinButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        signinButton = findViewById(R.id.signin_btn);
        signupButton = findViewById(R.id.signup_btn);

        signinButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent homeIntent = new Intent(this, MainActivity.class);
//            startActivity(homeIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_btn: Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
            break;
            case R.id.signup_btn:  Intent signup = new Intent(this, SignupActivity.class);
                startActivity(signup);
            break;


        }


    }
}