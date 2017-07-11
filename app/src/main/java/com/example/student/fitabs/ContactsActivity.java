package com.example.student.fitabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private List<User> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contacts = new ArrayList<User>();
        setContentView(R.layout.activity_contacts);
    }

    public void readFromDatabase() {
    // function for reading table with all contacts
    }
}
