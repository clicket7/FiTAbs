package com.example.student.fitabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        User user = new User();
    }

    public void saveUser(View view) {

    }

    public void closeUserSettings(View view) {
    }
}
