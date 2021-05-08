package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signinPtr;
    private FirebaseAuth mAuth;
    private Button signupBtn;
    private TextView emailField;
    private TextView passwordField;
    private ProgressBar signupProgress;
    private FirebaseUser mFirebaseUser;

    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    CollectionReference cities = db.collection("users");
    Intent form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        form = new Intent(this, FormActivity.class);

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
                            mFirebaseUser = mAuth.getCurrentUser();
                            signupProgress.setVisibility(View.INVISIBLE);
                            startActivityForResult(form, 50);
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

    private void addUsertoFireStore(String uid, String email, Intent data){

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(data.getStringExtra("NAME_VAL"))
                .build();

        mFirebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Name updated",Toast.LENGTH_SHORT).show();
                    }
                });

        Map<String, Object> newUser = new HashMap<>();
        newUser.put("name",data.getStringExtra("NAME_VAL"));
        newUser.put("age",data.getStringExtra("AGE_VAL"));
        Intent home = new Intent(this, HomeActivity.class);

    db.collection("users").document(mFirebaseUser.getUid())
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        home.putExtra("USER_NAME",data.getStringExtra("NAME_VAL"));
                        startActivity(home);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("upload","Something went wrong "+e.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 50 || resultCode==50){
            addUsertoFireStore(mFirebaseUser.getUid(), mFirebaseUser.getEmail(), data);
        }
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