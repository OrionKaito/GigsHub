package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chabbal.slidingdotsplash.SlidingSplashView;

import java.util.Arrays;

public class DetailGigsActivity extends AppCompatActivity {
    private TextView gigsname, title, date, time, descrip;
    private SlidingSplashView ssv_image;
    private int[] setImage;
    private ImageView iv_gif;
    private int typeUser;
    private static final int USER = 1;
    private static final int ADMIN = 2;
    private LinearLayout ll_action;
    private LinearLayout ll_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gigs);
        typeUser = USER;
        ssv_image = findViewById(R.id.ssv_detail_gigs_image);
        iv_gif = findViewById(R.id.iv_gigs_gif);
        ll_action = findViewById(R.id.ll_action_button);
        ll_update = findViewById(R.id.ll_update_button);
        setImage = new int[0];
        setImage = addElement(setImage, R.drawable.image_event_1);
        setImage = addElement(setImage, R.drawable.image_event_2);
        setImage = addElement(setImage, R.drawable.image_event_3);
        setImage = addElement(setImage, R.drawable.image_event_4);
        ssv_image.setImageResources(setImage);
        if (typeUser == ADMIN) {
            ll_action.setVisibility(View.GONE);
            ll_update.setVisibility(View.VISIBLE);
        } else {
            ll_action.setVisibility(View.VISIBLE);
            ll_update.setVisibility(View.GONE);
        }
    }

    private int[] addElement(int[] a, int e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    public void clickToBuyTicket(View view) {
        Intent intent = new Intent(DetailGigsActivity.this, BuyTicketActivity.class);
        startActivity(intent);
    }

    public void clickToOpenMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
