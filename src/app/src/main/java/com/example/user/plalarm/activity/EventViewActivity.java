package com.example.user.plalarm.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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

public class EventViewActivity extends AppCompatActivity implements OnDateSelectedListener {

    ListView mListView;
    String collectionPath = "user";
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_event_view);
            mListView = (ListView) findViewById(R.id.day_list);

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
            ListView lv = (ListView)findViewById(R.id.day_list);
            Event_calendar_Adapter ilAdapter = new Event_calendar_Adapter();
            String[] date_format = getDate(date);
            Log.d("check date", date_format[0] + date_format[1] + date_format[2]);


            EventList eventList = new EventListDAO(collectionPath).getEventItems();
            lv.setAdapter(ilAdapter);
            for (Event e:eventList.getEventList()){
                String title = e.getTitle();
                String start_time = e.getStartTime();
                String end_time = e.getEndTime();
                if(Compare_Date(date_format,start_time)){
                    ilAdapter.addItem(title,parsing_date(start_time),parsing_date(end_time));
                }
                Log.d("check time",start_time +" " + Compare_Date(date_format,start_time) +"");
            }
        }
        public String[] getDate(CalendarDay date){//터치 한 날짜 값 파싱
            String parse = date.toString();//date를 문자로 변환
            int start_num = parse.indexOf("{")+1;//{의 다음 위치값
            String result = parse.substring(start_num,(parse.substring(start_num).indexOf("}")+start_num));//{}안에 들어있는 데이터 뽑아오기
            return result.split("-");
        }
        public boolean Compare_Date(String []date, String start){
            if(date[1].length() == 1) date[1] = "0" + date[1];
            if(date[2].length() == 1) date[2] = "0" + date[2];
            String sum = date[0] + "-" + date[1] + "-" + date[2];
            String[] compare = start.split("T");
            if(sum.equals(compare[0])){return true;}
            else return false;
        }
        public String parsing_date(String s){
                String result;
                String[] date_arr, clock_arr;
                String []parse = s.split("T");
                date_arr = parse[0].split("-");
                clock_arr = parse[1].split(":");
                result = date_arr[0] +"년 " + date_arr[1] + "월 " + date_arr[2] +"일 "
                        + clock_arr[0] + "시 " + clock_arr[1] +"분 ";
                return result;
        }

    }