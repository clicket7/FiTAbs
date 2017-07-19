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
    public static final String TABLE_CHAT_MESSAGE = "ChatMessage";
    public static final String TABLE_EXERCISES = "Exercises";
    public static final String TABLE_IP = "IP";
    public static final String TABLE_EVENTS = "Events";

    // USer Table Columns names
    public static final String KEY_ID = "ID";
    public static final String KEY_USERNAME = "Username";
    public static final String KEY_TEL_NUMBER = "TelephoneNumber";
    public static final String KEY_IS_TRENER = "IsTrener";

    // IP Table Columns names
    public static final String KEY_IP_ID = "ID";
    public static final String KEY_IP = "IP";

    // Contacts Table Columns names
    public static final String KEY_C_ID = "ID";
    public static final String KEY_C_USERNAME = "Username";
    public static final String KEY_C_TEL_NUMBER = "TelephoneNumber";
    public static final String KEY_C_IS_TRENER = "IsTrener";

    // ChatMessage Table Columns names
    public static final String KEY_CM_ID = "ID";
    public static final String KEY_CM_CONTACT_NUMBER = "ConcactTelephoneNumber";
    public static final String KEY_CM_MSG = "Message";

    // Exercises Table Columns names
    public static final String KEY_EX_ID = "ID";
    public static final String KEY_EX_TYPE = "ExerciseType";
    public static final String KEY_EX_NAME = "ExerciseName";
    public static final String KEY_EX_DESCRIPTION = "ExerciseDescription";
    public static final String KEY_EX_IMAGE= "Image";

    //Events Table names
    public static final String KEY_E_ID = "ID";
    public static final String KEY_E_DATE = "Date";
    public static final String KEY_E_EVENT = "Event";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT, "
                + KEY_TEL_NUMBER + " TEXT, " + KEY_IS_TRENER + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_IP_TABLE = "CREATE TABLE " + TABLE_IP + "("
                + KEY_IP_ID + " INTEGER PRIMARY KEY," + KEY_IP + " TEXT)";
        db.execSQL(CREATE_IP_TABLE);

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_C_ID + " INTEGER PRIMARY KEY," + KEY_C_USERNAME + " TEXT, "
                + KEY_C_TEL_NUMBER + " TEXT, " + KEY_C_IS_TRENER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_CHAT_MESSAGE_TABLE = "CREATE TABLE " + TABLE_CHAT_MESSAGE + "("
                + KEY_CM_ID + " INTEGER PRIMARY KEY," + KEY_CM_CONTACT_NUMBER + " TEXT, "
                + KEY_CM_MSG + " TEXT" + ")";
        db.execSQL(CREATE_CHAT_MESSAGE_TABLE);

        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISES + "("
                + KEY_EX_ID + " INTEGER PRIMARY KEY," + KEY_EX_TYPE + " TEXT, "
                + KEY_EX_NAME + " TEXT, " + KEY_EX_DESCRIPTION + " TEXT, " + KEY_EX_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_EXERCISE_TABLE);

        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_E_ID + " INTEGER PRIMARY KEY," + KEY_E_DATE + " DATETIME, "
                + KEY_E_EVENT + " TEXT, " + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
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
    public void addEvent(String event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_E_EVENT, event);
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public String getEvent() {
        String event = "";
        String selectQuery = "SELECT KEY_E_EVENT FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) event = cursor.getString(0);
        cursor.close();
        db.close();
        return event;
    }



    public void addIP(String ip) {
        SQLiteDatabase db = this.getWritableDatabase();
        //map user values with table’s column using ContentValues object
        ContentValues values = new ContentValues();
        values.put(KEY_IP, ip);
        db.insert(TABLE_IP, null, values);
        db.close();
    }

    public String getIP() {
        String ip = "";
        String selectQuery = "SELECT * FROM " + TABLE_IP;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) ip = cursor.getString(0);
        cursor.close();
        db.close();
        return ip;
    }

    public void deleteIP() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IP, null, null);
        db.close();
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

    //  INSERT : Adding new ChatMessages
    public void addChatMessages(ArrayList<String> msg, String number) {
        SQLiteDatabase db = this.getWritableDatabase();

        int length = msg.size();
        int i = 0;
        while (i > length + 1) {
            //map user values with table’s column using ContentValues object
            ContentValues values = new ContentValues();
            values.put(KEY_CM_CONTACT_NUMBER, number);
            values.put(KEY_CM_MSG, msg.get(i).toString());
            // Inserting Row
            db.insert(TABLE_CHAT_MESSAGE, null, values);
            i++;
        }
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
    public User getContact(String telNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_C_ID,
                        KEY_C_USERNAME, KEY_C_TEL_NUMBER, KEY_C_IS_TRENER}, KEY_C_TEL_NUMBER + "=?",
                new String[]{String.valueOf(telNumber)}, null, null, null, null);
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

    // READ: Getting  ChatMessages by telephoneNumber
    public List<ChatMessage> getAllChatMessages(String number) {
        List<ChatMessage> msgList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CHAT_MESSAGE + "WHERE telephoneNumber = ?" + number;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {ChatMessage message = new ChatMessage();
                message.setId(Integer.parseInt(cursor.getString(0)));
                message.setPhoneNumber(cursor.getString(1));
                message.setMessage(cursor.getString(2));
                // Adding contact to list
                msgList.add(message);
            } while (cursor.moveToNext());
        }
        // return contact list
        return msgList;
    }

    // READ: Getting All Exercises by type
    public List<Exercises> getExercisesByType(String type) {
        List<Exercises> exerList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISES+ "WHERE exerciseType = ?" + type;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do { Exercises exer = new Exercises();
                exer.setId(Integer.parseInt(cursor.getString(0)));
                exer.setType(cursor.getString(1));
                exer.setExName(cursor.getString(2));
                exer.setDescription(cursor.getString(3));
                exer.setImage(cursor.getString(4));
                // Adding contact to list
                exerList.add(exer);
            } while (cursor.moveToNext());
        }
        // return contact list
        return exerList;
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
        return db.update(TABLE_CONTACTS, values, KEY_C_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    // DELETE: Deleting a user
    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    // DELETE: Deleting a cotact by contact name
    public void deleteContact(String contactName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, "username = ?", new String[]{contactName});
        db.close();
    }

    // DELETE: Deleting a ChatMessage by phoneNUmber
    public void deleteChatMessages(String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, "phoneNumber = ?", new String[]{number});
        db.close();
    }
}