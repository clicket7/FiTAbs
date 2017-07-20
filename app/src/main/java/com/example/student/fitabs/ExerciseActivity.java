package com.example.student.fitabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ExerciseActivity extends AppCompatActivity {
    private TextView label, description;
    private ImageView image;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        label = (TextView) findViewById(R.id.exerciseName);
        description = (TextView) findViewById(R.id.textDescription);
        image = (ImageView) findViewById(R.id.imageExercise);

        dbHandler = new DBHandler(this);
        label.setText(ExercisesActivity.selectedExercise);
        String d = dbHandler.getExerciseDescription(ExercisesActivity.selectedExercise);
        description.setText(d);
        String imageName = dbHandler.getExerciseImage(ExercisesActivity.selectedExercise);
        int res = getResources().getIdentifier(imageName, "drawable", this.getPackageName());;
        image.setImageResource(res);
    }

    public void backToExer(View view) {
        Intent intent = new Intent(ExerciseActivity.this, ExercisesActivity.class);
        startActivity(intent);
    }
}
