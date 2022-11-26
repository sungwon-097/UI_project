package com.example.user.plalarm.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;

import com.example.user.plalarm.ListItem;
import com.example.user.plalarm.R;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private ArrayList<ListItem> items = new ArrayList<>();

    @NonNull
    @Override
    public DayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapter.ViewHolder holder, int position) {
        ListItem item = items.get(position);

        holder.title.setText(item.getTitle());
        holder.workTime.setText(item.getWorktime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<ListItem> items){
        this.items = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, workTime;

        ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.day_title);
            workTime = itemView.findViewById(R.id.day_time);
        }
    }

}
