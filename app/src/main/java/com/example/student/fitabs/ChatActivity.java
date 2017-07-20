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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static com.example.student.fitabs.ContactsActivity.selectedContactName;
import static com.example.student.fitabs.ContactsActivity.selectedContactNumber;


/**
 * Created by student on 17.11.7.
 */

public class ChatActivity extends AppCompatActivity implements Runnable {
    public ArrayList<String> chatMessages = new ArrayList<String>();
    private TextView nameTo;
    private ListView chatWindow;
    private EditText editMessage;
    private Client client;
    private ClientListAdapter mAdapter;

    private static int port = 9999;
    private String host;
    static private Socket socket;
    private BufferedReader in;
    static private DataOutputStream os;
    Thread t;

    DBHandler dbHandler;
    User user;
    Button delete, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // initialization of variables and creating activity view

        nameTo = (TextView) findViewById(R.id.contact);
        nameTo.setText(ContactsActivity.selectedContactName);

        editMessage = (EditText) findViewById(R.id.editMessage);
        chatWindow = (ListView) findViewById(R.id.chat);

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
                dbHandler.deleteContact(selectedContactNumber);
                startActivity(new Intent(ChatActivity.this, ContactsActivity.class));
            }
        });


        //Update contacts information
        update = (Button) findViewById(R.id.editButton);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                //When Update button is clicked - transfers to update view
                startActivity(new Intent(ChatActivity.this, UpdateContactsActivity.class));
            }
        });



        dbHandler = new DBHandler(this);
        user = dbHandler.getUser(1);
        host = dbHandler.getIP();

        t = new Thread(this);
        t.start();
        mAdapter.notifyDataSetChanged();

        dbHandler.close();
    }

    public void sendMsg(View view) {
        dbHandler = new DBHandler(this);
        user = dbHandler.getUser(1);
        String msg = editMessage.getText().toString();
        chatMessages.add(user.getName() + ": " + msg);
        mAdapter.notifyDataSetChanged();

        client = new Client();
        client.execute(host, user.getName() + ": " + msg);

        editMessage.setText("");
        dbHandler.close();

    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new DataOutputStream(socket.getOutputStream());

            if (socket != null && in != null && os != null) {
                os.writeBytes(user.getTelnumber() + "\n");
                os.writeBytes(selectedContactNumber + "\n");
                os.writeBytes(user.getName() + " is online\n");
                Log.e("Send", "ok");
            }

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

    public class Client extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... params) {
            String ip = params[0];
            String data = params[1];
            Socket socket = null;
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

            return new Long(1);
        }
    }

    public void backToContacts(View view) {
        Intent intent = new Intent(ChatActivity.this, ContactsActivity.class);
        startActivity(intent);
    }
}
