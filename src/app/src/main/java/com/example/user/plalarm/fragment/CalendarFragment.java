package com.example.user.plalarm.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.user.plalarm.R;
import com.example.user.plalarm.activity.MainActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Calendar;

public class CalendarFragment extends Fragment implements OnDateSelectedListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.material_calendar);

        calendarView.setSelectedDate(CalendarDay.today());
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    //todo alertdialog 심폐소생술 중
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//alertdialog생성
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
        return result.split("-");
    }
}