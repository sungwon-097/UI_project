package com.example.user.plalarm;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.user.plalarm.config.FirebaseConfig;
import com.example.user.plalarm.fragment.CalendarFragment;
import com.example.user.plalarm.fragment.DayFragment;
import com.example.user.plalarm.fragment.WeekFragment;
import com.example.user.plalarm.model.Event;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button soundButton, notification, calendar, week, day;
    ImageButton settingButton;
    ImageButton newButton;

    ImageView notificationOnIcon;
    ImageView notificationOffIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //3가지 Button 활성화
        soundButton = (Button)findViewById(R.id.sound_button);
        settingButton = (ImageButton)findViewById(R.id.setting_button);
        newButton = (ImageButton)findViewById(R.id.new_button);

        calendar = findViewById(R.id.calendar);
        week = findViewById(R.id.week);
        day = findViewById(R.id.day);
        notification = findViewById(R.id.notification);

        //soundButton 작동 시 표시 Image 전환 위한 View 선언
        notificationOnIcon = (ImageView)findViewById(R.id.notification_on_icon);
        notificationOffIcon = (ImageView)findViewById(R.id.notification_mute_icon);

        soundButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        newButton.setOnClickListener(this);

        notification.setOnClickListener(this);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView(1);
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView(2);
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView(3);
            }
        });

//        TabHost tabHost = findViewById(R.id.host);
//        tabHost.setup();
//
//        TabHost.TabSpec spec = tabHost.newTabSpec("month");
//        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.month_icon, null));
//        spec.setContent(R.id.month_view);
//        tabHost.addTab(spec);
//
//        spec = tabHost.newTabSpec("week");
//        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.week_icon, null));
//        spec.setContent(R.id.week_view);
//        tabHost.addTab(spec);
//
//        spec = tabHost.newTabSpec("day");
//        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.daily_icon, null));
//        spec.setContent(R.id.day_view);
//        tabHost.addTab(spec);
    }

    private void fragmentView(int fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment){
            case 1:
                CalendarFragment cf = new CalendarFragment();
                transaction.replace(R.id.fragment_container, cf);
                transaction.commit();
                break;
            case 2:
                WeekFragment wf = new WeekFragment();
                transaction.replace(R.id.fragment_container, wf);
                transaction.commit();
                break;
            case 3:
                DayFragment df = new DayFragment();
                transaction.replace(R.id.fragment_container, df);
                transaction.commit();
                break;
        }
    }
    @Override
    public void onClick(View view) {
        //SoundButton을 클릭할 경우, 이미지를 교차 표시(visibility)
        if (view == soundButton) {
            if(notificationOnIcon.getVisibility() == View.VISIBLE) {
                notificationOnIcon.setVisibility(View.INVISIBLE);
                notificationOffIcon.setVisibility(View.VISIBLE);
            }
            else {
                notificationOnIcon.setVisibility(View.VISIBLE);
                notificationOffIcon.setVisibility(View.INVISIBLE);
            }
        }
        //SettingButton을 클릭할 경우, SettingActivity로 넘어감
        else if (view == settingButton) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        //NewButton을 클릭할 경우, 일정 생성 Activity로 넘어감
        //현재 일정 생성 Activity가 만들어지지 않았으므로 MainActivity로 가도록 해놓았음, 추후 수정
        else if (view == newButton) {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        }
        else if(view == notification){
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        }
    }
}