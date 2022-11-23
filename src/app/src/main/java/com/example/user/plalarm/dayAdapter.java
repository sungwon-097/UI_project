package com.example.user.plalarm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class dayAdapter extends RecyclerView.Adapter<dayAdapter.ViewHolder> {
    private ArrayList<ListItem> items = new ArrayList<>();

    @NonNull
    @Override
    public dayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull dayAdapter.ViewHolder holder, int position) {
        ListItem item = items.get(position);

        holder.title.setText(item.getTitle());
        holder.worktime.setText(item.getWorktime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<ListItem> items){
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, worktime;

        ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.day_title);
            worktime = itemView.findViewById(R.id.day_time);
        }
    }

}
