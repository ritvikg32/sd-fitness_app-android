package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    //private StorageReference storageReference;

    private List<Weights> weightsListFB;
    private RecyclerView wtsRV;
    private wktRvAdapter adapter;
    private String workoutName;

    private CollectionReference collectionReference=db.collection("weights");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_selection);

        Intent cardIntent = getIntent();
        workoutName = cardIntent.getStringExtra("WORKOUT_NAME");
        wktRvAdapter adapter;

        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        weightsListFB=new ArrayList<>();
        Log.d("WORKOUTACTI", "onCreate: "+user.getEmail());
        wtsRV=findViewById(R.id.wkt_selection_rv);
        wtsRV.setHasFixedSize(true);
        wtsRV.setLayoutManager(new LinearLayoutManager(this));


        getWorkoutList();
    }

    private void getWorkoutList(){
        db.collection(workoutName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("download", document.getId().toString() + " => " + document.getData().toString());
                                weightsListFB.add(document.toObject(Weights.class));
//                                Weights w = new Weights();
//                                w.setName(document.getData().get("name").toString());
//                                w.setimage(document.getData().get("image").toString());
//                                weightsListFB.add(w);

                            }
                            adapter = new wktRvAdapter(weightsListFB);
                            wtsRV.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Log.d("download", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String, Weights> map = new HashMap<>();
        //Log.d("TAG3", "onStart: "+collectionReference.getId());
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("Result",task.getResult().toString());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData().toString());

                                weightsListFB.add(document.toObject(Weights.class));


                            }
                            Log.d("List",weightsListFB.toString());

                            adapter = new wktRvAdapter(weightsListFB);
                            wtsRV.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } else {
                            Log.d("TAG2", "Error getting documents: ", task.getException());
                        }
                    }
                });

//
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if(!queryDocumentSnapshots.isEmpty()){
//                            for(QueryDocumentSnapshot journals:queryDocumentSnapshots){
//                                Weights journal =journals.toObject(Weights.class);
//                                weightsListFB.add(journal);
//                            }
//
//                            wktRvAdapter = new wktRvAdapter(weightsListFB);
//                            wtsRV.setAdapter(wktRvAdapter);
//                            wktRvAdapter.notifyDataSetChanged();
//                        }
//                        else{
//                            Toast.makeText(WorkoutSelectionActivity.this, "Khali hai bro", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });


    }
}