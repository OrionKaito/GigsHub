package com.example.myfuckingpc.gigshub1.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventUpdate {
    @SerializedName("Data")
    @Expose
    private List<EventUpdateItem> data = null;

    public List<EventUpdateItem> getData() {
        return data;
    }

    public void setData(List<EventUpdateItem> data) {
        this.data = data;
    }
}
