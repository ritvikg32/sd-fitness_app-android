package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private NumberPicker numPicker;
    private FirebaseAuth mAuth;
    private TextView username;
    private ImageView userdp;
    private ImageView greetingText;
    private GridLayout workoutCardGrid;
    private FirebaseUser mcurrentUser;

    private CardView yogaCard;
    private CardView weightsCard;
    private CardView cardioCard;
    private CardView dietCard;
    private String newUserusername;

    private FirebaseFirestore db =FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        Intent formData = new Intent();
        try {
            newUserusername = formData.getStringExtra("USER_NAME");
        }
        catch (Exception e){
            Log.d("INTENT",formData.getStringExtra("USER_NAME"));
        }


        mcurrentUser = mAuth.getCurrentUser();

        numPicker = findViewById(R.id.num_pick);
        username = findViewById(R.id.username_txt);
        userdp = findViewById(R.id.user_dp_img);
        yogaCard = findViewById(R.id.yoga_card);
        weightsCard = findViewById(R.id.weights_card);
        cardioCard = findViewById(R.id.cardio_card);
        dietCard = findViewById(R.id.diet_card);
        workoutCardGrid = findViewById(R.id.workout_grid);

        numPicker.setMinValue(0);
        numPicker.setMaxValue(6);

        String[] numPick = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        numPicker.setDisplayedValues(numPick);

        updateGUI();

        yogaCard.setOnClickListener(this);
        weightsCard.setOnClickListener(this);
        cardioCard.setOnClickListener(this);
        dietCard.setOnClickListener(this);

        workoutCardGrid.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.yoga_card: Intent intent = new Intent(HomeActivity.this, WorkoutSelectionActivity.class);
                        Toast.makeText(HomeActivity.this,"adlfksjf",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    break;
                }
            }
        });


        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });
    }

    private void getCalories(){
        DocumentReference docRef = db.collection("users").document(mcurrentUser.getUid().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.d("download","Document data: "+document.getData());
                    }
                }
            }
        });

    }





    private void updateGUI(){


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url

            String email = user.getEmail();
            String name = user.getDisplayName();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            if(name.length()!=0)
                username.setText(name);


            if(photoUrl!=null)
                Glide.with(this).load(photoUrl).into(userdp);


        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, WorkoutSelectionActivity.class);
        switch (v.getId()){
            case R.id.yoga_card: ;
                Toast.makeText(HomeActivity.this,"adlfksjf",Toast.LENGTH_LONG).show();
                intent.putExtra("WORKOUT_NAME","yoga");
                startActivity(intent);
                break;
            case R.id.weights_card: intent.putExtra("WORKOUT_NAME","weights");
            startActivity(intent);
            break;

            case R.id.cardio_card: intent.putExtra("WORKOUT_NAME","cardio");
            startActivity(intent);
            break;
        }
    }
}