package com.example.user.plalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button soundButton;
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

        //soundButton 작동 시 표시 Image 전환 위한 View 선언
        notificationOnIcon = (ImageView)findViewById(R.id.notification_on_icon);
        notificationOffIcon = (ImageView)findViewById(R.id.notification_mute_icon);

        soundButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        newButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //SoundButton을 클릭할 경우, 이미지를 교차 표시(visibility)
        if (view == soundButton) {
            notificationOnIcon.setVisibility(view.INVISIBLE);
            notificationOffIcon.setVisibility(view.VISIBLE);
        }
        //SettingButton을 클릭할 경우, SettingActivity로 넘어감
        else if (view == settingButton) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        //NewButton을 클릭할 경우, 일정 생성 Activity로 넘어감
        //현재 일정 생성 Activity가 만들어지지 않았으므로 MainActivity로 가도록 해놓았음, 추후 수정
        else if (view == newButton) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
        }
    }
}