package com.example.user.plalarm.model;

import java.io.Serializable;

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

    public String parsing_date(String s) {
        String result;
        String[] date_arr, clock_arr;
        String[] parse = s.split("T");
        date_arr = parse[0].split("-");
        clock_arr = parse[1].split(":");
//        result = date_arr[0] + "년 " + date_arr[1] + "월 " + date_arr[2] + "일 "
//                + clock_arr[0] + "시 " + clock_arr[1] + "분 ";
        return clock_arr[0] + "시 " + clock_arr[1] + "분";
    }
}
