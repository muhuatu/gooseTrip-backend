package com.example.goosetrip.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public class DeleteUserReq {

    @NotBlank(message = "使用者信箱不得為空")
    private String userMail;   // 信箱（主鍵）

    @NotBlank(message = "使用者密碼不得為空")
    private String userPassword;

    public DeleteUserReq() {
    }

    public DeleteUserReq(String userMail, String userPassword) {
        this.userMail = userMail;
        this.userPassword = userPassword;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
