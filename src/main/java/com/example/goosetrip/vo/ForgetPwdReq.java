package com.example.goosetrip.vo;

import jakarta.validation.constraints.NotBlank;

public class ForgetPwdReq {
	@NotBlank(message = "信箱不可為空")
	private String mail;

	@NotBlank(message = "驗證碼不可為空")
	private String verificationCode;

	@NotBlank(message = "新密碼不可為空")
	private String pwd;

	public ForgetPwdReq() {
		super();
	}

	public ForgetPwdReq(@NotBlank String mail, @NotBlank String verificationCode, @NotBlank String pwd) {
		super();
		this.mail = mail;
		this.verificationCode = verificationCode;
		this.pwd = pwd;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
