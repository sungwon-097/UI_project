package com.example.user.plalarm.fragment;

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

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private EventList eventList = new EventList();

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
        holder.startTime.setText(event.getStartTime());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setItems(EventList eventList){
        this.eventList = (EventList) eventList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, startTime;

        ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.day_title);
            startTime = itemView.findViewById(R.id.day_time);
        }
    }

}
