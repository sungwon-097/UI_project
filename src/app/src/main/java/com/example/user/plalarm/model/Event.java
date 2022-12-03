package com.example.user.plalarm.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Event implements Serializable {

    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private String intentApp;

    public Event(String title, String content, String startTime, String endTime, String intentApp) {
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intentApp = intentApp;
    }

    public Event(){}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setIntentApp(String intentApp) {
        this.intentApp = intentApp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getIntentApp() {
        return intentApp;
    }
}
