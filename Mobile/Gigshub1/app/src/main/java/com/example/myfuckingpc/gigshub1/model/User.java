package com.example.myfuckingpc.gigshub1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("Data")
    @Expose
    private List<UserItem> data = null;

    public List<UserItem> getData() {
        return data;
    }

    public void setData(List<UserItem> data) {
        this.data = data;
    }

}