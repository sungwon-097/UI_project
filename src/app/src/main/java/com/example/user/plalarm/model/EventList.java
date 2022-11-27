package com.example.user.plalarm.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    private List<Event> eventList = new ArrayList<>();

    public List<Event> getEventList(){
        return eventList;
    }
    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
    }
    public void add(Event item) {
        eventList.add(item);
    }
    public int size() {
        return eventList.size();
    }
}
