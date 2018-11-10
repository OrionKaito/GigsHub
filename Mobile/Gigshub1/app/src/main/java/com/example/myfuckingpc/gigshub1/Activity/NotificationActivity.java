package com.example.myfuckingpc.gigshub1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myfuckingpc.gigshub1.Notification;
import com.example.myfuckingpc.gigshub1.Adapter.NotificationAdapter;
import com.example.myfuckingpc.gigshub1.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notificationList);
        recyclerView = findViewById(R.id.rv_notification);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setGigsList();
        recyclerView.setAdapter(notificationAdapter);

    }

    private void setGigsList() {
        Notification notification = new Notification("FPT Graduation Ceremony", "Event change time start from 5:00PM to 7:00PM", "30 minutes ago", R.drawable.image_event_1);
        notificationList.add(notification);
        notification = new Notification("Sơn Tùng MTP", "Ready to Join? Event will start in 30 minutes", "23 minutes ago", R.drawable.event2);
        notificationList.add(notification);
        notificationAdapter.notifyDataSetChanged();
    }
}
