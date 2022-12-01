package com.example.user.plalarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.plalarm.EventListDAO;
import com.example.user.plalarm.R;
import com.example.user.plalarm.fragment.CalendarFragment;
import com.example.user.plalarm.fragment.DayFragment;
import com.example.user.plalarm.fragment.WeekFragment;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.example.user.plalarm.service.NotificationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlarmManager alarmManager;
    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    Button soundButton, notification, calendar, week, day;
    ImageButton settingButton;
    ImageButton newButton;

    ImageView notificationOnIcon;
    ImageView notificationOffIcon;

    public EventList eventList;
//    public static Event staticEvent;
//    public static Context eventCreatedContext;
//    boolean madeFirstEvent;
//    boolean canSetAlarm;
    boolean mute;

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

        eventList = new EventListDAO("test").getEventItems();
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        eventCreatedContext = this;
//        madeFirstEvent = false;
        mute = false;

        // sharedPref 로 지정된 Fragment 먼저 가져오는 부분
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

        // 첫 Notification 오류 수정용 fragment view 출력 수정
        fragmentView(DEFAULT);

//         Notification 설정
        Event event = setEvent();
        Log.v("test", "Event is set");
        if (event != null){
            setAlarm(event);
            Log.v("test", "Alarm is Set");
//            staticEvent = event;
        }

        // sharedPref 로 notification button 의 상태 저장
        boolean NOTIFICATION_MUTE = sharedPref.getBoolean("NOTIFICATION_MUTE", false);
        if (NOTIFICATION_MUTE) {
            notificationOnIcon.setVisibility(View.INVISIBLE);
            notificationOffIcon.setVisibility(View.VISIBLE);
            mute = true;
        }
        else {
            mute = false;
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
        else if (view == newButton) {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        }
//        else if(view == notification){
////            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
////            startActivity(intent);
////            Event event = queue.poll();
//            setAlarm();
//        }
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

    public Event setEvent() {

        //알림 울릴 Event 정보 가져옴
        Event event;
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date(now);
        // eventList 내 정보 비교, 적합한 정보 선택
        int position = 0;
        while (true) {
            if (position == eventList.size()) {
                return null;
            }
            event = eventList.getEventList().get(position);
            String eventTime = event.getStartTime();
            eventTime = eventTime.substring(0, 10) + " " + eventTime.substring(11);
            Date dateToCompare = null;
            try {
                dateToCompare = sdf.parse(eventTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dateToCompare.after(dateNow)) {
                return event;
            }
            else position++;
        }
    }

    public void setAlarm(Event event) {
        Intent receiverIntent = new Intent(MainActivity.this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        //알림 울릴 Event 정보 가져옴
//        Event event;
//        long now = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date dateNow = new Date(now);
//        // eventList 내 정보 비교, 적합한 정보 선택
//        int position = 0;
//        while (true) {
//            event = eventList.getEventList().get(position);
//            String eventTime = event.getStartTime();
//            eventTime = eventTime.substring(0, 10) + " " + eventTime.substring(11);
//            Date dateToCompare = null;
//            try {
//                dateToCompare = sdf.parse(eventTime);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            if (dateToCompare.after(dateNow)) {
//                break;
//            }
//            else position++;
//        }

        String eventTime = event.getStartTime();
        String from = eventTime.substring(0, 10) + " " + eventTime.substring(11);

        //날짜 포맷을 바꿔주는 소스코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = null;
        try {
            datetime = dateFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.v("test", datetime.toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.v("test", "Event given to Alarm Manager");
    }
}