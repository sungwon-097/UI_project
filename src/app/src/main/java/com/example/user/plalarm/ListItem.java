package com.example.user.plalarm;

public class ListItem {
    private String title;
    private String worktime;

    public String getTitle() {
        return title;
    }
    public String getWorktime() {
        return worktime;
    }
    public ListItem(String title, String worktime) {
        this.title = title;
        this.worktime = worktime;
    }
}
