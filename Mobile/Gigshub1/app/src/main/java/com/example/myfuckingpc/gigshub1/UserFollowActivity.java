package com.example.myfuckingpc.gigshub1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class UserFollowActivity extends AppCompatActivity {
    private RecyclerView rv_list;
    private EventSearchAdapter adapter;
    private List<EventSearch> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_follow);
        eventList = new ArrayList<>();
        rv_list = findViewById(R.id.rv_user_follow_list_event);
        adapter = new EventSearchAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(adapter);
        listFollow();
    }

    public void clickToReturnUserFollow(View view) {
        finish();
    }

    private void listFollow() {
        eventList.clear();
        List<EventSearch> data = new ArrayList<>();
        data.add(new EventSearch("Hyperplay", "Day trips to world class attractions, and a chance to compete for the Grand Prize against the best of Southeast Asia", "Thu, Sep 13, 2019", R.drawable.pop_event4));
        data.add(new EventSearch("Countdown NYE", "There are few better ways to welcome in the new year than at a huge Insomniac party. Brought to San Bernardino by the incredible minds behind Electric Daisy Carnival, Nocturnal Wonderland, Escape, Life is Beautiful, Dreamstate and Middlelands", "Sun, Dec 31, 2018", R.drawable.edm_event1));
        data.add(new EventSearch("Electric Zoo", "Randall's Island, East Manhattan, parks a full-scale electronic festival right in the heart of New York City", "Sun, Mar 2, 2019", R.drawable.edm_event2));
        data.add(new EventSearch("Black Sun Empire", "Returning in late December on the beautiful west coast of Vietnam, the electronic dance music festival extravaganza EPIZODE³ will be welcoming the bigges", "Sun, Dec 20, 2018", R.drawable.rock_event2));
        data.add(new EventSearch("Viral Fest Asia", "Award competition for young singer for asia singer, band. Now return to Bankok, Thailand", "Sat, Jan 30, 2019", R.drawable.pop_event3));
        data.add(new EventSearch("Hyperplay", "Day trips to world class attractions, and a chance to compete for the Grand Prize against the best of Southeast Asia", "Thu, Sep 13, 2019", R.drawable.pop_event4));
        data.add(new EventSearch("Rock Concert 2018 Vietnam", "Rock Concert 2018 với chủ đề “Battleship” (tạm dịch là Chiến hạm) sẽ ra mắt khán giả yêu rock năm đầu tiên với 2 live-show vào các ngày 26-4 tại sân vận động Hàng Đẫy (Hà Nội) ", "Mon, April 26, 2019", R.drawable.rock_event1));
        data.add(new EventSearch("Vietnam’s Epizode festival enlists ", "The end-of-year EDM showdown is on! Vietnam’s Epizode festival is coming back with an epic 2017 edition", "Sat, Jan 20, 2019", R.drawable.rock_event3));
        eventList.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
