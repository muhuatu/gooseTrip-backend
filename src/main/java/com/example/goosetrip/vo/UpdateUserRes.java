package com.example.goosetrip.vo;

import com.example.goosetrip.dto.Users;
import jakarta.validation.Valid;

@Valid
public class UpdateUserRes extends BasicRes{

    private Users user;

    public UpdateUserRes() {
    }

    public UpdateUserRes(int code, String message) {
        super(code, message);
    }

    public UpdateUserRes(int code, String message, Users user) {
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
