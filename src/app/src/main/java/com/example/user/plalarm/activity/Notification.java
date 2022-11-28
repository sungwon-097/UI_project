//package com.example.user.plalarm.activity;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.os.Build;
//import com.example.user.plalarm.R;
//import com.example.user.plalarm.model.Event;
//import com.example.user.plalarm.model.EventList;
//
//import androidx.core.app.NotificationCompat;
//
//import java.util.List;
//
//public class Notification {
//
//    //형님이 주신 메소드
//    public void createNotificationChannel(String channelID, String channelName, int importance){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(new NotificationChannel(channelID, channelName, importance));
//        }
//    }
//
//    public void createNotification(String channelID, int id, String title, String content){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//
//        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(id, builder.build());
//    }
//
//    // 여기서부터 구현
//
//    String channelID = "DEFAULT";
//    // Channel 생성
//    createNotificationChannel(channelID, "default", NotificationManager.IMPORTANCE_HIGH);
//    // eventList 가져옴(event 목록)
//    List<Event> eventList = EventList.getEventList();
//    // eventList 의 첫번째 event 가 제일 가까운 event 라고 가정
//    Event oncomingEvent = eventList.get(0);
//    // Notification 생성
//    createNotification(channelID, 1, oncomingEvent.getTitle(), oncomingEvent.getContent());
//
//}
