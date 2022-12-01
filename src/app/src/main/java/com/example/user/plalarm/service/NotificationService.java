package com.example.user.plalarm.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.user.plalarm.EventListDAO;
import com.example.user.plalarm.R;
import com.example.user.plalarm.activity.MainActivity;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class NotificationService extends BroadcastReceiver {

    public NotificationService(){ }

    NotificationManager manager;
    NotificationCompat.Builder builder;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("test", "onReceive is activated");
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Log.v("test", "Alarm Manager has gotten Context");

        builder = null;
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            );
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        Log.v("test", "Notification Channel Created");

        //알림창 클릭 시 activity 화면 부름
        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,101,intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        //알림창 제목
        builder.setContentTitle("알람");
        //알림창 아이콘
        builder.setSmallIcon(R.drawable.logo);
        //알림창 터치시 자동 삭제
        builder.setAutoCancel(true);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        manager.notify(1,notification);
        // Notify test 용 Log
        Log.v("test", "Notified");

        // TTS
//        TtsService ttsService = new TtsService();

    }
}
