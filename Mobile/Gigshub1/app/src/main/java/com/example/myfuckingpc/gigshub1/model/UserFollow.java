package com.example.myfuckingpc.gigshub1.model;

import java.io.Serializable;

public class UserFollow implements Serializable {
    private int image;
    private String name, fullname;
    private boolean verify;

    public UserFollow(int image, String name, String fullname, boolean verify, boolean follow) {
        this.image = image;
        this.name = name;
        this.fullname = fullname;
        this.verify = verify;
        this.follow = follow;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    private boolean follow;

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

}
