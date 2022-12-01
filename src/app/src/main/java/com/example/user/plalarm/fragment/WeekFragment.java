package com.example.user.plalarm.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.user.plalarm.EventListDAO;
import com.example.user.plalarm.R;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class WeekFragment extends Fragment{
    TimetableView timetableView;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    String collectionPath = "user"; // TODO : 경로를 핸들링 해야 함

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_week, container, false);
        timetableView = view.findViewById(R.id.timetable);
        schedules = MakeData(new EventListDAO(collectionPath).getEventItems());

        return view;
    }
    public ArrayList<Schedule> MakeData(EventList eventList){
        Schedule schedule = new Schedule();
        for (Event e:eventList.getEventList()){
            String title = e.getTitle();
            String dayOfWeek = e.getStartTime().substring(0,10);
            schedule.setClassTitle(title);
            schedule.setDay(getWeekOfDay(e.getStartTime()));
            schedule.setStartTime(getTime(e.getStartTime()));
            schedule.setEndTime(getTime(e.getEndTime()));
            Log.d(TAG, "Title: " + schedule.getClassTitle() + "// Time : " + schedule.getStartTime().getHour() + "// Time : "+ schedule.getEndTime().getHour());
            schedules.add(schedule);
            timetableView.add(schedules);
        }
        return schedules;
    }

    public int getWeekOfDay(String date){
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        LocalDate localDate = LocalDate.of(year, month, day);

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public Time getTime(String date){
        int hour = Integer.parseInt(date.substring(11, 13));
        int minute = Integer.parseInt(date.substring(14, 16));
        return new Time(hour, minute);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}