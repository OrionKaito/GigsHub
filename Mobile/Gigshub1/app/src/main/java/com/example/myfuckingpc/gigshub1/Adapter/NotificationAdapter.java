package com.example.myfuckingpc.gigshub1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.Notification;
import com.example.myfuckingpc.gigshub1.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, time, detail;
        public CircleImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_notification_user);
            detail = view.findViewById(R.id.tv_notification_detail);
            time = view.findViewById(R.id.tv_notification_time);
            image = view.findViewById(R.id.civ_notification_image);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_notification, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Notification notification = notificationList.get(i);
        myViewHolder.image.setImageResource(notification.getImage());
        myViewHolder.name.setText(notification.getName());
        myViewHolder.time.setText(notification.getTime());
        myViewHolder.detail.setText(notification.getDetail());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
