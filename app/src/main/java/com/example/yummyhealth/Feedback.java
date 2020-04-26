package com.example.yummyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.yummyhealth.Model.FeedbackModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {

    RatingBar foodrating;
    RatingBar riderrating;
    RatingBar apprating;
    EditText editText;
    Button submit;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");

        editText = findViewById(R.id.editText);
        foodrating = findViewById(R.id.ratingBar);
        riderrating = findViewById(R.id.ratingBar2);
        apprating = findViewById(R.id.ratingBar3);
        submit = findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addfeedback();
            }
        });


    }

    private void addfeedback(){
        String feedback = editText.getText().toString();
        Float appr = apprating.getRating();
        Float foodr = foodrating.getRating();
        Float riderr = riderrating.getRating();

        String id = databaseReference.push().getKey();
        FeedbackModel feedbackModel = new FeedbackModel(id, feedback, foodr, riderr, appr);
        databaseReference.child(id).setValue(feedbackModel);

        Intent intent = new Intent(Feedback.this,MainActivity.class);
        startActivity(intent);
    }
}
