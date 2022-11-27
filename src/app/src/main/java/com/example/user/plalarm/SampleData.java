package com.example.user.plalarm;

import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;

public class SampleData {

    String collectionPath = "user";
    EventList eventList = new EventList();

    public EventList getEventItems(){

        Event event1 = new Event();
        Event event2 =  new Event();
        Event event3 = new Event();

        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        return eventList;
    }
}
