package com.example.myfuckingpc.gigshub1.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("Data")
    @Expose
    private List<CategoryItem> data = null;

    public List<CategoryItem> getData() {
        return data;
    }

    public void setData(List<CategoryItem> data) {
        this.data = data;
    }

}
