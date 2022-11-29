package com.example.user.plalarm;

import com.example.user.plalarm.config.FirebaseDataContainer;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventListDAO {

    String collectionPath;
    EventList eventList = new EventList();

    public EventListDAO(String collectionPath) {
        this.collectionPath = collectionPath;
    }

    public EventList getEventItems(){

        FirebaseDataContainer firebaseDataContainer = new FirebaseDataContainer(collectionPath);
//        eventList.setEventList(firebaseDataContainer.getContainer());
        eventList = firebaseDataContainer.getContainer();
        return eventList;
    }
}
