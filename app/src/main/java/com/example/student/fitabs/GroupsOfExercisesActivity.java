package com.example.student.fitabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 17.18.7.
 */

public class GroupsOfExercisesActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> groups;
    private DBHandler dbHandler;
    static public String selectedOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_of_exercises);

        dbHandler = new DBHandler(this);
        listView = (ListView) findViewById(R.id.exerciseList);
        groups = new ArrayList<>();
        readFromDatabase();
        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //Display right icon
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        //Define Bottom navigation view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //Selected icon(item) - changes to the appropriate view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //Contacts
                    case R.id.action_contacts:
                        startActivity(new Intent(GroupsOfExercisesActivity.this, ContactsActivity.class));
                        break;
                    //Exercise
                    case R.id.action_exercise:
                        break;
                    //Calendar
                    case R.id.action_calendar:
                        startActivity(new Intent(GroupsOfExercisesActivity.this, MyCalendarActivity.class));
                        break;
                    //Settings
                    case R.id.action_settings:
                        startActivity(new Intent(GroupsOfExercisesActivity.this, UserSettingsActivity.class));
                        break;
                }
                return true;
            }
        });

        StringAdapter adapter = new StringAdapter(this, groups);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
                SQLiteDatabase database = dbHandler.getReadableDatabase();
                Cursor cursor = database.query(DBHandler.TABLE_EXERCISES,
                        null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    //converts cicked item postion id from long to int
                    int i = (int) arg3;
                    //gets selectedContact object from contacts ArrayList by selectedID
                    selectedOption = groups.get(i);
                    // Create a new intent to open the {@link FamilyActivity}
                    Intent intent = new Intent(GroupsOfExercisesActivity.this, ExercisesActivity.class);
                    // Start the new activity
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error during loading exercises!", Toast.LENGTH_LONG);
                    toast.show();
                }

                cursor.close();
                dbHandler.close();
            }
        });
    }

    public void readFromDatabase() {

        dbHandler.deleteAllExercises();
        Bitmap bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] image;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pushup);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Chest", "Decline Push-Up", "Move your feet up to a box or bench. Just do push-ups.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chainpress);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Chest", "Chain Press", "Lower the chains by flexing the elbows, unloading some of the chain onto the floor.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.butterfly);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Chest", "ButterFly", "Sit on the machine with your back flat on the pad.Push the handles together slowly.", image);


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.burbellcurl);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Arms", "Barbell Curl ", "Stand up with your torso upright while holding a barbell at a shoulder-width grip.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.benchdips);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Arms", "Bench Dips", "Slowly lower your body as you inhale by bending at the elbows until you lower yourself far enough.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bodyup);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Arms", "Body-Up", "Slowly lower your forearms back to the ground by allowing the elbows to flex. Repeat for the desired number of repetitions.", image);


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.benchjump);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Legs", "Bench Jump", "Jump over the bench, landing with the knees bent, absorbing the impact through the legs.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bicycling);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Legs", "Bicycling", "To begin, seat yourself on the bike and adjust the seat to your height.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bodyweightsquat);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Legs", "Bodyweight Squat", "Begin the movement by flexing your knees and hips, sitting back with your hips.", image);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.situp);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Abs", "Sit-Up", "Flex your hips and spine to raise your torso toward your knees.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.airbike);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Abs", "Air Bike", "Slowly go through a cycle pedal motion kicking forward with the right leg and bringing in the knee of the left leg. Bring your right elbow close to your left knee by crunching to the side, as you breathe out.", image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.deadbug);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        dbHandler.addExercise("Abs", "Dead Bug", "Begin lying on your back with your hands extended above you toward the ceiling. Bring your feet, knees, and hips up to 90 degrees.", image);


        String selectQuery = "SELECT DISTINCT " + dbHandler.KEY_EX_TYPE + " FROM " + dbHandler.TABLE_EXERCISES;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                groups.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        dbHandler.close();
    }

}
