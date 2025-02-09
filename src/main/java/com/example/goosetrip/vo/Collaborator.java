package com.example.goosetrip.vo;

public class Collaborator {
	private String userMail;

	private String userName;

	private String userImage;
	
	private String invitation;

	public Collaborator() {
		super();
	}

	public Collaborator(String userMail, String userName, String userImage, String invitation) {
		super();
		this.userMail = userMail;
		this.userName = userName;
		this.userImage = userImage;
		this.invitation = invitation;
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

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

}
