package com.example.myfuckingpc.gigshub1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.Adapter.EventSearchAdapter;
import com.example.myfuckingpc.gigshub1.EventSearch;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class UserFollowActivity extends AppCompatActivity {
    private RecyclerView rv_list;
    private EventSearchAdapter adapter;
    private List<EventSearch> eventList;
    private Intent intent;
    private TextView tv_follow_type;
    private boolean isFollow;
    public static final int FOLLOWER = 4;
    public static final int FOLLOWING = 3;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_follow);
        isFollow = true;
        eventList = new ArrayList<>();
        rv_list = findViewById(R.id.rv_user_follow_list_event);
        adapter = new EventSearchAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(mLayoutManager);
        tv_follow_type = findViewById(R.id.tv_follow_type);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(adapter);
        listFollow();
        intent = new Intent(this, DetailGigsActivity.class);
        rv_list.addOnItemTouchListener(new RecyclerTouchListener(this, rv_list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE", 2);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void clickToReturnUserFollow(View view) {
        finish();
    }

    private void listFollow() {
        eventList.clear();
        List<EventSearch> data = new ArrayList<>();
        data.add(new EventSearch("Viral Fest Asia", "Award competition for young singer for asia singer, band. Now return to Bankok, Thailand", "Sat, Jan 30, 2019", R.drawable.pop_event3));
        eventList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    public void clickToSetFollow(View view) {
        isFollow = !isFollow;
        if (isFollow) {
            tv_follow_type.setBackground(getResources().getDrawable(R.drawable.background_white_border));
            tv_follow_type.setTextColor(getResources().getColor(R.color.black));
            tv_follow_type.setText("Following");
        } else {
            tv_follow_type.setText("Follow");
            tv_follow_type.setBackground(getResources().getDrawable(R.drawable.background_camera_orange));
            tv_follow_type.setTextColor(getResources().getColor(R.color.white));

        }
    }


    public void clickToViewListUserFollowing(View view) {
        intent = new Intent(this, FollowActivity.class);
        intent.putExtra("TYPE", FOLLOWING);
        startActivity(intent);
    }

    public void clickToViewListUserFollower(View view) {
        intent = new Intent(this, FollowActivity.class);
        intent.putExtra("TYPE", FOLLOWER);
        startActivity(intent);
    }

    public void clickToViewVerify(View view) {
        Toast.makeText(this, "This profile was verified", Toast.LENGTH_SHORT).show();
    }
}
