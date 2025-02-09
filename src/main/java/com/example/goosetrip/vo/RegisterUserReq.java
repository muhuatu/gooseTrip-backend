package com.example.goosetrip.vo;

import java.io.Serializable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Valid
public class RegisterUserReq implements Serializable {

	private static final long serialVersionUID = 2022999228904672170L;

	@NotBlank(message = "信箱不可為空")
	@Email(message = "必須是有效的電子信箱")
	private String userMail; // 信箱（主鍵）

	@NotBlank(message = "名稱不可為空")
	private String userName; // 名稱

	@NotBlank(message = "使用者手機不得為空")
	@Pattern(regexp = "^\\d{10}$", message = "手機必須為 10 個數字")
	private String userPhone; // 手機

	@NotBlank(message = "使用者密碼不得為空")
	private String userPassword; // 密碼

	@NotBlank(message = "使用者頭貼不得為空")
	private String userImage; // 頭貼

	public RegisterUserReq() {
	}

	public RegisterUserReq(String userMail, String userName, String userPhone, String userPassword, String userImage) {
		this.userMail = userMail;
		this.userName = userName;
		this.userPhone = userPhone;
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
