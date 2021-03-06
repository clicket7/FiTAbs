package com.example.student.fitabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.student.fitabs.R.id.calendarView;

public class MyCalendarActivity extends AppCompatActivity implements View.OnClickListener{
    CalendarView _Calendar;
    Button _ButtonSave;
    EditText _EditText;

    Day  _SelectedDay;

    ArrayList<Day> _Schedule;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Load previous entries
        dbHandler = new DBHandler(this);
        _Schedule = dbHandler.getAllEvents();

        //Setup widgets for easier access
        _Calendar = (CalendarView) this.findViewById(calendarView);
        _ButtonSave = (Button) this.findViewById(R.id.button8);
        _EditText = (EditText) this.findViewById(R.id.editText);
        _SelectedDay = new Day(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        selectedDateChanged(_Calendar, _SelectedDay.getYear(), _SelectedDay.getMonth(), _SelectedDay.getDay());

        //Set up button
        _ButtonSave.setText(R.string.btn_edit);
        updateEditText();

        _ButtonSave.setOnClickListener(this);
        _Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDateChanged(view, year, month, dayOfMonth);
            }

        });

        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById (R.id.bottom_navigation);

        //Display right icon
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        //Define Bottom navigation view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            //Selected icon(item) - changes to the appropriate view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                dbHandler.saveAllEvents(_Schedule);
                switch (item.getItemId())
                {
                    //Contacts
                    case R.id.action_contacts:
                        startActivity(new Intent(MyCalendarActivity.this, ContactsActivity.class));
                        break;

                    //Exercise
                    case R.id.action_exercise:
                        startActivity(new Intent(MyCalendarActivity.this, GroupsOfExercisesActivity.class));
                        break;

                    //Calendar
                    case R.id.action_calendar:
                        break;

                    //Settings
                    case R.id.action_settings:
                        startActivity(new Intent(MyCalendarActivity.this, UserSettingsActivity.class));
                        break;
                }
                return true;
            }
        });


    }

    public void onClick(View view){
        String buttonText = _ButtonSave.getText().toString();

        if(buttonText.equals("Edit")){
            handleEdit();
        }
        else if(buttonText.equals("Save")){
            handleSave(view);
        }

    }

    /**
     * Handles editting of schedule
     */
    private void handleEdit(){
        _ButtonSave.setText(R.string.btn_save);
        _ButtonSave.setBackgroundColor(Color.parseColor("#8bc34a"));
        updateEditText();
    }
    /**
     * Handles saving schedule
     * @param view - current view
     */
    private void handleSave(View view){
        _ButtonSave.setText(R.string.btn_edit);
        _ButtonSave.setBackgroundColor(Color.parseColor("#00b0ff"));
        updateEditText();
        closeOnScreenKeyboard(view);
        if(dayIsAlreadyInList(_SelectedDay)){
            int indexOfDay = getIndexOfSpecificDay(_SelectedDay);
            _SelectedDay.setMessage(_EditText.getText().toString());
            _Schedule.remove(indexOfDay);
            _Schedule.add(indexOfDay, _SelectedDay);
        }
        else{
            _SelectedDay.setMessage(_EditText.getText().toString());
            _Schedule.add(_SelectedDay);
        }
    }

    /**
     * Method called when CalendarView selected date changes
     * @param view - current view
     * @param year - year selected
     * @param month - month selected
     * @param dayOfMonth - day selected
     */
    private void selectedDateChanged(CalendarView view, int year, int month, int dayOfMonth){
        //Switch back to view mode
        _ButtonSave.setText(R.string.btn_edit);
        _EditText.setText("");
        updateEditText();

        closeOnScreenKeyboard(view);

        Day selectedDay = new Day(year, month, dayOfMonth);
        _SelectedDay = selectedDay;
        if(dayIsAlreadyInList(selectedDay)){
            int indexOfFoundDay = getIndexOfSpecificDay(selectedDay);
            Day day = _Schedule.get(indexOfFoundDay);
            _EditText.setText(day.getMessage());
        }
    }

    /**
     * Returns the index of a specific day from the _Schedule ArrayList
     * Note: this method does no checking that the day exists so ensure
     * dayIsAlreadyInList() is called prior to calling this method
     * @param toFind - Day that needs found
     * @return int - index of given day
     */
    private int getIndexOfSpecificDay(Day toFind){
        for(Day day : _Schedule){
            if(daysAreEqual(toFind, day)){
                return _Schedule.indexOf(day);
            }
        }
        return -1;
    }

    /**
     * Closes the on screenkeyboard
     * @param view - current view
     */
    private void closeOnScreenKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Checks if two given days are equal ie same day, month, and year
     * @param dayOne - first Day to check
     * @param dayTwo - second Day to check
     * @return boolean - true if they are the same day, otherwise false
     */
    private boolean daysAreEqual(Day dayOne, Day dayTwo){
        boolean areEqual = true;
        if(dayOne.getDay() != dayTwo.getDay() ||
                dayOne.getMonth() != dayTwo.getMonth() ||
                dayOne.getYear() != dayTwo.getYear()){
            areEqual = false;
        }
        return areEqual;
    }

    /**
     * Checks if a given day is already in the _Schedule list
     * @param dayToCheck - Day to check if it is alredy part of list
     * @return boolean - true if the Day is already in list, otherwise false
     */
    private boolean dayIsAlreadyInList(Day dayToCheck){
        boolean alreadyContains = false;
        for(Day day : _Schedule){
            if(daysAreEqual(day, dayToCheck)){
                alreadyContains = true;
            }
        }
        return alreadyContains;
    }

    /**
     * Updates the status of EditText widget so it can be interacted with properly
     */
    private void updateEditText(){
        String btnText = _ButtonSave.getText().toString();
        if(btnText.equals("Save")){
            _EditText.setEnabled(true);
            _EditText.setFocusable(true);
            _EditText.setFocusableInTouchMode(true);
            _EditText.setCursorVisible(true);
        }
        else if(btnText.equals("Edit")){
            _EditText.setEnabled(true);
            _EditText.setFocusable(false);
            _EditText.setFocusableInTouchMode(false);
            _EditText.setCursorVisible(false);
        }
    }

//
}