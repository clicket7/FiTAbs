package com.example.student.fitabs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private EditText username, telNumber;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void saveContact(View view) {
        dbHandler = new DBHandler(this);
        username = (EditText) findViewById (R.id.createUsername);
        telNumber = (EditText) findViewById (R.id.createTelnum);

        if (username.getText().toString().equals("") || telNumber.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noValueEntered), Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            User user = new User(username.getText().toString(), telNumber.getText().toString(), false);
            SQLiteDatabase database = dbHandler.getReadableDatabase();
            boolean exist = false;
            Cursor cursor = database.query(DBHandler.TABLE_CONTACTS,
                    null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int telNumberIndex = cursor.getColumnIndex(dbHandler.KEY_C_TEL_NUMBER);
                do {
                    if (user.getTelnumber().equals(cursor.getString(telNumberIndex))) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Contact already exist", Toast.LENGTH_LONG);
                        toast.show();
                        exist = true;
                        break;
                    }

                } while (cursor.moveToNext());

            } else{
                Toast toast = Toast.makeText(getApplicationContext(), "Table is empty", Toast.LENGTH_LONG);
                toast.show();
            }

            if (!exist) {
                database = dbHandler.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(DBHandler.KEY_C_USERNAME, user.getName());
                contentValues.put(DBHandler.KEY_C_TEL_NUMBER, user.getTelnumber());
                contentValues.put(DBHandler.KEY_C_IS_TRENER, false);

                database.insert(DBHandler.TABLE_CONTACTS, null, contentValues);

                Toast toast = Toast.makeText (getApplicationContext(), getString(R.string.saveSucc), Toast.LENGTH_LONG);
                toast.show();

                startActivity(new Intent(AddContactActivity.this, ContactsActivity.class));
            }

            username.setText("");
            telNumber.setText("");
            dbHandler.close();

        }
    }

    public void cancelAdd(View view) {
        Intent intent = new Intent(AddContactActivity.this, ContactsActivity.class);
        startActivity(intent);
    }

}
