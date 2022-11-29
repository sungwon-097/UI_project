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
    public void setEventList(List<Event> eventList){
        Collections.sort(eventList, new UserComparator());
        this.eventList = eventList;
    }
    public void add(Event item) {
        eventList.add(item);
    }
    public int size() {
        return eventList.size();
    }
    public void clear(){eventList.clear();}
}
class UserComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        if(o1.getStartTime().compareTo(o2.getStartTime())>0) {
            return 0;
        }else
            return -1;
    }
}