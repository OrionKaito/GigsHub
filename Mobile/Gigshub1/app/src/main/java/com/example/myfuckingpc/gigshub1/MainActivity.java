package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import api.ApiUtils;
import api.CustomerClient;
import api.UserClient;
import model.SavedToken;
import model.UserInfomation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter fragmentPagerAdapter;
    private ImageView createEvent;
    CustomerClient customerClient = ApiUtils.getCustomerClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.vp_main);
        fragmentPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        PagerSlidingTabStrip tabsStrip = findViewById(R.id.tabs);
        tabsStrip.setShouldExpand(true);
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        createEvent = findViewById(R.id.imgCreateEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseEventTypeActivity.class);
                startActivity(intent);
            }
        });
        String token = SavedToken.getUserToken(MainActivity.this);
        String content_type = "application/json";
        String accept = "application/json";
        Call<UserInfomation> call = customerClient.getUserInfomation(token, content_type, accept);

        call.enqueue(new Callback<UserInfomation>() {
            @Override
            public void onResponse(Call<UserInfomation> call, Response<UserInfomation> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.body().getUserName(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Please NO!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfomation> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please NO!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
