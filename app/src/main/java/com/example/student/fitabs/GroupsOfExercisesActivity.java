package com.example.student.fitabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by student on 17.18.7.
 */

public class GroupsOfExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_of_exercises);

        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //Display right icon
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
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
                    //Chat
                    case R.id.action_chat:
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
    }

}
