package com.example.student.fitabs;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by student on 17.20.7.
 */

public class InsertExercises {

    public void addExercises() {
        DBHandler dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        db.close();
    }
}
