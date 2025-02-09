package com.example.goosetrip.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

public class Users implements Serializable {

	private static final long serialVersionUID = -1287984960852090607L;

	@TableId
	private String userMail; // 信箱（主鍵）

	private String userName; // 名稱

	private String userPhone; // 手機

	private String userPassword; // 密碼

	private String userImage; // 頭貼

	private String userFavorite; // 我的最愛

	private String userEdit; // 編輯第幾天，範例：'1,2'(表示正在編輯 行程ID為1且第2天 的行程)

	private String favoritePost; // 收藏貼文

	private LocalDateTime editTime; // 編輯時間

	public Users(String userMail, String userName, String userPhone, String userPassword, String userImage) {
		this.userMail = userMail;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userPassword = userPassword;
		this.userImage = userImage;
	}

	public Users(String userMail, String userName, String userPhone, String userPassword, String userImage,
			String userFavorite, String userEdit, String favoritePost, LocalDateTime editTime) {
		super();
		this.userMail = userMail;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userPassword = userPassword;
		this.userImage = userImage;
		this.userFavorite = userFavorite;
		this.userEdit = userEdit;
		this.favoritePost = favoritePost;
		this.editTime = editTime;
	}

	public Users() {
		super();
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

	public String getUserFavorite() {
		return userFavorite;
	}

	public void setUserFavorite(String userFavorite) {
		this.userFavorite = userFavorite;
	}

	public String getUserEdit() {
		return userEdit;
	}

	public void setUserEdit(String userEdit) {
		this.userEdit = userEdit;
	}

	public String getFavoritePost() {
		return favoritePost;
	}

	public void setFavoritePost(String favoritePost) {
		this.favoritePost = favoritePost;
	}

	public LocalDateTime getEditTime() {
		return editTime;
	}

	public void setEditTime(LocalDateTime editTime) {
		this.editTime = editTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
