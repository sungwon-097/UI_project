package com.example.user.plalarm;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.user.plalarm.config.FirebaseDataContainer;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class EventListDAO {

    String collectionPath;
    EventList eventList = new EventList();
    LocalDate localDate = LocalDate.now();
    String currentDate = String.valueOf(localDate).substring(0, 10);

    public EventListDAO(String collectionPath) {
        this.collectionPath = collectionPath;
    }

    public EventList getEventItems(){

        FirebaseDataContainer firebaseDataContainer = new FirebaseDataContainer(collectionPath);
        eventList = firebaseDataContainer.getContainer();
        return eventList;
    }

    public EventList getDayEventItems(String date){

        EventList returnList = new EventList();
        FirebaseDataContainer firebaseDataContainer = new FirebaseDataContainer(collectionPath);
        eventList = firebaseDataContainer.getContainer();
        for (Event e:eventList.getEventList()){
            if(e.getStartTime().substring(0, 10).equals(date)){
                returnList.add(e);
            }
        }
        return returnList;
    }

    public EventList getWeekEventItems(String date){

        EventList returnList = new EventList();
        FirebaseDataContainer firebaseDataContainer = new FirebaseDataContainer(collectionPath);
        eventList = firebaseDataContainer.getContainer();
        Log.d(TAG, "getWeekEventItems: "+ currentDate);
        for (Event e:eventList.getEventList()){
            if(compareIsCurrentWeek(e.getStartTime().substring(0, 10))){
                Log.d(TAG, "getWeekEventItems: " + e.getStartTime().substring(0, 10));
                returnList.add(e);
            }
            else
                Log.d(TAG, "getWeekEventItems: False");
        }
        return returnList;
    }

    public long getWeekDays(String date){
        String currentDate = date.substring(0, 10);
        LocalDate localDate = LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE);
        return localDate.getDayOfWeek().getValue();
    }

    public LocalDate getSundayDate(String date){
        String currentDate = date.substring(0, 10);
        LocalDate localDate = LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE);
        localDate = localDate.minusDays(getWeekDays(date));

        return localDate;
    }

    public LocalDate getNextSundayDate(String date){
        String currentDate = date.substring(0, 10);
        LocalDate localDate = LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE);
        localDate = localDate.minusDays(getWeekDays(date));
        localDate = localDate.plusDays(7);

        return localDate;
    }

    public boolean compareIsCurrentWeek(String date){
        LocalDate thisDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        Log.d(TAG, "compareIsCurrentWeek: " + getSundayDate(currentDate).compareTo(getNextSundayDate(currentDate)));
        Period period = Period.between(getSundayDate(currentDate), getNextSundayDate(currentDate));
        Log.d(TAG, "compareIsCurrentWeek: " + period.getDays());

        if (getSundayDate(currentDate).isBefore(thisDate)||getSundayDate(currentDate).isEqual(thisDate))
            return getNextSundayDate(currentDate).isAfter(thisDate);
        return false;
    }
}
