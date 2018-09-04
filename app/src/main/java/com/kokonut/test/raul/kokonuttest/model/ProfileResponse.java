package com.kokonut.test.raul.kokonuttest.model;

public class ProfileResponse {
    private int success;
    private String message;
    private ProfileResponseData data;

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

    public ProfileResponseData getData() {
        return data;
    }

    public void setData(ProfileResponseData data) {
        this.data = data;
    }
}
