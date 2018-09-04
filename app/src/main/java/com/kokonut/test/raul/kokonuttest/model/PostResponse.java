package com.kokonut.test.raul.kokonuttest.model;

public class PostResponse {
    private int success;
    private String message;
    private PostResponseData data;

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

    public PostResponseData getData() {
        return data;
    }

    public void setData(PostResponseData data) {
        this.data = data;
    }
}
