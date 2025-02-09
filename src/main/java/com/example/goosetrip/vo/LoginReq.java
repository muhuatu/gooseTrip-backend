package com.example.goosetrip.vo;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public class LoginReq {
	@NotBlank(message = "使用者信箱不得為空")
	private String userMail;

	@NotBlank(message = "使用者密碼不得為空")
	@JsonAlias({ "pwd", "userPassword", "password" })
	private String pwd;

	public LoginReq() {
		super();
	}

	public LoginReq(String userMail, String pwd) {
		super();
		this.userMail = userMail;
		this.pwd = pwd;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
