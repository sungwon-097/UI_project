package com.example.user.plalarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.user.plalarm.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Spinner 선언
        Spinner spinner = findViewById(R.id.calendar_spinner);
        //기본 레이아웃 사용
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.calendar_order_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner 에 Adapter 넣음
        spinner.setAdapter(adapter);

        //SharedPreferences 에서 저장된 값 가져옴
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //초기 상태는 월간 View 로 설정, 저장값이 존재할 경우 저장값으로 설정
        int calendarSpinnerValue = sharedPref.getInt("calendarSpinner", 0);
        spinner.setSelection(calendarSpinnerValue);

        //Spinner 에서 값 지정
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //새로운 값 지정 시
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int userChoice = spinner.getSelectedItemPosition();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("calendarSpinner", userChoice);
                editor.apply();
            }
            //지정하지 않을 시
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}