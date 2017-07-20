package com.example.student.fitabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity {
    private ArrayList<String> exercises;
    private DBHandler dbHandler;
    static public String selectedExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        TextView label = (TextView) findViewById(R.id.groupName);
        label.setText(GroupsOfExercisesActivity.selectedOption);
        exercises = new ArrayList<>();
        ListView listExercises = (ListView) findViewById(R.id.exercises);
        dbHandler = new DBHandler(this);
        readFromDatabase();

        StringAdapter adapter = new StringAdapter(this, exercises);
        listExercises.setAdapter(adapter);

        listExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
                SQLiteDatabase database = dbHandler.getReadableDatabase();
                Cursor cursor = database.query(DBHandler.TABLE_EXERCISES,
                        null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    //converts cicked item postion id from long to int
                    int i = (int) arg3;
                    //gets selectedContact object from contacts ArrayList by selectedID
                    selectedExercise = exercises.get(i);
                    // Create a new intent to open the {@link FamilyActivity}
                    Intent intent = new Intent(ExercisesActivity.this, ExerciseActivity.class);
                    // Start the new activity
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error during loading exercises!", Toast.LENGTH_LONG);
                    toast.show();
                }

                cursor.close();
                dbHandler.close();
            }
        });


    }

    public void readFromDatabase() {
        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor cursor = database.query(DBHandler.TABLE_EXERCISES, new String[]{DBHandler.KEY_EX_NAME}, DBHandler.KEY_EX_TYPE + "=?",
                new String[]{GroupsOfExercisesActivity.selectedOption}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                exercises.add(cursor.getString(0));
            } while (cursor.moveToNext());

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
