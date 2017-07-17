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
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import static android.R.attr.id;
import static com.example.student.fitabs.R.id.editUsername;
public class ContactsActivity extends AppCompatActivity {
    private ArrayList<User> contacts;
    DBHandler dbHandler;
    ArrayList<Integer> id;
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<String> telNumber = new ArrayList<>();
    ArrayList<Boolean> isTrener = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contacts = new ArrayList<User>();
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
                    //Chat
                    case R.id.action_chat:
                        startActivity(new Intent(ContactsActivity.this, ChatActivity.class));
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
        ListView listView = (ListView) findViewById(R.id.contacts);
        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
        //Selects view to set listener on it
        ListView listContacts = (ListView) findViewById(R.id.contacts);
        // Set a click listener on that View
        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                // Create a new intent to open the {@link FamilyActivity}
                Intent chatIntent = new Intent(ContactsActivity.this, ChatActivity.class);
                // Start the new activity
                startActivity(chatIntent);
            }
        });
    }
    // loads previously entered data from database table TABLE_CONTACTS
    public void readFromDatabase() {
        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor cursor = database.query(DBHandler.TABLE_CONTACTS,
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHandler.KEY_C_ID);
            int usernameIndex = cursor.getColumnIndex(dbHandler.KEY_C_USERNAME);
            int telNumberIndex = cursor.getColumnIndex(dbHandler.KEY_C_TEL_NUMBER);
            int statusIndex = cursor.getColumnIndex(dbHandler.KEY_C_IS_TRENER);
            do {
                contacts.add(new User(cursor.getString(usernameIndex), cursor.getString(telNumberIndex), cursor.getInt(statusIndex) > 0));
            } while (cursor.moveToNext());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Table is empty", Toast.LENGTH_LONG);
            toast.show();
        }
        dbHandler.close();
    }
    public void addContact(View view) {
        Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
        startActivity(intent);
    }
    public void deleteContact(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "It's not finished yet!", Toast.LENGTH_LONG);
        toast.show();
    }
}