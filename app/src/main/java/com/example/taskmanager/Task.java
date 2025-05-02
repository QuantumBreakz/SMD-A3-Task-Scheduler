package com.example.taskmanager;

public class Task {
    private long id;
    private String title;
    private String description;
    private String dateTime;
    private String status;
    private String category;

    public Task(long id, String title, String description, String dateTime, String status, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
        this.category = category;
    }

    public Task(String title, String description, String dateTime, String status, String category) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 