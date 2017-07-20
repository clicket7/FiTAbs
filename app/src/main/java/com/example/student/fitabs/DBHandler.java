package com.example.student.fitabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "userInfo";
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
                + KEY_E_ID + " INTEGER PRIMARY KEY," + KEY_E_DATE + " TEXT, "
                + KEY_E_EVENT + " TEXT " + ")";
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

    public void addExercise(String type, String name, String description, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EX_TYPE, type);
        values.put(KEY_EX_NAME, name);
        values.put(KEY_EX_DESCRIPTION, description);
        values.put(KEY_EX_IMAGE, image);
        db.insert(TABLE_EXERCISES, null, values);
        db.close();
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
        if (cursor.moveToFirst()) ip = cursor.getString(1);
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

    // READ: Getting one user
    public User getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        User contact;
        String query = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            contact = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(3) > 0);
        else contact = new User();

        cursor.close();
        db.close();
        return contact;
    }

    // READ: Getting one contact
    public User getContact(String telNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        User contact;
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_C_ID,
                        KEY_C_USERNAME, KEY_C_TEL_NUMBER, KEY_C_IS_TRENER}, KEY_C_TEL_NUMBER + "=?",
                new String[]{String.valueOf(telNumber)}, null, null, null, null);
        if (cursor.moveToFirst()) contact = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(3) > 0);
        else contact = new User();
        cursor.close();
        db.close();
        return contact;
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

        cursor.close();
        db.close();
        // return contact list
        return contactList;
    }

    // READ: Getting  ChatMessages by telephoneNumber
    public ArrayList<String> getAllChatMessages(String number) {
        ArrayList<String> msgList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_CM_MSG + " FROM " + TABLE_CHAT_MESSAGE + " WHERE " + KEY_CM_CONTACT_NUMBER + "= '" + number + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(0);
                msgList.add(message);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return msgList;
    }

    public void saveAllChatMessages(ArrayList<String> list, String number) {
        deleteChatMessages(number);
        SQLiteDatabase db = this.getWritableDatabase();

        for (String str:list) {
            ContentValues values = new ContentValues();
            values.put(KEY_CM_MSG, str);
            values.put(KEY_CM_CONTACT_NUMBER, number);
            db.insert(TABLE_CHAT_MESSAGE, null, values);
        }
        db.close();
    }

    public String getExerciseDescription(String name) {
        String d = "Nothing";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXERCISES, new String[]{KEY_EX_DESCRIPTION}, KEY_EX_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor.moveToFirst())
            do {
                d = cursor.getString(0);
        } while (cursor.moveToNext());
        return d;
    }

    public String getExerciseImage(String name) {
        String image = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXERCISES, new String[]{KEY_EX_IMAGE}, KEY_EX_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor.moveToFirst())
            do {
                image = cursor.getString(0);
            } while (cursor.moveToNext());
        return image;
    }

    public void updateChatMessages(String oldNumber, String newNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CM_CONTACT_NUMBER, newNumber);
        db.update(TABLE_CHAT_MESSAGE, values, KEY_CM_CONTACT_NUMBER + " = ?", new String[] {oldNumber});
        db.close();
    }

    // DELETE: Deleting a user
    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    // DELETE: Deleting a cotact by contact name
    public void deleteContact(String telNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, "TelephoneNumber = ?", new String[]{telNumber});
        db.close();
    }

    // DELETE: Deleting a ChatMessage by phoneNUmber
    public void deleteChatMessages(String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHAT_MESSAGE, KEY_CM_CONTACT_NUMBER + " = ?", new String[]{number});
        db.close();
    }

    public void deleteAllEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, null, null);
        db.close();
    }

    public void saveAllEvents(ArrayList<Day> list) {
        deleteAllEvents();
        SQLiteDatabase db = this.getWritableDatabase();

        for (Day day:list) {
            ContentValues values = new ContentValues();
            values.put(KEY_E_DATE, day.getDay()+"/"+day.getMonth()+"/"+day.getYear());
            values.put(KEY_E_EVENT, day.getMessage());
            db.insert(TABLE_EVENTS, null, values);
        }
        db.close();
    }

    public ArrayList<Day> getAllEvents() {
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Day> list = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String[] date = cursor.getString(1).split("/");
                Day day = new Day(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
                day.setMessage(cursor.getString(2));
                // Adding contact to list
                list.add(day);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return list;
    }
}