package com.example.user.plalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import org.threeten.bp.DayOfWeek;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Day_Activity extends AppCompatActivity {

    TextView current_day;
    private MaterialCalendarView calendarView;
    RecyclerView recyclerView;
    private dayAdapter adapter = new dayAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        ArrayList<String> Week = new ArrayList<>(Arrays.asList("일요일","월요일","화요일"
        ,"수요일","목요일","금요일","토요일","일요일"));

        current_day = (TextView)findViewById(R.id.check_day);
        recyclerView = findViewById(R.id.recycler_view);

        current_day.setText(getCurrentDate() + " " + Week.get(getCurrentWeek()-1));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setItems(new SampleData().getItems());

    }

    public static String getCurrentDate(){
        Date dateNow = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        return format.format(dateNow);
    }
    public static int getCurrentWeek(){
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeekNumber;
    }
}