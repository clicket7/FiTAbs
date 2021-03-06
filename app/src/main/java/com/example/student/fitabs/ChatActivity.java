package com.example.student.fitabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import static com.example.student.fitabs.ContactsActivity.selectedContactNumber;

public class ChatActivity extends AppCompatActivity implements Runnable {
    public ArrayList<String> chatMessages;
    private EditText editMessage;
    private ClientListAdapter mAdapter;

    private String host;
    Thread t;

    DBHandler dbHandler;
    User user;
    Button delete, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // initialization of variables and creating activity view

        dbHandler = new DBHandler(this);
        user = dbHandler.getUser();
        host = dbHandler.getIP();

        chatMessages = dbHandler.getAllChatMessages(ContactsActivity.selectedContactNumber);

        TextView nameTo = (TextView) findViewById(R.id.contact);
        nameTo.setText(ContactsActivity.selectedContactName);

        editMessage = (EditText) findViewById(R.id.editMessage);
        ListView chatWindow = (ListView) findViewById(R.id.chat);

        mAdapter = new ClientListAdapter(this, chatMessages);
        chatWindow.setAdapter(mAdapter);

        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //Display right icon
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        //Define Bottom navigation view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //Selected icon(item) - changes to the appropriate view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Client client = new Client();
                client.execute(host, user.getName() + " is offline");
                dbHandler.saveAllChatMessages(chatMessages, ContactsActivity.selectedContactNumber);
                switch (item.getItemId()) {
                    //Contacts
                    case R.id.action_back:
                        startActivity(new Intent(ChatActivity.this, ContactsActivity.class));
                        break;
                    //Exercise
                    case R.id.action_exercise:
                        startActivity(new Intent(ChatActivity.this, GroupsOfExercisesActivity.class));
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

        //finds and selects  deleteButton view
        delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                dbHandler.deleteChatMessages(ContactsActivity.selectedContactNumber);
                dbHandler.deleteContact(ContactsActivity.selectedContactNumber);
                startActivity(new Intent(ChatActivity.this, ContactsActivity.class));
            }
        });


        //Update contacts information
        update = (Button) findViewById(R.id.editButton);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                dbHandler.saveAllChatMessages(chatMessages, ContactsActivity.selectedContactNumber);
                //When Update button is clicked - transfers to update view
                startActivity(new Intent(ChatActivity.this, UpdateContactsActivity.class));
            }
        });

        t = new Thread(this);
        t.start();
        mAdapter.notifyDataSetChanged();

        dbHandler.close();
    }

    public void sendMsg(View view) {
        dbHandler = new DBHandler(this);
        user = dbHandler.getUser();
        String msg = editMessage.getText().toString();
        chatMessages.add(user.getName() + ": " + msg);
        mAdapter.notifyDataSetChanged();

        Client client = new Client();
        client.execute(host, user.getName() + ": " + msg);

        editMessage.setText("");
        dbHandler.close();

    }

    @Override
    public void run() {
        try {
            int port = 9999;
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());

            os.writeBytes(user.getTelnumber() + "\n");
            os.writeBytes(selectedContactNumber + "\n");
            os.writeBytes(user.getName() + " is online\n");
            Log.e("Send", "ok");

            String msg;
            while ((msg = in.readLine()) != null) {
                Log.e("I", msg);
                chatMessages.add(msg);
                runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Client extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            String ip = params[0];
            String data = params[1];
            Socket socket;
            try {
                socket = new Socket(ip, 9999);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                output.writeBytes(user.getTelnumber() + "\n");
                output.writeBytes(selectedContactNumber + "\n");
                output.writeBytes(data + "\n");
                Log.e("Send", data);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return 1;
        }
    }
}
