package com.example.student.fitabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by student on 17.11.7.
 */

public class ChatActivity extends AppCompatActivity {
    public ArrayList<String> chatMessages = new ArrayList<>();
    ListView chatWindow;
    EditText editMessage;
    ChatMessage message = new ChatMessage();
    static int port = 9999;
    String host = "192.168.8.117";
    ChatClient cc = new ChatClient(host, port);
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // initialization of variables and creating activity view
        editMessage = (EditText) findViewById(R.id.editMessage);
        chatWindow = (ListView) findViewById(R.id.chat);


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

        t = new Thread(cc);
        t.start();

    }

    public void sendMsg(View view) {
        String msg = editMessage.getText().toString();
        message.setAuthor("User");
        message.setMsg(msg);
        chatMessages.add(message.getAuthor() + ": " + message.getMsg()); // adding message to List of ChatMessage
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        // creating adapter for adding all messages to chat window
        chatWindow.setAdapter(adapter); // adding messages to chat window through adapter
        editMessage.setText("");
        cc.sendServer(message.getMsg());
    }

    public class ChatClient implements Runnable {
        Socket socket;
        int port;
        String host;
        BufferedReader in;
        PrintWriter out;

        public ChatClient(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(host, port);
                Log.e("Socket", "CONNECTED");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                getServer();
                disconnet();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendServer(String msg) {
            if (socket != null) {
                out.print(msg);
                Log.e("O", msg);
            }
        }

        public String getServer() {
            String msg = "";
            while (true) {
                try {
                    String line;
                    if ((line = in.readLine()) != null) {
                        Log.e("I", line);
                        msg += line;
                    } else {
                        return msg;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void disconnet() {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}