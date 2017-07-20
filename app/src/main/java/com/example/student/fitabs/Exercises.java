package com.example.student.fitabs;

public class Exercises {
    private int id;
    private String type;
    private String exName;
    private String description;
    private String image;

    public Exercises () {
        this.type = "";
        this.exName = "";
        this.description = "";
        this.image = "";
    }

    public Exercises (String type, String exName, String description, String image){
        this.type = type;
        this.exName = exName;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
