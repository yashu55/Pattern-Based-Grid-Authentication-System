package com.patternGrid.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login_transaction")
public class LoginTransaction {

    @Id
    @GeneratedValue
    @Column(name = "Tr_Id")
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

    @Column(name = "Session_Id")
    private String sessionId;

    @Column(name = "Status")
    private boolean status;

    @Column(name = "Login_Time")
    private String loginTime;

    @Column(name = "Logut_Time")
    private String logoutTime;

    public LoginTransaction() {
	super();
	// TODO Auto-generated constructor stub
    }

    public LoginTransaction(User user, String sessionId, boolean status, String loginTime, String logoutTime) {
	super();
	this.user = user;
	this.sessionId = sessionId;
	this.status = status;
	this.loginTime = loginTime;
	this.logoutTime = logoutTime;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    public boolean isStatus() {
	return status;
    }

    public void setStatus(boolean status) {
	this.status = status;
    }

    public String getLoginTime() {
	return loginTime;
    }

    public void setLoginTime(String loginTime) {
	this.loginTime = loginTime;
    }

    public String getLogoutTime() {
	return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
	this.logoutTime = logoutTime;
    }

    public int getTransactionId() {
	return transactionId;
    }

    @Override
    public String toString() {
	return "LoginTransaction [transactionId=" + transactionId + ", user=" + user + ", sessionId=" + sessionId
		+ ", status=" + status + ", loginTime=" + loginTime + ", logoutTime=" + logoutTime + "]";
    }

}
