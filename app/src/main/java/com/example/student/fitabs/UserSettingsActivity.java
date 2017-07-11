package com.example.student.fitabs;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.name;

public class UserSettingsActivity extends AppCompatActivity {

    EditText editUsername, editNumber;
    CheckBox checkStatus;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        

        editUsername = (EditText) findViewById(R.id.editUsername);
        editNumber = (EditText) findViewById(R.id.editTelNumber);
        checkStatus = (CheckBox) findViewById(R.id.checkBoxStatus);
        User user = new User();

        dbHandler = new DBHandler(this);

        editUsername.setText(user.getName());
        editNumber.setText(user.getTelnumber());
        checkStatus.setChecked(user.getStatus());

    }

    public void saveUser(View view) {
        if (editUsername.getText().toString().equals("") || editNumber.getText().toString().equals("") || !checkStatus.isChecked()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please, fill in all fields", Toast.LENGTH_LONG);
            toast.show();
        } else {
            User user = new User();
            user.setName(editUsername.getText().toString());
            user.setTelnumber(editNumber.getText().toString());
            user.setStatus(checkStatus.isChecked());

            SQLiteDatabase database = dbHandler.getWritableDatabase();

            ContentValues contentValues = new ContentValues();



            contentValues.put(DBHandler.KEY_USERNAME, user.getName());
            contentValues.put(DBHandler.KEY_TEL_NUMBER, user.getTelnumber());
            contentValues.put(DBHandler.KEY_IS_TRENER, user.getStatus());

            database.insert(DBHandler.TABLE_USERS, null, contentValues);

            editUsername.setText("");
            editNumber.setText("");
            checkStatus.setChecked(false);

        }
    }

    //public void


    public void closeUserSettings(View view) {
        dbHandler.close();
        finish();
    }
}
