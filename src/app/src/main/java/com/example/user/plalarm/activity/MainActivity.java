package com.example.user.plalarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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

    long pressedTime = 0;

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

        // sharedPref 로 지정된 Fragment 먼저 가져오는 부분
        // TODO : sharedPref 로 이 부분을 가져 올 수 있어야 함 (DONE)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int DEFAULT = sharedPref.getInt("calendarSpinner", 0) + 1;
        fragmentView(DEFAULT);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                fragmentView(DEFAULT);
            }
        }, 1500);

        // sharedPref 로 notification button 의 상태 저장
        boolean NOTIFICATION_MUTE = sharedPref.getBoolean("NOTIFICATION_MUTE", false);
        if (NOTIFICATION_MUTE) {
            notificationOnIcon.setVisibility(View.INVISIBLE);
            notificationOffIcon.setVisibility(View.VISIBLE);
        }

        notification.setOnClickListener(this);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, EventViewActivity.class);
                startActivity(intent);
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
        // TODO : sharedPref 로 MUTE 의 ON/OFF 상태 조절 가능해야 함 (DONE)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (view == soundButton) {
            if(notificationOnIcon.getVisibility() == View.VISIBLE) {
                notificationOnIcon.setVisibility(View.INVISIBLE);
                notificationOffIcon.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("NOTIFICATION_MUTE", true);
                editor.apply();
            }
            else {
                notificationOnIcon.setVisibility(View.VISIBLE);
                notificationOffIcon.setVisibility(View.INVISIBLE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("NOTIFICATION_MUTE", false);
                editor.apply();
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
    @Override
    public void onBackPressed() {// backButton 2번누르면 나가짐

        //마지막으로 누른 '뒤로가기' 버튼 클릭 시간이 이전의 '뒤로가기' 버튼 클릭 시간과의 차이가 2초보다 크면
        if(System.currentTimeMillis() > pressedTime + 2000){
            //현재 시간을 pressedTime 에 저장
            pressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"한번 더 누르면 종료", Toast.LENGTH_SHORT).show();
        }

        //마지막 '뒤로가기' 버튼 클릭시간이 이전의 '뒤로가기' 버튼 클릭 시간과의 차이가 2초보다 작으면
        else{
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
}