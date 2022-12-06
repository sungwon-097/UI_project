package com.example.user.plalarm.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.user.plalarm.R;
import com.example.user.plalarm.config.FirebaseDataContainer;
import com.example.user.plalarm.service.BackgroundService;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);  //Logo Activity xml 파일 호출
        // service start
        startService(new Intent(LogoActivity.this, BackgroundService.class));
        // SharedPref 로 초기 화면 지정
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int spinnerValue = sharedPref.getInt("calendarSpinner", 0);
        if (spinnerValue == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }, 1500);
        }
        else if (spinnerValue == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LogoActivity.this, WeekViewActivity.class);
                    startActivity(intent);
                }
            }, 1500);
        }
        else if (spinnerValue == 2) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LogoActivity.this, DayViewActivity.class);
                    startActivity(intent);
                }
            }, 1500);
        }
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
    }
}