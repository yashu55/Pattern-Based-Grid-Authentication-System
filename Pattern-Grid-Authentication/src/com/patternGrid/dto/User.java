package com.patternGrid.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	
	@Id
	@Column(name="User_Id")
	String userId;
	
	@Column(name="User_Email")
	String userEmail;
	
	@Column(name="User_Pattern_Password")
	String userPatternPassword;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String userEmail, String userPatternPassword) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPatternPassword = userPatternPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPatternPassword() {
		return userPatternPassword;
	}

	public void setUserPatternPassword(String userPatternPassword) {
		this.userPatternPassword = userPatternPassword;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userEmail=" + userEmail + ", userPatternPassword=" + userPatternPassword
				+ "]";
	}

}
