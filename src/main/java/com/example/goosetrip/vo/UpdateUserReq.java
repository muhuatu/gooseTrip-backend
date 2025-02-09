package com.example.goosetrip.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public class UpdateUserReq {

    private String userMail;   // 信箱（主鍵）

    private String userName;  // 名稱

    private String userPhone;    // 手機

    //@NotBlank(message = "密碼不可為空")
    private String userOldPassword; // 舊密碼(變更密碼的話必填)

    private String userPassword; // 新密碼

    private String userImage; // 頭貼

    public UpdateUserReq() {
    }

    public UpdateUserReq(String userMail, String userName, String userPhone, String userOldPassword, String userPassword, String userImage) {
        this.userMail = userMail;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userOldPassword = userOldPassword;
        this.userPassword = userPassword;
        this.userImage = userImage;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserOldPassword() {
        return userOldPassword;
    }

    public void setUserOldPassword(String userOldPassword) {
        this.userOldPassword = userOldPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
