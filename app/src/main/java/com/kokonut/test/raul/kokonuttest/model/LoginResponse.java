package com.kokonut.test.raul.kokonuttest.model;

public class LoginResponse {

    public static final int SUCCESS = 1;
    public static final int FAIL    = 0;

    private int success;
    private String message;
    private LoginResponseData data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponseData getData() {
        return data;
    }

    public void setData(LoginResponseData data) {
        this.data = data;
    }
}
