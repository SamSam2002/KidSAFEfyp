package com.example.myapplication;

public class ChildResponse {
    private boolean error;
    private String message;

    public boolean isError() {
        return error;
    }

    public String getMessage(String message) {
        return this.message;
    }

}
