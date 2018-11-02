package com.example.myfuckingpc.gigshub1;

import java.io.Serializable;

public class Comment implements Serializable {
    private int image;
    private String name, comment;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment(int image, String name, String comment) {

        this.image = image;
        this.name = name;
        this.comment = comment;
    }
}
