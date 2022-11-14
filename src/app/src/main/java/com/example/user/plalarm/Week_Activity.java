package com.example.user.plalarm;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
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

        calendarView.addDecorators(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                int weekDay = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
                return weekDay == day.getDay();
            }
            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.BLUE));
            }
        }, new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                int weekDay = day.getDate().with(DayOfWeek.SUNDAY).getDayOfMonth();
                return weekDay == day.getDay();
            }
            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.RED));
            }
        });

        calendarView.setTitleFormatter(new TitleFormatter() {//header title setting
            @Override
            public CharSequence format(CalendarDay day) {
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                //                        .append(Integer.parseInt(calendarHeaderElements[2]) / 4 + 1)// 주계산하는 알고리즘 구현해봐야함
                //                        .append("주");
                return calendarHeaderElements[0] +
                        "년 " +
                        calendarHeaderElements[1] +
                        "월";
            }
        });
    }
}
