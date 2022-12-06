package com.example.user.plalarm.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.plalarm.R;
import com.example.user.plalarm.config.FirebaseConfig;
import com.example.user.plalarm.config.FirebaseDataContainer;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.example.user.plalarm.service.TtsService;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationActivity extends AppCompatActivity{

    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");
        if (event == null)
            finish();
        assert event != null;
        makeNotification(event);
        super.onCreate(savedInstanceState);
    }

    public void makeNotification(Event event) {
        String channelID = "DEFAULT";
        createNotificationChannel(channelID, "default", NotificationManager.IMPORTANCE_HIGH);
        createNotification(channelID, 1, event.getTitle(), event.getContent());
        TtsService ttsService = new TtsService(this);
        ttsService.speak(event.getTitle()); // TODO : event.getContent() 로 치환
        pendingIntent(event.getIntentApp());
    }

    public void createNotificationChannel(String channelID, String channelName, int importance){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(channelID, channelName, importance));
        }
    }

    public void createNotification(String channelID, int id, String title, String content){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(content)
//                .setSound()
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }

    public boolean getPackageList(String appName) {
        boolean isExist = false;

        PackageManager pkgMgr = getPackageManager();
        List<ResolveInfo> mApps;
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = pkgMgr.queryIntentActivities(mainIntent, 0);

        try {
            for (int i = 0; i < mApps.size(); i++) {
                if(mApps.get(i).activityInfo.packageName.startsWith(appName)){
                    isExist = true;
                    break;
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        return isExist;
    }

    public void pendingIntent(String appName){
        if(getPackageList(appName)) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(appName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else{
            String url = "market://details?id=" + appName;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }
}

