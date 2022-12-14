package com.example.user.plalarm.service;

import static android.content.ContentValues.TAG;

import static com.example.user.plalarm.config.FirebaseDataContainer.container;
import static com.example.user.plalarm.config.FirebaseDataContainer.getFirebaseData;
import static com.example.user.plalarm.config.UserInfo.userName;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.user.plalarm.activity.NotificationActivity;
import com.example.user.plalarm.model.Event;

import java.time.LocalDateTime;

public class BackgroundService extends Service {

    private Event todoEvent;

    public BackgroundService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        Thread t1 = new Thread("T1"){
            public synchronized void run(){
                while(true) {
                    getFirebaseData(userName);
                    if (container != null) {
                        for (Event e : container.getEventList()) {
                            if (container == null)
                                break;
//                            Log.d(TAG, "onStartCommand: t1");
                            LocalDateTime currentTime = LocalDateTime.now();
                            LocalDateTime thisTime = LocalDateTime.parse(e.getStartTime());
                            if (currentTime.compareTo(thisTime) <= 0) {
                                todoEvent = e;
                                break;
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t2 = new Thread("T2"){
            public synchronized void run(){
                while(true) {
                    if (todoEvent==null)
                        continue;
                    String currentTime = LocalDateTime.now().toString().substring(0, 16);
                    String thisTime= todoEvent.getStartTime().substring(0, 16);
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(BackgroundService.this);
                    boolean NOTIFICATION_MUTE_BACKGROUND = sharedPref.getBoolean("NOTIFICATION_MUTE", false);
//                    Log.d(TAG, "onStartCommand: t2 : " + currentTime + ", " + todoEvent.getStartTime().substring(0, 16));
                    if (currentTime.equals(thisTime) && !NOTIFICATION_MUTE_BACKGROUND){
                        Intent intent1 = new Intent(BackgroundService.this, NotificationActivity.class);
                        intent1.putExtra("event", todoEvent);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                        todoEvent = null;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t1.start();
        t2.start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}