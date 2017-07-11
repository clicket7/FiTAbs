package com.example.student.fitabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by student on 17.11.7.
 */

public class ChatActivity extends AppCompatActivity {
    private String msg;
    private List<ChatMessage> chatMessages;
    private ListView chatWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatMessages = new ArrayList<ChatMessage>();
        chatWindow = (ListView) findViewById(R.id.chat);
        setContentView(R.layout.activity_chat); // initialization of variables and creating activity view
    }

    public void sendMsg() {
        msg = findViewById(R.id.editText).toString();
        ChatMessage message = new ChatMessage("Kirils", msg); // creating message object
        chatMessages.add(message); // adding message to List of ChatMessage
        ArrayAdapter<ChatMessage> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        // creating adapter for adding all messages to chat window
        chatWindow.setAdapter(adapter); // adding messages to chat window through adapter
        
    }
}
