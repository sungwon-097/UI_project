package com.example.user.plalarm.activity.main;

import static com.example.user.plalarm.config.UserInfo.userName;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

    LocalDate localDate = LocalDate.now();
    String currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private long pressedTime = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        calendar = findViewById(R.id.calendar);
        day = findViewById(R.id.day);
        newButton = findViewById(R.id.new_button);

        calendar.setOnClickListener(this);
        day.setOnClickListener(this);
        newButton.setOnClickListener(this);

        timetableView = (TimetableView) findViewById(R.id.timetable);
        schedules = MakeData(new EventListDAO(userName).getWeekEventItems(currentDate));
        current_day = (TextView)findViewById(R.id.check_day);
        current_day.setText(localDate.getYear() + "??? " + localDate.getMonthValue() + "??? " + getWeekOfMonth(localDate)+"???");

        TopFragment tf = new TopFragment();
        if (!tf.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_week, tf);
            transaction.commit();
        }
    }

    private int getWeekOfMonth(LocalDate localDate) {

        LocalDate resultDate = localDate;
        int numberOfWeek = 1;

        // ?????? ?????? ???????????? ?????????????????? ??????
        boolean startsWithSunday = false;
        LocalDate monthStartDate = localDate.minusDays(resultDate.getDayOfMonth() - 1);
        if (monthStartDate.getDayOfWeek().getValue() == 7) startsWithSunday = true;

        // ???????????? ???????????? ?????? ?????? ????????? ?????? ????????????
        if (!startsWithSunday) {
            int firstWeekDays = resultDate.getDayOfWeek().getValue() - monthStartDate.getDayOfWeek().getValue();
            if (localDate.getDayOfWeek().getValue() <= firstWeekDays) return numberOfWeek;
            resultDate = monthStartDate.plusDays(firstWeekDays);
            numberOfWeek += 1;
        }
        // ?????? Logic ???????????? ??????
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
            schedule.setClassPlace(e.getContent());//?????????????????? class
            schedule.setProfessorName(e.getIntentApp());//?????? ???
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
    public void onBackPressed() {// backButton 2???????????? ?????????

        //??????????????? ?????? '????????????' ?????? ?????? ????????? ????????? '????????????' ?????? ?????? ???????????? ????????? 2????????? ??????
        if(System.currentTimeMillis() > pressedTime + 2000){
            //?????? ????????? pressedTime ??? ??????
            pressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"?????? ??? ????????? ??????", Toast.LENGTH_SHORT).show();
        }
        //????????? '????????????' ?????? ??????????????? ????????? '????????????' ?????? ?????? ???????????? ????????? 2????????? ?????????
        else{
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
}