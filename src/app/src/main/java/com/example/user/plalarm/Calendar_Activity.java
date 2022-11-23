package com.example.user.plalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.LocalDate;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Calendar_Activity extends AppCompatActivity implements OnDateSelectedListener{

    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (MaterialCalendarView) findViewById(R.id.material_calendar);
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.setOnDateChangedListener(this);
        calendarView.addDecorators(new SundayDecorator(),new SaturdayDecorator());

        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append("년 ")
                        .append(calendarHeaderElements[1])
                        .append("월");
                return calendarHeaderBuilder.toString();
            }
        });

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Calendar_Activity.this);//alertdialog생성
        String[] date_format = getDate(date);

        builder.setTitle(date_format[0]+"년 " + date_format[1]+"월 " + date_format[2] + "일");//제목
        builder.setMessage("데이터 베이스 연동하면 내용 출력");//내용 (DB연결해서 출력)

        builder.setPositiveButton("일정 추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {//일정 추가 버튼 누르면 설정화면 전달
//                ArrayList<String> send_data = new ArrayList<String>(Arrays.asList(date_format));//배열을 arraylist로 변환
//                Intent intent = new Intent(Calendar_Activity.this, MainActivity.class);//임의로 메인화면
//                intent.putStringArrayListExtra("Array",send_data);
//                startActivity(intent);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //구현 x
            }
        });
        builder.show();
        //Toast.makeText(getApplicationContext(),"2"+date+"3"+selected,Toast.LENGTH_LONG).show();
    }
    public String[] getDate(CalendarDay date){//터치 한 날짜 값 파싱
        String parse = date.toString();//date를 문자로 변환
        int start_num = parse.indexOf("{")+1;//{의 다음 위치값
        String result = parse.substring(start_num,(parse.substring(start_num).indexOf("}")+start_num));//{}안에 들어있는 데이터 뽑아오기
        String date_format[] = result.split("-");//-기준으로 split
        return date_format;
    }

}