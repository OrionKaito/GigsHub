package com.example.myfuckingpc.gigshub1;

import java.io.Serializable;

public class EventSearch implements Serializable {
    private String title;
    private String description;
    private String datetime;
    private String location;
    private int Image;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public EventSearch(String title, String description, String datetime, int image) {
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        Image = image;
    }

    public EventSearch(String title, String description, String datetime, String location, int image) {
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.location = location;
        Image = image;

    }
}
