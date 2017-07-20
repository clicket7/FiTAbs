package com.example.student.fitabs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;


public class UserSettingsActivity extends AppCompatActivity {

    EditText editUsername, editNumber, editIp;
    CheckBox checkStatus;
    DBHandler dbHandler;
    ArrayList<Integer> id = new ArrayList<>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editNumber = (EditText) findViewById(R.id.editTelNumber);
        checkStatus = (CheckBox) findViewById(R.id.checkBoxStatus);
        editIp = (EditText) findViewById(R.id.editIP);

        dbHandler = new DBHandler(this);

        user = dbHandler.getUser();
        editUsername.setText(user.getName());
        editNumber.setText(user.getTelnumber());
        checkStatus.setChecked(user.getStatus());

        if (user.getName().equals("") || user.getTelnumber().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Need to set up settings", Toast.LENGTH_LONG);
            toast.show();
        }

        editIp.setText(dbHandler.getIP());

        dbHandler.close();

        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById (R.id.bottom_navigation);

        //Display right icon
        bottomNavigationView.getMenu().getItem(3).setChecked(true);

        //Define Bottom navigation view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            //Selected icon(item) - changes to the appropriate view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    //Contacts
                    case R.id.action_contacts:
                        startActivity(new Intent(UserSettingsActivity.this, ContactsActivity.class));
                        break;

                    //Exercise
                    case R.id.action_exercise:
                        startActivity(new Intent(UserSettingsActivity.this, GroupsOfExercisesActivity.class));
                        break;

                    //Calendar
                    case R.id.action_calendar:
                        startActivity(new Intent(UserSettingsActivity.this, MyCalendarActivity.class));
                        break;

                    //Settings
                    case R.id.action_settings:
                        break;
                }
                return true;
            }
        });
    }

    //saves user's entered info
    public void saveUser(View view) {
        //gives error message is user didn't fill in all fields
        if (editUsername.getText().toString().equals("") || editNumber.getText().toString().equals("") || editIp.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noValueEntered), Toast.LENGTH_LONG);
            toast.show();
        }
        //creates user with entered info
        else {
            dbHandler.deleteUser();
            dbHandler.deleteIP();
            user.setName(editUsername.getText().toString());
            user.setTelnumber(editNumber.getText().toString());
            user.setStatus(checkStatus.isChecked());

            dbHandler.addUser(user);
            dbHandler.addIP(editIp.getText().toString());

            Toast toast = Toast.makeText (getApplicationContext(), getString(R.string.saveSucc), Toast.LENGTH_LONG);
            toast.show();

            startActivity(new Intent(UserSettingsActivity.this, ContactsActivity.class));
        }

        dbHandler.close();
    }
}
