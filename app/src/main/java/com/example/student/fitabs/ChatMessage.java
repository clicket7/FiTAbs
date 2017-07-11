package com.example.student.fitabs;

/**
 * Created by student on 17.11.7.
 */

public class ChatMessage {
    private String msg;
    private String author;

    public ChatMessage() {
        msg = "";
        author = "";
    }

    public ChatMessage(String author, String msg) {
        this.author = author;
        this.msg = msg;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
