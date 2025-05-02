package com.example.taskmanager;

public class Notification {
    private long id;
    private String message;
    private String dateTime;

    public Notification(long id, String message, String dateTime) {
        this.id = id;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Notification(String message, String dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime;
    }
} 