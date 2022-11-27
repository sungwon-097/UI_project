package com.example.user.plalarm.fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.plalarm.R;
import com.example.user.plalarm.SampleData;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayFragment extends Fragment {

    TextView current_day;
    private MaterialCalendarView calendarView;
    RecyclerView recyclerView;
    private final DayAdapter adapter = new DayAdapter();

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

        adapter.setItems(new SampleData().getEventItems());

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