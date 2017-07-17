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
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by student on 17.11.7.
 */

public class ChatActivity extends AppCompatActivity {
    public ArrayList<String> chatMessages;
    private ListView chatWindow;
    private EditText editMessage;
    private Client client;
    public static ChatClient chatClient;
    private ClientListAdapter mAdapter;

    private static int port = 9999;
    private String host = "192.168.8.117";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // initialization of variables and creating activity view
        editMessage = (EditText) findViewById(R.id.editMessage);
        chatWindow = (ListView) findViewById(R.id.chat);
        chatMessages = new ArrayList<String>();
        mAdapter = new ClientListAdapter(this, chatMessages);
        chatWindow.setAdapter(mAdapter);


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

        chatClient = new ChatClient(host, 9999);
        new Thread(chatClient).start();
        mAdapter.notifyDataSetChanged();
        client = new Client();
        client.execute(host, "Android user is online");
    }

    public void sendMsg(View view) {
        String msg = editMessage.getText().toString();
        client = new Client();
        client.execute(host, "Android user:" + msg);
        editMessage.setText("");

    }

    public class Client extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... params) {
            String ip = params[0];
            String data = params[1];
            Socket socket = null;
            OutputStream out = null;
            try {
                socket = new Socket(ip, 9999);
                out = socket.getOutputStream();
                out.write(data.getBytes());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Long(1);
        }
    }

    public class ChatClient implements Runnable {
        private Socket client;
        BufferedReader in;
        private String serverIp;
        private int serverPort;

        public ChatClient(String host, int port) {
            serverIp = host;
            serverPort = port;
        }

        @Override
        public void run() {
            try {
                client = new Socket(serverIp, serverPort);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String msg;
                while (true) {
                    try {
                        msg = in.readLine();
                        chatMessages.add(msg);
                        Log.e("I", msg);
                        if (msg.equals("exit")) {
                            in.close();
                            client.close();
                            break;
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}