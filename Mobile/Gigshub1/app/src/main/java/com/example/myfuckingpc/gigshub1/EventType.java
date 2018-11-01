package com.example.myfuckingpc.gigshub1;

import java.io.Serializable;

public class EventType implements Serializable {
    private String type;
    private int color;

    public EventType(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
