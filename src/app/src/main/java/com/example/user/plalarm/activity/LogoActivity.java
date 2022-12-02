package com.example.user.plalarm.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.user.plalarm.R;
import com.example.user.plalarm.config.FirebaseDataContainer;
import com.example.user.plalarm.service.BackgroundService;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);  //Logo Activity xml 파일 호출
        new FirebaseDataContainer("test");
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // Logo -> EventViewActivity
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }
}