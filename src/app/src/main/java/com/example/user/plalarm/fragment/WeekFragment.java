package com.example.user.plalarm.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WeekFragment extends Fragment{

    TextView current_day;
    TimetableView timetableView;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    String collectionPath = "test"; // TODO : 경로를 핸들링 해야 함

    LocalDate localDate = LocalDate.now();
    String currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_week, container, false);

        timetableView = view.findViewById(R.id.timetable1);
        schedules = MakeData(new EventListDAO(collectionPath).getWeekEventItems(currentDate));
        current_day = (TextView)view.findViewById(R.id.check_day1);
        current_day.setText(localDate.getYear() + "년 " + localDate.getMonthValue() + "월 " + getWeekOfMonth(localDate)+"주");

        return view;
    }

    private int getWeekOfMonth(LocalDate localDate) {

        LocalDate resultDate = localDate;
        int numberOfWeek = 1;
        while(true){
            resultDate = resultDate.minusDays(7);
            if (resultDate.getMonth() != localDate.getMonth())
                break;
            numberOfWeek += 1;
        }
        return numberOfWeek;
    }

    public ArrayList<Schedule> MakeData(EventList eventList){
        Schedule schedule = new Schedule();
        for (Event e:eventList.getEventList()){
            String title = e.getTitle();
            schedule.setClassTitle(title);
            schedule.setDay(sevenToZero(getWeekDay(e.getStartTime())));
            schedule.setStartTime(getTime(e.getStartTime()));
            schedule.setEndTime(getTime(e.getEndTime()));
            schedules.add(schedule);
            timetableView.add(schedules);
        }
        return schedules;
    }

    public int sevenToZero(long weekDay){
        if (weekDay == 7)
            return 0;
        return (int) weekDay;
    }

    public int getWeekDay(String date){
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