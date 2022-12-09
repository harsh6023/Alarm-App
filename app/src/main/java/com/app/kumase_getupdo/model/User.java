package com.app.kumase_getupdo.model;

public class User {
    private String userId, userName, fullName, userEmail, userPassword;

    public User(String userId, String userName, String fullName, String userEmail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
