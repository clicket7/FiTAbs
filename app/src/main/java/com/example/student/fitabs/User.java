package com.example.student.fitabs;

/**
 * Created by KarishoK27 on 17.11.7.
 */

public class User {
    private int id;
    private String name;
    private String telnumber;
    private boolean isCoach;

    public User() {
        this.name = "New user";
        this.telnumber = "No number";
        this.isCoach = false;
    }

    public User(String name, String number, boolean status) {
        this.name = name;
        this.telnumber =  number;
        this.isCoach = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 20) {
            name = name.substring(0, 19);
        }
        this.name = name;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String number) {
        if (number.length() > 20) {
            number = number.substring(0, 19);
        }
        this.telnumber = number;
    }

    public boolean getStatus() {
        return isCoach;
    }

    public void setStatus(boolean status) {
        this.isCoach = status;
    }
}
