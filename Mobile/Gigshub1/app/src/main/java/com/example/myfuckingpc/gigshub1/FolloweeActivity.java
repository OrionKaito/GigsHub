package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FolloweeActivity extends AppCompatActivity {
    private List<UserFollowee> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserFolloweeAdapter adapter;
    private TextView tv_title;
    private Intent intent;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followee);
        int check = getIntent().getIntExtra("TYPE", 0);
        recyclerView = findViewById(R.id.rv_user_follow);
        tv_title = findViewById(R.id.tv_title_follow);
        userList = new ArrayList<>();
        if (check == 2) {
            tv_title.setText("Following");
            userList.add(new UserFollowee(R.drawable.image_st3, "Sơn Tùng M-TP", "Nguyễn Thanh Tùng", true, true));
            userList.add(new UserFollowee(R.drawable.ic_personal2, "User B", "Fullname User B", false, true));
            userList.add(new UserFollowee(R.drawable.ic_personal3, "User C", "Fullname User C", false, true));
            userList.add(new UserFollowee(R.drawable.ic_personal4, "User D", "Fullname User D", true, true));
        } else {
            tv_title.setText("Follower");
            userList.add(new UserFollowee(R.drawable.ic_personal2, "User E", "Fullname User E", false, true));
            userList.add(new UserFollowee(R.drawable.ic_personal4, "User F", "Fullname User F", false, false));
            userList.add(new UserFollowee(R.drawable.ic_personal5, "User G", "Fullname User G", true, false));
            userList.add(new UserFollowee(R.drawable.ic_personal1, "User H", "Fullname User H", false, true));

        }
        intent = new Intent(this, UserFollowActivity.class);
        adapter = new UserFolloweeAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void clickToReturnFollowee(View view) {
        finish();
    }

    public void clickToViewUserDetail(View view) {
        Intent intent = new Intent(this, UserFollowActivity.class);
        startActivity(intent);
    }
}
