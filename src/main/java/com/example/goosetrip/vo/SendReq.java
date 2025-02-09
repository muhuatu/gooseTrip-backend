package com.example.goosetrip.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public class SendReq {
	@NotBlank(message = "信箱不可為空")
	private String email;
	
	private boolean register;

	public SendReq() {
		super();
	}

	public SendReq(String email, boolean register) {
		super();
		this.email = email;
		this.register = register;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isRegister() {
		return register;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}

}
