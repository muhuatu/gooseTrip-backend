package com.example.goosetrip.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public class VerifyCodeReq {
	@NotBlank
	private String mail;

	@NotBlank
	private String verificationCode;

	public VerifyCodeReq() {
		super();
	}

	public VerifyCodeReq(String mail, String verificationCode) {
		super();
		this.mail = mail;
		this.verificationCode = verificationCode;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
