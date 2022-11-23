package com.example.user.plalarm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import kotlin.BuilderInference;

public class Event implements Serializable {

    private String title;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String intentApp;

    public Event(String title, String content, LocalDateTime startTime, LocalDateTime endTime, String intentApp) {
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intentApp = intentApp;
    }
    public Event(){}


    public void setIntentApp(String intentApp) {
        this.intentApp = intentApp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getIntentApp() {
        return intentApp;
    }
}
