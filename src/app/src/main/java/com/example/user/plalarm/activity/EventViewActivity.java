package com.example.user.plalarm.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.plalarm.EventListDAO;
import com.example.user.plalarm.R;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class EventViewActivity extends AppCompatActivity implements OnDateSelectedListener, View.OnClickListener {

    String collectionPath = "test";
    Button week, day, soundButton;
    ImageButton setting, newButton;
    ImageView notificationOnIcon;
    ImageView notificationOffIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        Log.d(TAG, "onCreate: EventViewActivity");
        week = findViewById(R.id.week);
        day = findViewById(R.id.day);
        setting = findViewById(R.id.setting_button);
        newButton = findViewById(R.id.new_button);
        notificationOnIcon = findViewById(R.id.notification_on_icon);
        notificationOffIcon = findViewById(R.id.notification_mute_icon);
        soundButton = findViewById(R.id.sound_button);

        week.setOnClickListener(this);
        day.setOnClickListener(this);
        setting.setOnClickListener(this);
        newButton.setOnClickListener(this);
        soundButton.setOnClickListener(this);
        notificationOnIcon.setOnClickListener(this);
        notificationOffIcon.setOnClickListener(this);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // sharedPref 로 notification button 의 상태 저장
        boolean NOTIFICATION_MUTE = sharedPref.getBoolean("NOTIFICATION_MUTE", false);
        if (NOTIFICATION_MUTE) {
            notificationOnIcon.setVisibility(View.INVISIBLE);
            notificationOffIcon.setVisibility(View.VISIBLE);
        }else{
            notificationOnIcon.setVisibility(View.VISIBLE);
            notificationOffIcon.setVisibility(View.INVISIBLE);
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
        Event_calendar_Adapter ilAdapter = new Event_calendar_Adapter();
        String[] date_format = getDate(date);
        Log.d("check date", date_format[0] + date_format[1] + date_format[2]);


        EventList eventList = new EventListDAO(collectionPath).getEventItems();
        lv.setAdapter(ilAdapter);
        for (Event e : eventList.getEventList()) {
            String title = e.getTitle();
            String start_time = e.getStartTime();
            String end_time = e.getEndTime();
            if (Compare_Date(date_format, start_time)) {
                ilAdapter.addItem(e);
            }
            Log.d("check time", start_time + " " + Compare_Date(date_format, start_time) + "");
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(EventViewActivity.this);
                builder.setTitle(date_format[0]+"년 " + date_format[1]+"월 " + date_format[2] + "일");//제목
                builder.setMessage("일정 : " + event.getTitle() + "\n"
                        + "시작 시간 : " + parsing_date(event.getStartTime()) + "\n"
                        + "종료 시간 : " + parsing_date(event.getEndTime()) + "\n"
                        + "내용 : " + event.getContent() + "\n"
                        + "이동 앱 : " + event.getIntentApp());
                builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EventViewActivity.this,EventActivity.class);
                        intent.putExtra("change",event);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EventViewActivity.this,EventDeleteActivity.class);
                        intent.putExtra("delete",event);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }


    public String[] getDate(CalendarDay date) {//터치 한 날짜 값 파싱
        String parse = date.toString();//date를 문자로 변환
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
        result = date_arr[0] + "년 " + date_arr[1] + "월 " + date_arr[2] + "일 "
                + clock_arr[0] + "시 " + clock_arr[1] + "분 ";
        return result;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (v == soundButton) {
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
        else if (v == week | v == day) {
            onBackPressed();
        }
        else if (v == setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
            //NewButton 을 클릭할 경우, 일정 생성 Activity 로 넘어감
            //현재 일정 생성 Activity 가 만들어지지 않았으므로 MainActivity 로 가도록 해놓았음, 추후 수정
        else if (v == newButton) {
            Intent intent = new Intent(this, EventActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if(notificationOnIcon.getVisibility() == View.VISIBLE) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("NOTIFICATION_MUTE", false);
            editor.apply();
        }
        else {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("NOTIFICATION_MUTE", true);
            editor.apply();
        }
    }
}