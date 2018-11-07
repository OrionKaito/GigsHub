package com.example.myfuckingpc.gigshub1;

import java.io.Serializable;

public class VerifyUser implements Serializable {
    private String fullname, mail;
    private int image;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public VerifyUser(String fullname, String mail, int image) {

        this.fullname = fullname;
        this.mail = mail;
        this.image = image;
    }
}
