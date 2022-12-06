package com.example.user.plalarm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;

import com.example.user.plalarm.R;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private EventList eventList = new EventList();
    LocalDate localDate = LocalDate.now();
    String currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    @NonNull
    @Override
    public DayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapter.ViewHolder holder, int position) {
        Event event = eventList.getEventList().get(position);

        holder.title.setText(event.getTitle());
        holder.startTime.setText(getGeneralTimeFormat(event.getStartTime()));
        holder.endTime.setText(getGeneralTimeFormat(event.getEndTime()));
    }

    public String getGeneralTimeFormat(String date){
        return date.substring(11, 13) +"시"+ date.substring(14, 16)+"분";
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setItems(EventList eventList){
        this.eventList = (EventList) eventList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, startTime, endTime;

        ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.day_title);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String collectionPath = "test";
                    Event event;
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("제목");
                    builder.setMessage("하이");
                    builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //
                        }
                    });
                    builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //
                        }
                    });
                    builder.show();
                }
            });
        }
    }

}
