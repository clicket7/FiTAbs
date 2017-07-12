package com.example.student.fitabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandra Bogusha on 17.11.7.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "userInfo";
    // Contacts table name
    public static final String TABLE_USER = "User";
    public static final String TABLE_CONTACTS = "Contacts";
    // USer Table Columns names
    public static final String KEY_ID = "ID";
    public static final String KEY_USERNAME = "Username";
    public static final String KEY_TEL_NUMBER = "TelephoneNumber";
    public static final String KEY_IS_TRENER = "IsTrener";

    // Contacts Table Columns names
    public static final String KEY_C_ID = "ID";
    public static final String KEY_C_USERNAME = "Username";
    public static final String KEY_C_TEL_NUMBER = "TelephoneNumber";
    public static final String KEY_C_IS_TRENER = "IsTrener";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT, "
                + KEY_TEL_NUMBER + " TEXT, " + KEY_IS_TRENER + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT, "
                + KEY_TEL_NUMBER + " TEXT, " + KEY_IS_TRENER + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Creating tables again
        onCreate(db);
    }

    //  INSERT : Adding new user
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        //map user values with table’s column using ContentValues object
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getName());
        values.put(KEY_TEL_NUMBER, user.getTelnumber());
        values.put(KEY_IS_TRENER, user.getStatus());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    //  INSERT : Adding new contacts
    public void addContacts(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        //map user values with table’s column using ContentValues object
        ContentValues values = new ContentValues();
        values.put(KEY_C_USERNAME, user.getName());
        values.put(KEY_C_TEL_NUMBER, user.getTelnumber());
        values.put(KEY_C_IS_TRENER, user.getStatus());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // READ: Getting one user
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID,
                        KEY_USERNAME, KEY_TEL_NUMBER, KEY_IS_TRENER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User contact = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(3) > 0);
        return contact;
    }

    // READ: Getting one contact
    public User getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_C_ID,
                        KEY_C_USERNAME, KEY_C_TEL_NUMBER, KEY_C_IS_TRENER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User contact = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(3) > 0);
        return contact;
    }

    // READ: Getting All Users
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setTelnumber(cursor.getString(2));
                user.setStatus(cursor.getInt(3) > 0);
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        // return contact list
        return userList;
    }

    // READ: Getting All Contacts
    public List<User> getAllContacts() {
        List<User> contactList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setTelnumber(cursor.getString(2));
                user.setStatus(cursor.getInt(3) > 0);
                // Adding contact to list
                contactList.add(user);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // To get total numbers of user records in database write getUsersCount
    public int getUserCount() {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // To get total numbers of user records in database write getUsersCount
    public int getContactsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }


    // UPDATE: Updating a user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getName());
        values.put(KEY_TEL_NUMBER, user.getTelnumber());
        values.put(KEY_IS_TRENER, user.getStatus());
        // updating row
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    // UPDATE: Updating a contact
    public int updateContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_C_USERNAME, user.getName());
        values.put(KEY_C_TEL_NUMBER, user.getTelnumber());
        values.put(KEY_C_IS_TRENER, user.getStatus());
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    // DELETE: Deleting a user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // DELETE: Deleting a cotact
    public void deleteContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
}