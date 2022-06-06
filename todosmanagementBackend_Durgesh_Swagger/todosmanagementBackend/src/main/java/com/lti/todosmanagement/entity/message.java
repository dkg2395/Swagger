package com.lti.todosmanagement.entity;

public class message {
    private String message;

    public message() {
    }

    public message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "message{" +
                "message='" + message + '\'' +
                '}';
    }
}
