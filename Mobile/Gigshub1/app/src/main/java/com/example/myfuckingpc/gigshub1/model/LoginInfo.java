package com.example.myfuckingpc.gigshub1.model;

public class LoginInfo {
    String username, password, grant_type;

    public LoginInfo(String username, String password, String grant_type) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }
}
