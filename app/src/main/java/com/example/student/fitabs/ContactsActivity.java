package com.example.student.fitabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ContactsActivity extends AppCompatActivity {
    private ArrayList<User> contacts;
    DBHandler dbHandler;
    ArrayList<Integer> id;
    ListView listContacts;
    static public String selectedContactName;
    static public String selectedContactNumber;
    User selectedContact = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contacts = new ArrayList<>();
        setContentView(R.layout.activity_contacts);
        dbHandler = new DBHandler(this);
        id = new ArrayList<>();
        readFromDatabase();
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
                        break;
                    //Exercise
                    case R.id.action_exercise:
                        startActivity(new Intent(ContactsActivity.this, GroupsOfExercisesActivity.class));
                        break;
                    //Calendar
                    case R.id.action_calendar:
                        startActivity(new Intent(ContactsActivity.this, MyCalendarActivity.class));
                        break;
                    //Settings
                    case R.id.action_settings:
                        startActivity(new Intent(ContactsActivity.this, UserSettingsActivity.class));
                        break;
                }
                return true;
            }
        });
        // Create an {@link ContactsAdapter}, whose data source is a list of {@link User}s. The
        // adapter knows how to create list items for each item in the list.
        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_contacts.xml layout file.
        listContacts = (ListView) findViewById(R.id.contacts);
        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listContacts.setAdapter(adapter);
        //Selects view to set listener on it
        // Set a click listener on that View
        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                SQLiteDatabase database = dbHandler.getReadableDatabase();
                Cursor cursor = database.query(DBHandler.TABLE_USER,
                        null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    //converts cicked item postion id from long to int
                    int i = (int) arg3;
                    //gets selectedContact object from contacts ArrayList by selectedID
                    selectedContact = contacts.get(i);
                    //initialize selectedContactName string as selectedContact name
                    selectedContactName = selectedContact.getName();
                    selectedContactNumber = selectedContact.getTelnumber();
                    // Create a new intent to open the {@link FamilyActivity}
                    Intent chatIntent = new Intent(ContactsActivity.this, ChatActivity.class);
                    // Start the new activity
                    startActivity(chatIntent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noUserName), Toast.LENGTH_LONG);
                    toast.show();
                }

                cursor.close();
                database.close();
            }
        });
    }

    // loads previously entered data from database table TABLE_CONTACTS
    public void readFromDatabase() {
        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor cursor = database.query(DBHandler.TABLE_CONTACTS,
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            contacts = (ArrayList<User>) dbHandler.getAllContacts();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Table is empty", Toast.LENGTH_LONG);
            toast.show();
        }
        cursor.close();
        dbHandler.close();
    }

    public void addContact(View view) {
        Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
        startActivity(intent);
    }
}