package com.example.student.fitabs;

/**
 * Created by student on 17.14.7.
 */

public final class Debugger {
    public static boolean debugMode;

    public static void setDebugMode(boolean value){
        debugMode = value;
    }

    public static void print(String str){
        if(debugMode){
            System.out.println(str);
        }
    }
}