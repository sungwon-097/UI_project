package com.example.user.plalarm.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.user.plalarm.R;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class WeekFragment extends Fragment implements View.OnClickListener{
    TimetableView timetableView;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_week, container, false);
        timetableView = view.findViewById(R.id.timetable);

        MakeData("사인페",5, 13,55,15,0);
        MakeData("사인페",3, 13,55,15,0);
        MakeData("사인페",5, 13,55,15,0);


//        MaterialCalendarView calendarView = view.findViewById(R.id.material_week_calendar);
//        calendarView.setSelectedDate(CalendarDay.today());
//
//        calendarView.state()
//                .edit()
//                .setCalendarDisplayMode(CalendarMode.WEEKS)
//                .commit();
//
//        calendarView.addDecorators(new DayViewDecorator() {
//            @Override
//            public boolean shouldDecorate(CalendarDay day) {
//                int weekDay = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
//                return weekDay == day.getDay();
//            }
//
//            @Override
//            public void decorate(DayViewFacade view) {
//                view.addSpan(new ForegroundColorSpan(Color.BLUE));
//            }
//        }, new DayViewDecorator() {
//            @Override
//            public boolean shouldDecorate(CalendarDay day) {
//                int weekDay = day.getDate().with(DayOfWeek.SUNDAY).getDayOfMonth();
//                return weekDay == day.getDay();
//            }
//
//            @Override
//            public void decorate(DayViewFacade view) {
//                view.addSpan(new ForegroundColorSpan(Color.RED));
//            }
//        });
//
//        calendarView.setTitleFormatter(new TitleFormatter() {//header title setting
//            @Override
//            public CharSequence format(CalendarDay day) {
//                LocalDate inputText = day.getDate();
//                String[] calendarHeaderElements = inputText.toString().split("-");
//                //                        .append(Integer.parseInt(calendarHeaderElements[2]) / 4 + 1)// 주계산하는 알고리즘 구현해봐야함
//                //                        .append("주");
//                return calendarHeaderElements[0] +
//                        "년 " +
//                        calendarHeaderElements[1] +
//                        "월";
//            }
//        });
        return view;
    }
    public void MakeData(String t,int SD, int S_h, int S_m, int E_h, int E_m ){
        Schedule schedule = new Schedule();
        schedule.setClassTitle(t); // sets subject
        schedule.setDay(SD);
        schedule.setStartTime(new Time(S_h,S_m)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(E_h,E_m)); // sets the end of class time (hour,minute)
        schedules.add(schedule);
        timetableView.add(schedules);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }
}