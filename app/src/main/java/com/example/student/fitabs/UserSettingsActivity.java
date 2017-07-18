package com.example.student.fitabs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;


public class UserSettingsActivity extends AppCompatActivity {

    EditText editUsername, editNumber;
    CheckBox checkStatus;
    DBHandler dbHandler;
    Integer activeId;
    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<String> telNumber = new ArrayList<>();
    ArrayList<Boolean> isTrener = new ArrayList<>();
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editNumber = (EditText) findViewById(R.id.editTelNumber);
        checkStatus = (CheckBox) findViewById(R.id.checkBoxStatus);


        dbHandler = new DBHandler(this);

        editUsername.setText(user.getName());
        editNumber.setText(user.getTelnumber());
        checkStatus.setChecked(user.getStatus());


        //whole block loads previously entered data from database table TABLE_USER
        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor cursor = database.query(DBHandler.TABLE_USER,
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHandler.KEY_ID);
            int usernameIndex = cursor.getColumnIndex(dbHandler.KEY_USERNAME);
            int telNumberIndex = cursor.getColumnIndex(dbHandler.KEY_TEL_NUMBER);
            int statusIndex = cursor.getColumnIndex(dbHandler.KEY_IS_TRENER);
            do {
                id.add(cursor.getInt(idIndex));
                usernames.add(cursor.getString(usernameIndex));
                telNumber.add(cursor.getString(telNumberIndex));
                isTrener.add(cursor.getInt(statusIndex) > 0);

            } while (cursor.moveToNext());

        } else{
            Toast toast = Toast.makeText(getApplicationContext(), "Table is empty", Toast.LENGTH_LONG);
            toast.show();
        }
        if (!id.isEmpty()) {
            editUsername.setText(usernames.get(0));
            editNumber.setText(telNumber.get(0) );
            checkStatus.setChecked(isTrener.get(0));
            activeId = id.get(0);
        }
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
        if (editUsername.getText().toString().equals("") || editNumber.getText().toString().equals("") ) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noValueEntered), Toast.LENGTH_LONG);
            toast.show();
        }
        //creates user with entered info
        else {
            dbHandler.deleteUser();
            user.setName(editUsername.getText().toString());
            user.setTelnumber(editNumber.getText().toString());
            user.setStatus(checkStatus.isChecked());

            SQLiteDatabase database = dbHandler.getWritableDatabase();

            ContentValues contentValues = new ContentValues();


            contentValues.put(DBHandler.KEY_USERNAME, user.getName());
            contentValues.put(DBHandler.KEY_TEL_NUMBER, user.getTelnumber());
            contentValues.put(DBHandler.KEY_IS_TRENER, user.getStatus());

            database.insert(DBHandler.TABLE_USER, null, contentValues);

            Toast toast = Toast.makeText (getApplicationContext(), getString(R.string.saveSucc), Toast.LENGTH_LONG);
            toast.show();

            startActivity(new Intent(UserSettingsActivity.this, ContactsActivity.class));

        }

        dbHandler.close();
    }
}
