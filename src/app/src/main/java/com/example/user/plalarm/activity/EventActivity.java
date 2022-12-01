package com.example.user.plalarm.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.example.user.plalarm.R;
import com.example.user.plalarm.model.Event;
import java.time.LocalDateTime;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity{

    Activity act = this;
    EditText title, content;
    NumberPicker startYear, startMonth, startDate, startHour, startMinute, endHour, endMinute;
    LocalDateTime startDt, endDt;
    Button submitButton;
    String intentApp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Calendar calendar = Calendar.getInstance();

        title = findViewById(R.id.editTitle);
        content = findViewById(R.id.editContent);

        startYear = findViewById(R.id.startYear);
        startMonth = findViewById(R.id.startMonth);
        startDate = findViewById(R.id.startDate);
        startHour = findViewById(R.id.startHour);
        startMinute = findViewById(R.id.startMinute);

        endHour = findViewById(R.id.endHour);
        endMinute = findViewById(R.id.endMinute);

        submitButton = findViewById(R.id.submitButton);

        setPicker(startYear, 2100, 1900, calendar.get(Calendar.YEAR));
        setPicker(startMonth, 12, 1, calendar.get(Calendar.MONTH)+1);
        setPicker(startDate, 31, 1, calendar.get(Calendar.DATE));
        setPicker(startHour, 23, 0, calendar.get(Calendar.HOUR_OF_DAY));
        setPicker(startMinute, 60, 0, calendar.get(Calendar.MINUTE));

        setPicker(endHour, 23, 0, calendar.get(Calendar.HOUR_OF_DAY));
        setPicker(endMinute, 60, 0, calendar.get(Calendar.MINUTE));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                startDt = LocalDateTime.of(startYear.getValue(), startMonth.getValue(), startDate.getValue(), startHour.getValue(), startMinute.getValue());
                endDt = LocalDateTime.of(startYear.getValue(), startMonth.getValue(), startDate.getValue(), endHour.getValue(), endMinute.getValue());
                String startDateToString = startDt.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String endDateToString = endDt.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                Event event = new Event(title.getText().toString(), content.getText().toString(), startDateToString, endDateToString, intentApp);

                Intent intent = new Intent(EventActivity.this, IntentActivity.class);
                intent.putExtra("user", event);
                startActivity(intent);
            }
        });
    }
    void setPicker(NumberPicker picker, int maxVal, int minVal, int initVal){
        picker.setMaxValue(maxVal);
        picker.setMinValue(minVal);
        picker.setValue(initVal);
    }
}