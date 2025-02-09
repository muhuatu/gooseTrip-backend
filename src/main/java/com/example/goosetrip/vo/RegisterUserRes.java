package com.example.goosetrip.vo;

import com.example.goosetrip.dto.Users;

import java.io.Serializable;

public class RegisterUserRes extends BasicRes implements Serializable  {

    private Users user;

    public RegisterUserRes() {
    }

    public RegisterUserRes(int code, String message) {
        super(code, message);
    }

    public RegisterUserRes(int code, String message, Users user) {
        super(code, message);
        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
