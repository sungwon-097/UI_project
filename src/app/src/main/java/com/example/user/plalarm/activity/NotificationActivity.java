package com.example.user.plalarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.user.plalarm.R;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.service.TtsService;

import java.util.List;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity{

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
        TtsService ttsService = new TtsService(getApplicationContext());
        ttsService.speak(event.getTitle()); // TODO : event.getContent() 로 치환
        if (!Objects.equals(event.getIntentApp(), ""))
            pendingIntent(event.getIntentApp());
        else{
            finish();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
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
            startActivity(intent);
        }else{
            String url = "market://details?id=" + appName;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }
}

