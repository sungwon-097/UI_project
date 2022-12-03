package com.example.user.plalarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.user.plalarm.EventListDAO;
import com.example.user.plalarm.R;
import com.example.user.plalarm.fragment.DayAdapter;
import com.example.user.plalarm.fragment.TopFragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayViewActivity extends AppCompatActivity implements View.OnClickListener {

    Button calendar, week;
    ImageButton newButton;

    TextView current_day;
    RecyclerView recyclerView;
    String collectionPath = "test"; // TODO : 경로를 핸들링 해야 함

    private final DayAdapter adapter = new DayAdapter();

    LocalDate localDate = LocalDate.now();
    String currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        ArrayList<String> Week = new ArrayList<>(Arrays.asList("일요일","월요일","화요일"
                ,"수요일","목요일","금요일","토요일","일요일"));

        calendar = findViewById(R.id.calendar);
        week = findViewById(R.id.week);
        newButton = findViewById(R.id.new_button);

        calendar.setOnClickListener(this);
        week.setOnClickListener(this);
        newButton.setOnClickListener(this);

        current_day = (TextView)findViewById(R.id.check_day);
        recyclerView = findViewById(R.id.recycler_view);

        current_day.setText(getCurrentDate() + " " + Week.get(getCurrentWeek()-1));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Fragment
        TopFragment tf = new TopFragment();
        if (!tf.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_day, tf);
            transaction.commit();
        }
        adapter.setItems(new EventListDAO(collectionPath).getDayEventItems(currentDate));
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

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public void onClick(View view) {
        if (view == calendar) {
            Intent intent = new Intent(DayViewActivity.this, EventViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else if (view == week) {
            Intent intent = new Intent(DayViewActivity.this, WeekViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else if (view == newButton) {
            Intent intent = new Intent(DayViewActivity.this, EventActivity.class);
            startActivity(intent);
        }
    }
}