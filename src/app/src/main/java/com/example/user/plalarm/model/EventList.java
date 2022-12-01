package com.example.user.plalarm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventList{
    private List<Event> eventList = new ArrayList<>();

    public List<Event> getEventList(){
        return eventList;
    }
    public void add(Event item) {
        eventList.add(item);
    }
    public int size() {
        return eventList.size();
    }
    public void clear(){eventList.clear();}
}
