package com.example.user.plalarm.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.plalarm.R;
import com.example.user.plalarm.model.Event;

import java.util.ArrayList;

public class Calendar_Adapter extends BaseAdapter{
    ArrayList<Event> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Event getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;//return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_calendar_list,parent,false);
        }

        TextView title = (TextView)convertView.findViewById(R.id.list_title);
        TextView start_time = (TextView)convertView.findViewById(R.id.list_start);
        TextView end_time = (TextView)convertView.findViewById(R.id.list_end);

        Event event = items.get(position);

        title.setText(event.getTitle());
        start_time.setText(parsing_date(event.getStartTime()));
        end_time.setText(parsing_date(event.getEndTime()));

        return convertView;
    }
    public void addItem(Event event){

        Event i = new Event();
        i.setTitle(event.getTitle());
        i.setContent(event.getContent());
        i.setStartTime(event.getStartTime());
        i.setEndTime(event.getEndTime());
        i.setIntentApp(event.getIntentApp());

        items.add(i);
    }
    public String parsing_date(String s) {
        String result;
        String[] date_arr, clock_arr;
        String[] parse = s.split("T");
        date_arr = parse[0].split("-");
        clock_arr = parse[1].split(":");
        return clock_arr[0] + "시 " + clock_arr[1] + "분 ";
    }
}
