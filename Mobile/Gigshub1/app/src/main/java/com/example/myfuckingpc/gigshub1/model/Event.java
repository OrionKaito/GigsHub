package com.example.myfuckingpc.gigshub1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event {

    @SerializedName("Data")
    @Expose
    private List<EventItem> data = null;

    public List<EventItem> getData() {
        return data;
    }

    public void setData(List<EventItem> data) {
        this.data = data;
    }

}