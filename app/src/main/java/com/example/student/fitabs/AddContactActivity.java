package com.example.student.fitabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by student on 17.12.7.
 */

public class AddContactActivity extends AppCompatActivity {
    private EditText username, telnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = (EditText) findViewById(R.id.editUsername);
        telnumber = (EditText) findViewById(R.id.editTelNumber);
        setContentView(R.layout.activity_add_contact);
    }

    public void saveUser(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "It's not finished yet!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void cancelAdd(View view) {
        Intent intent = new Intent(AddContactActivity.this, ContactsActivity.class);
        startActivity(intent);
    }

}
