package com.neotech.crossfit.request;

public class LoginRequest {

    private  int login_ID;

    public int getLogin_ID() {
        return login_ID;
    }

    public void setLogin_ID(int login_ID) {
        this.login_ID = login_ID;
    }

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
