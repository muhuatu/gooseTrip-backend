package com.example.goosetrip.vo;

import com.example.goosetrip.dto.Users;

public class LoginRes extends BasicRes {
    private Users user;

    public LoginRes() {
        super();
    }

    public LoginRes(Users user) {
        super();
        this.user = user;
    }

    public LoginRes(int code, String message) {
        super(code, message);
    }

    public LoginRes(int code, String message, Users user) {
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
