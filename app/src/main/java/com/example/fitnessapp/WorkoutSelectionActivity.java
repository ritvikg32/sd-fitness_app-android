package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    //private StorageReference storageReference;

    private List<Weights> weightsListFB;
    private RecyclerView wtsRV;
    private wktRvAdapter wktRvAdapter;

    private CollectionReference collectionReference=db.collection("weights");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_selection);

        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        weightsListFB=new ArrayList<>();
        Log.d("WORKOUTACTI", "onCreate: "+user.getEmail());
        wtsRV=findViewById(R.id.wkt_selection_rv);
        wtsRV.setHasFixedSize(true);
        wtsRV.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d("TAG3", "onStart: "+collectionReference.getId());
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                weightsListFB.add(document.toObject(Weights.class));

                            }

                                                       wktRvAdapter = new wktRvAdapter(weightsListFB);
                            wtsRV.setAdapter(wktRvAdapter);
                            wktRvAdapter.notifyDataSetChanged();

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