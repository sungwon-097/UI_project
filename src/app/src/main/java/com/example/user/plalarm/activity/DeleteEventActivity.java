package com.example.user.plalarm.activity;

import static com.example.user.plalarm.config.UserInfo.userName;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user.plalarm.activity.main.MainActivity;
import com.example.user.plalarm.config.FirebaseConfig;
import com.example.user.plalarm.model.Event;

public class DeleteEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("delete");
        FirebaseConfig.deleteData(userName, event.getStartTime()+event.getTitle());

        Toast.makeText(this, event.getTitle() + "일정이 삭제 되었습니다", Toast.LENGTH_SHORT).show();
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
