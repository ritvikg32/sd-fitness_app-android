package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FormActivity extends AppCompatActivity {

    private TextView nameField;
    private TextView ageField;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        nameField = findViewById(R.id.name_entry);
        ageField = findViewById(R.id.name_entry);
        submitBtn = findViewById(R.id.form_submit_btn);


        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("NAME_VAL",nameField.getText().toString());
                intent.putExtra("AGE_VAL",ageField.getText().toString());

                setResult(50, intent);
                finish();
            }
        });
    }
}