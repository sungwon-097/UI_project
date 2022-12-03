package com.example.user.plalarm.activity.main;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.plalarm.DAO.EventListDAO;
import com.example.user.plalarm.R;
import com.example.user.plalarm.activity.EventActivity;
import com.example.user.plalarm.activity.DeleteEventActivity;
import com.example.user.plalarm.activity.Calendar_Adapter;
import com.example.user.plalarm.fragment.TopFragment;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.example.user.plalarm.service.TtsService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener, View.OnClickListener {

    String collectionPath = "test";
    Button week, day;

    ImageButton newButton;
    private long pressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        week = findViewById(R.id.week);
        day = findViewById(R.id.day);
        newButton = findViewById(R.id.new_button);

        week.setOnClickListener(this);
        day.setOnClickListener(this);
        newButton.setOnClickListener(this);

        Intent eventIntent = getIntent();
        Event getEvent = (Event) eventIntent.getSerializableExtra("event");

        if(getEvent != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    makeNotification(getEvent); // TODO : 시간에 맞게 알람이 울리게 해야 함
                }
            }, 3000);
        }

        TopFragment tf = new TopFragment();
        if (!tf.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_calendar, tf);
            transaction.commit();
        }

        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.material_calendarA);
        calendarView.addDecorators(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                int weekDay = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
                return weekDay == day.getDay();
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.BLUE));
            }
        }, new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                int weekDay = day.getDate().with(DayOfWeek.SUNDAY).getDayOfMonth();
                return weekDay == day.getDay();
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.RED));
            }
        },new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                CalendarDay date = CalendarDay.today();
                return date != null & day.equals(date);
            }
            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.YELLOW));
                view.addSpan(new StyleSpan(Typeface.BOLD));
                view.addSpan(new RelativeSizeSpan(1.4f));
            }
        }, new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                EventList data = new EventListDAO(collectionPath).getEventItems();
                ArrayList<String> sample = new ArrayList<>();
                ArrayList<String> parse = new ArrayList<>();
                for (Event e : data.getEventList()) {
                    sample.add(e.getStartTime());
                }
                parse = parsing_decorate(sample);
                for(int i = 0; i<sample.size(); i++){
                    String to_day = day+"";
                    if(to_day.equals(parse.get(i))) return true;
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5, Color.RED));
            }
        });
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.setOnDateChangedListener(this);
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                return calendarHeaderElements[0] +
                        "년 " +
                        calendarHeaderElements[1] +
                        "월";
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        ListView lv = (ListView) findViewById(R.id.day_list);
        Calendar_Adapter ilAdapter = new Calendar_Adapter();
        String[] date_format = getDate(date);

        EventList eventList = new EventListDAO(collectionPath).getEventItems();
        lv.setAdapter(ilAdapter);
        for (Event e : eventList.getEventList()) {
            String start_time = e.getStartTime();
            if (Compare_Date(date_format, start_time)) {
                ilAdapter.addItem(e);
            }
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(date_format[0]+"년 " + date_format[1]+"월 " + date_format[2] + "일");//제목
                builder.setMessage("일정 : " + event.getTitle() + "\n"
                        + "시작 시간 : " + parsing_date(event.getStartTime()) + "\n"
                        + "종료 시간 : " + parsing_date(event.getEndTime()) + "\n"
                        + "내용 : " + event.getContent() + "\n"
                        + "이동 앱 : " + event.getIntentApp());
                builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, EventActivity.class);
                        intent.putExtra("change",event);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, DeleteEventActivity.class);
                        intent.putExtra("delete",event);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
    public ArrayList<String> parsing_decorate(ArrayList<String> s){
        ArrayList<String> val = new ArrayList<String>();
        for(int i = 0; i < s.size(); i++){
            String[] parse = s.get(i).split("T");
            String data = parse[0];
            if(data.charAt(8)=='0')
            {
                StringBuffer str = new StringBuffer(parse[0]);
                str.deleteCharAt(parse[0].length() -2);
                val.add("CalendarDay{" + str+"}");
            }else{
                val.add("CalendarDay{" + parse[0] + "}");
            }
        }
        return val;
    }

    public String[] getDate(CalendarDay date) {//터치 한 날짜 값 파싱
        String parse = date.toString();//date 를 문자로 변환
        int start_num = parse.indexOf("{") + 1;//{의 다음 위치값
        String result = parse.substring(start_num, (parse.substring(start_num).indexOf("}") + start_num));//{}안에 들어있는 데이터 뽑아오기
        return result.split("-");
    }

    public boolean Compare_Date(String[] date, String start) {
        if (date[1].length() == 1) date[1] = "0" + date[1];
        if (date[2].length() == 1) date[2] = "0" + date[2];
        String sum = date[0] + "-" + date[1] + "-" + date[2];
        String[] compare = start.split("T");
        if (sum.equals(compare[0])) {
            return true;
        } else return false;
    }

    public String parsing_date(String s) {
        String result;
        String[] date_arr, clock_arr;
        String[] parse = s.split("T");
        date_arr = parse[0].split("-");
        clock_arr = parse[1].split(":");
        return clock_arr[0] + "시 " + clock_arr[1] + "분 ";
    }

    @Override
    public void onClick(View view) {

        if (view == week) {
            Intent intent = new Intent(MainActivity.this, WeekViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else if (view == day) {
            Intent intent = new Intent(MainActivity.this, DayViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else if (view == newButton) {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        }
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
    public void setNotification(Event event){

        Log.d(TAG, "setNotification: ");

        LocalDateTime eventTime = LocalDateTime.parse(event.getStartTime());
        while(true){
            LocalDateTime currentTime = LocalDateTime.now();
            if (eventTime.compareTo(currentTime) == 0){
                makeNotification(event);
            }
        }
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
            startActivity(intent);
        }else{
            String url = "market://details?id=" + appName;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }
}