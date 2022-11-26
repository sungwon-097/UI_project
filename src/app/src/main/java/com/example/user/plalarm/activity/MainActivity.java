package com.example.user.plalarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.user.plalarm.R;
import com.example.user.plalarm.fragment.CalendarFragment;
import com.example.user.plalarm.fragment.DayFragment;
import com.example.user.plalarm.fragment.WeekFragment;

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

        int DEFAULT = 1; // TODO : sharedPref 로 이 부분을 가져 올 수 있어야 함
        fragmentView(DEFAULT);

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
        //SoundButton 을 클릭할 경우, 이미지를 교차 표시(visibility)
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
        //SettingButton 을 클릭할 경우, SettingActivity 로 넘어감
        else if (view == settingButton) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        //NewButton 을 클릭할 경우, 일정 생성 Activity 로 넘어감
        //현재 일정 생성 Activity 가 만들어지지 않았으므로 MainActivity 로 가도록 해놓았음, 추후 수정
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