package com.example.student.fitabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 17.18.7.
 */

public class GroupsOfExercisesActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> groups;
    private DBHandler dbHandler;
    static public String selectedOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_of_exercises);

        dbHandler = new DBHandler(this);
        listView = (ListView) findViewById(R.id.exerciseList);
        groups = new ArrayList<>();

        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //Display right icon
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        //Define Bottom navigation view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //Selected icon(item) - changes to the appropriate view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //Contacts
                    case R.id.action_contacts:
                        startActivity(new Intent(GroupsOfExercisesActivity.this, ContactsActivity.class));
                        break;
                    //Exercise
                    case R.id.action_exercise:
                        break;
                    //Calendar
                    case R.id.action_calendar:
                        startActivity(new Intent(GroupsOfExercisesActivity.this, MyCalendarActivity.class));
                        break;
                    //Settings
                    case R.id.action_settings:
                        startActivity(new Intent(GroupsOfExercisesActivity.this, UserSettingsActivity.class));
                        break;
                }
                return true;
            }
        });

        StringAdapter adapter = new StringAdapter(this, groups);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
                SQLiteDatabase database = dbHandler.getReadableDatabase();
                Cursor cursor = database.query(DBHandler.TABLE_USER,
                        null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    //converts cicked item postion id from long to int
                    int i = (int) arg3;
                    //gets selectedContact object from contacts ArrayList by selectedID
                    selectedOption = groups.get(i);
                    // Create a new intent to open the {@link FamilyActivity}
                    Intent intent = new Intent(GroupsOfExercisesActivity.this, ExercisesActivity.class);
                    // Start the new activity
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error during loading exercises!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

}
