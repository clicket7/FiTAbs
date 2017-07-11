package com.example.student.fitabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    private EditText msgField;
    private ArrayList<String> chatMessages = new ArrayList<>();
    ListView chatWindow;
    EditText editMessage;
    ChatMessage message = new ChatMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatMessages = new ArrayList<ChatMessage>();
        chatWindow = (ListView) findViewById(R.id.chat);
        msgField = (EditText) findViewById(R.id.editText);
        setContentView(R.layout.activity_chat); // initialization of variables and creating activity view
        editMessage = (EditText) findViewById(R.id.editMessage);
        chatWindow = (ListView) findViewById(R.id.chat);
    }

    public void sendMsg() {
        msg = msgField.getText().toString();
        ChatMessage message = new ChatMessage("Kirils", msg); // creating message object
        chatMessages.add(message); // adding message to List of ChatMessage
        ArrayAdapter<ChatMessage> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
    public void sendMsg(View view) {
        String msg = editMessage.getText().toString();
        message.setAuthor("User");
        message.setMsg(msg);
        chatMessages.add(message.getAuthor() + ": " + message.getMsg()); // adding message to List of ChatMessage

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        // creating adapter for adding all messages to chat window
        chatWindow.setAdapter(adapter); // adding messages to chat window through adapter
        editMessage.setText("");
    }
}
