package com.example.user.plalarm.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.plalarm.DAO.EventListDAO;
import com.example.user.plalarm.R;
import com.example.user.plalarm.activity.EventActivity;
import com.example.user.plalarm.fragment.TopFragment;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements View.OnClickListener {

    Button calendar, day;
    ImageButton newButton;

    TextView current_day;
    TimetableView timetableView;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    String collectionPath = "test"; // TODO : 경로를 핸들링 해야 함

    LocalDate localDate = LocalDate.now();
    String currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private long pressedTime = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);

        calendar = findViewById(R.id.calendar);
        day = findViewById(R.id.day);
        newButton = findViewById(R.id.new_button);

        calendar.setOnClickListener(this);
        day.setOnClickListener(this);
        newButton.setOnClickListener(this);

        timetableView = (TimetableView) findViewById(R.id.timetable);
        schedules = MakeData(new EventListDAO(collectionPath).getWeekEventItems(currentDate));
        current_day = (TextView)findViewById(R.id.check_day);
        current_day.setText(localDate.getYear() + "년 " + localDate.getMonthValue() + "월 " + getWeekOfMonth(localDate)+"주");

        // Fragment
        TopFragment tf = new TopFragment();
        if (!tf.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_week, tf);
            transaction.commit();
        }
        timetableView.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WeekViewActivity.this);
                //builder.setTitle(schedules.get(idx - 1).getClassTitle());
                Log.d("check11111", idx+"");
                for(Schedule s: schedules){
                    Log.d("hihihihi",s + "" + idx + "") ;
                }
                builder.setMessage("하이");
                builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Event event = new Event();
//                        Intent intent = new Intent(WeekViewActivity.this, EventActivity.class);
//                        intent.putExtra("1","1");

                    }
                });
                builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                });
                builder.show();
            }
        });
    }

    private int getWeekOfMonth(LocalDate localDate) {

        LocalDate resultDate = localDate;
        int numberOfWeek = 1;

        // 이번 달이 일요일로 시작하는지의 여부
        boolean startsWithSunday = false;
        LocalDate monthStartDate = localDate.minusDays(resultDate.getDayOfMonth() - 1);
        if (monthStartDate.getDayOfWeek().getValue() == 7) startsWithSunday = true;

        // 일요일로 시작하지 않는 경우 추가적 계산 해놓는다
        if (!startsWithSunday) {
            int firstWeekDays = resultDate.getDayOfWeek().getValue() - monthStartDate.getDayOfWeek().getValue();
            if (localDate.getDayOfWeek().getValue() <= firstWeekDays) return numberOfWeek;
            resultDate = monthStartDate.plusDays(firstWeekDays);
            numberOfWeek += 1;
        }
        // 기존 Logic 사용하여 계산
        while(true){
            resultDate = resultDate.minusDays(7);
            if (resultDate.getMonth() != localDate.getMonth())
                break;
            numberOfWeek += 1;
        }
        return numberOfWeek;
    }
    public ArrayList<Schedule> MakeData(EventList eventList){
        Schedule schedule = new Schedule();
        for (Event e:eventList.getEventList()){
            String title = e.getTitle();
            schedule.setClassTitle(title);
            schedule.setDay(sevenToZero(getWeekDay(e.getStartTime())));
            schedule.setStartTime(getTime(e.getStartTime()));
            schedule.setEndTime(getTime(e.getEndTime()));
            schedule.setClassPlace(e.getContent());//내용받아오는 class
            schedule.setProfessorName(e.getIntentApp());//실행 앱
            schedules.add(schedule);
            timetableView.add(schedules);
        }
        return schedules;
    }
    public int sevenToZero(long weekDay){
        if (weekDay == 7)
            return 0;
        return (int) weekDay;
    }

    public int getWeekDay(String date){
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        LocalDate localDate = LocalDate.of(year, month, day);

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public Time getTime(String date){
        int hour = Integer.parseInt(date.substring(11, 13));
        int minute = Integer.parseInt(date.substring(14, 16));
        return new Time(hour, minute);
    }

    @Override
    public void onClick(View view) {
        if (view == calendar) {
            Intent intent = new Intent(WeekViewActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else if (view == day) {
            Intent intent = new Intent(WeekViewActivity.this, DayViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else if (view == newButton) {
            Intent intent = new Intent(WeekViewActivity.this, EventActivity.class);
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
}