package com.example.student.fitabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }

    public void backToExer(View view) {
        Intent intent = new Intent(ExerciseActivity.this, ExercisesActivity.class);
        startActivity(intent);
    }
}
