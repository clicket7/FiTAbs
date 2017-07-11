package com.example.student.fitabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class UserSettingsActivity extends AppCompatActivity {

    EditText editUsername, editNumber;
    CheckBox checkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        // initialization of variables and creating new empty object User
        editUsername = (EditText) findViewById(R.id.editUsername);
        editNumber = (EditText) findViewById(R.id.editTelNumber);
        checkStatus = (CheckBox) findViewById(R.id.checkBoxStatus);
        User user = new User();
        // setting EditText value to default values
        editUsername.setText(user.getName());
        editNumber.setText(user.getTelnumber());
        checkStatus.setChecked(user.getStatus());
    }

    public void saveUser(View view) {
        User user = new User();
        user.setName(editUsername.getText().toString());
        user.setTelnumber(editNumber.getText().toString());
        user.setStatus(checkStatus.isChecked());
    }

    public void closeUserSettings(View view) {
        finish();
    }
}
