package com.example.user.plalarm.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.plalarm.EventListDAO;
import com.example.user.plalarm.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayFragment extends Fragment {

    TextView current_day;
    RecyclerView recyclerView;
    String collectionPath = "test"; // TODO : 경로를 핸들링 해야 함

    private final DayAdapter adapter = new DayAdapter();

    LocalDate localDate = LocalDate.now();
    String currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        ArrayList<String> Week = new ArrayList<>(Arrays.asList("일요일","월요일","화요일"
                ,"수요일","목요일","금요일","토요일","일요일"));

        current_day = (TextView)view.findViewById(R.id.check_day);
        recyclerView = view.findViewById(R.id.recycler_view);

        current_day.setText(getCurrentDate() + " " + Week.get(getCurrentWeek()-1));

        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setItems(new EventListDAO(collectionPath).getDayEventItems(currentDate));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static String getCurrentDate(){
        Date dateNow = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        return format.format(dateNow);
    }
    public static int getCurrentWeek(){
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}