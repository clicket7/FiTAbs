package com.example.student.fitabs;

/**
 * Created by student on 17.14.7.
 */

import java.io.Serializable;

public class Day implements Serializable {
    //Fields
    private int _Year = -1;
    private int _Month = -1;
    private int _Day = -1;

    private String _Message;

    //Constructor
    public Day(int newYear, int newMonth, int newDay){
        _Year = newYear;
        _Month = newMonth;
        _Day = newDay;
    }

    //Getters & Setters
    public int getYear(){
        return _Year;
    }
    public int getMonth(){
        return _Month;
    }
    public int getDay(){
        return _Day;
    }
    public void setMessage(String newMessage){
        _Message = newMessage;
    }
    public String getMessage(){
        return _Message;
    }

}