package com.example.student.fitabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateContactsActivity extends AppCompatActivity {
    private EditText username, telNumber;
    private DBHandler dbHandler;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        dbHandler = new DBHandler(this);
        username = (EditText) findViewById (R.id.updateUsername);
        telNumber = (EditText) findViewById (R.id.updateTelnum);

        user = dbHandler.getContact(ContactsActivity.selectedContactNumber);

        username.setText(user.getName());
        telNumber.setText(user.getTelnumber());
    }

    public void updateContact(View view) {

        if (username.getText().toString().equals("") || telNumber.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noValueEntered), Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            user.setName(username.getText().toString());
            user.setTelnumber(telNumber.getText().toString());
            if (!user.getTelnumber().equals(dbHandler.getContact(telNumber.getText().toString()).getTelnumber()) ||
                    user.getTelnumber().equals(ContactsActivity.selectedContactNumber)){
                dbHandler.deleteContact(ContactsActivity.selectedContactNumber);
                dbHandler.updateChatMessages(ContactsActivity.selectedContactNumber, user.getTelnumber());
                dbHandler.addContacts(user);
                ContactsActivity.selectedContactName = user.getName();
                ContactsActivity.selectedContactNumber = user.getTelnumber();
                username.setText("");
                telNumber.setText("");
                dbHandler.close();
                cancelUpdate(view);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Contact already exist", Toast.LENGTH_LONG);
                toast.show();
                dbHandler.close();
            }
        }
    }

    public void cancelUpdate(View view) {
        Intent intent = new Intent(UpdateContactsActivity.this, ChatActivity.class);
        startActivity(intent);
    }

}