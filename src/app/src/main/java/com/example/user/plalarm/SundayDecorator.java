package com.example.user.plalarm;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

public class SundayDecorator implements DayViewDecorator {
    
    private final Calendar calendar = Calendar.getInstance();
    
    public SundayDecorator(){}
    
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        int weekDay = day.getDate().with(DayOfWeek.SUNDAY).getDayOfMonth();
        return weekDay == day.getDay();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}
