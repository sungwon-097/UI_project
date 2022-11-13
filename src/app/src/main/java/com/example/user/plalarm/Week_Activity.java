package com.example.user.plalarm;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Calendar;

public class Week_Activity extends AppCompatActivity {

    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        calendarView = findViewById(R.id.material_week_calendar);
        calendarView.setSelectedDate(CalendarDay.today());

        calendarView.state()
                .edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        calendarView.addDecorators(new SaturdayDecorator(), new SundayDecorator());

        calendarView.setTitleFormatter(new TitleFormatter() {//header title setting
            @Override
            public CharSequence format(CalendarDay day) {
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append("년 ")
                        .append(calendarHeaderElements[1])
                        .append("월");
//                        .append(Integer.parseInt(calendarHeaderElements[2]) / 4 + 1)// 주계산하는 알고리즘 구현해봐야함
//                        .append("주");
                return calendarHeaderBuilder.toString();
            }
        });

    }
}
