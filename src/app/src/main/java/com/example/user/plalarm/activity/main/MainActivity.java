package com.example.user.plalarm.activity.main;

import static com.example.user.plalarm.config.UserInfo.userName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener, View.OnClickListener {

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
                EventList data = new EventListDAO(userName).getEventItems();
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

        EventList eventList = new EventListDAO(userName).getEventItems();
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
}