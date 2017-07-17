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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static com.example.student.fitabs.ContactsActivity.selectedContactName;
import static com.example.student.fitabs.R.id.contact;


/**
 * Created by student on 17.11.7.
 */

public class ChatActivity extends AppCompatActivity {
    private ArrayList<String> chatMessages = new ArrayList<>();
    ListView chatWindow;
    EditText editMessage;
    TextView contactName;
    ChatMessage message = new ChatMessage();
    User user = new User();
    DBHandler dbHandler;
    ArrayList<String> usernames = new ArrayList<>();
    String userName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // initialization of variables and creating activity view
        editMessage = (EditText) findViewById(R.id.editMessage);
        chatWindow = (ListView) findViewById(R.id.chat);
        contactName = (TextView) findViewById(contact);
        contactName.setText(selectedContactName);

        dbHandler = new DBHandler(this);

        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor cursor = database.query(DBHandler.TABLE_USER,
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex(dbHandler.KEY_USERNAME);
            userName = cursor.getString(usernameIndex);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Table is empty", Toast.LENGTH_LONG);
            toast.show();
        }
        user.setName(userName);



        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById (R.id.bottom_navigation);

        //Display right icon
        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        //Define Bottom navigation view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            //Selected icon(item) - changes to the appropriate view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    //Contacts
                    case R.id.action_contacts:
                        startActivity(new Intent(ChatActivity.this, ContactsActivity.class));
                        break;

                    //Chat
                    case R.id.action_chat:
                        break;

                    //Calendar
                    case R.id.action_calendar:
                        startActivity(new Intent(ChatActivity.this, MyCalendarActivity.class));
                        break;

                    //Settings
                    case R.id.action_settings:
                        startActivity(new Intent(ChatActivity.this, UserSettingsActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void sendMsg(View view) {
        String msg = editMessage.getText().toString();
        message.setAuthor(user.getName());
        message.setMsg(msg);
        chatMessages.add(message.getAuthor() + ": " + message.getMsg()); // adding message to List of ChatMessage

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        // creating adapter for adding all messages to chat window
        chatWindow.setAdapter(adapter); // adding messages to chat window through adapter
        editMessage.setText("");
    }
}
