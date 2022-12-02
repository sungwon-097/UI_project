package com.example.user.plalarm.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.plalarm.R;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;

import java.util.ArrayList;

public class Event_calendar_Adapter extends BaseAdapter{
    ArrayList<Event> items = new ArrayList<>();
    //EventList eventList = new EventList();
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

        //EventList eventList = new EventList();
//        for(Event e:eventList.getEventList()){
//            title.setText(e.getTitle());
//            start_time.setText(e.getStartTime());
//            end_time.setText(e.getEndTime());
//        }
        Event event = items.get(position);

        title.setText(event.getTitle());
        start_time.setText(parsing_date(event.getStartTime()));
        end_time.setText(parsing_date(event.getEndTime()));

        return convertView;
    }
//    public void setItems(EventList eventList){
//        this.eventList = (EventList) eventList;
//    }

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
//        result = date_arr[0] + "년 " + date_arr[1] + "월 " + date_arr[2] + "일 "
//                + clock_arr[0] + "시 " + clock_arr[1] + "분 ";
        return clock_arr[0] + "시 " + clock_arr[1] + "분 ";
    }
}
