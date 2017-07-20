package com.example.student.fitabs;

public class ChatMessage {
    private int id;
    private String phoneNumber;
    private String message;

    public ChatMessage (){
        this.phoneNumber = "";
        this.message = "";
    }

    public ChatMessage (String phoneNumber, String message){
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
