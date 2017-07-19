package com.example.student.fitabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by student on 17.18.7.
 */

public class ExercisesActivity extends AppCompatActivity {
    private ArrayList<String> exercises;
    private ListView listExercises;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        TextView label = (TextView) findViewById(R.id.groupName);
        label.setText(GroupsOfExercisesActivity.selectedOption);
        exercises = new ArrayList<>();
        listExercises = (ListView) findViewById(R.id.exercises);
        dbHandler = new DBHandler(this);
        readFromDatabase();

        StringAdapter adapter = new StringAdapter(this, exercises);
        listExercises.setAdapter(adapter);


    }

    public void readFromDatabase() {
        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor cursor = database.query(DBHandler.TABLE_EXERCISES,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Table is empty", Toast.LENGTH_LONG);
            toast.show();
        }

        cursor.close();
        dbHandler.close();
    }

    public void backToGroups(View view) {
        Intent intent = new Intent(ExercisesActivity.this, GroupsOfExercisesActivity.class);
        startActivity(intent);
    }
}
