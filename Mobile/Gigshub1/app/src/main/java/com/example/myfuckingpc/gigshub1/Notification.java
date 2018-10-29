package com.example.myfuckingpc.gigshub1;

import java.io.Serializable;

public class Notification implements Serializable {
    private String name, detail, time;
    private int image;

    public Notification(String name, String detail, String time, int image) {
        this.name = name;
        this.detail = detail;
        this.time = time;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
