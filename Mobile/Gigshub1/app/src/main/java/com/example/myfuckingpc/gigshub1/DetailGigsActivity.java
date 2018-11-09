package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chabbal.slidingdotsplash.SlidingSplashView;

import java.util.Arrays;

public class DetailGigsActivity extends AppCompatActivity {
    private TextView gigsname, title, datetime, description, location, artist, type, accept, decline;
    private SlidingSplashView ssv_image;
    private int[] setImage;
    private int typeUser;
    private static final int USER = 1;
    private static final int ADMIN = 2;
    private static final int VERIFY = 3;
    private LinearLayout ll_action;
    private LinearLayout ll_update;
    private ImageView iv_price;
    private LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gigs);
        Bundle bundle = getIntent().getExtras();
        typeUser = bundle.getInt("TYPE");
        ssv_image = findViewById(R.id.ssv_detail_gigs_image);
        ll_action = findViewById(R.id.ll_action_button);
        ll_update = findViewById(R.id.ll_update_button);
        title = findViewById(R.id.tv_gigs_title);
        datetime = findViewById(R.id.tv_gigs_details_datetime);
        location = findViewById(R.id.tv_location);
        description = findViewById(R.id.tv_description);
        iv_price = findViewById(R.id.iv_buy_ticket);
        artist = findViewById(R.id.tv_gigs_details_artist);
        type = findViewById(R.id.tv_gigs_details_types);
        accept = findViewById(R.id.tv_join_event);
        decline = findViewById(R.id.tv_not_join);
        ll_back = findViewById(R.id.ll_back_button);
        if (typeUser == ADMIN) {
            ll_action.setVisibility(View.GONE);
            ll_update.setVisibility(View.GONE);
            iv_price.setVisibility(View.GONE);
            ll_back.setVisibility(View.VISIBLE);
            setImage = new int[0];
            setImage = addElement(setImage, R.drawable.pop_event3);
            setImage = addElement(setImage, R.drawable.admin_event_detai2);
            setImage = addElement(setImage, R.drawable.admin_event_detai3);
            setImage = addElement(setImage, R.drawable.admin_event_detail);
            setImage = addElement(setImage, R.drawable.admin_event_detai5);
            artist.setText("Sơn Tùng MTP");
            title.setText("Ultra Music Festival");
            datetime.setText("07:30PM \n24 Nov 2018");
            type.setText("Pop");
            location.setText("Crescent mall, District 7, Ho Chi Minh City");
            description.setText("ULTRA MUSIC FESTIVAL’S TWENTIETH ANNIVERSARY AFTERMOVIE HAS ARRIVED\n" +
                    "EVERYBODY'S GOING VIRAL\n" +
                    "\n" +
                    "2 days of event\n" +
                    "The inaugural Viral Fest Asia will be an annual festival celebrating the very best in music and video content coming from Asia for the digital space.\n" +
                    "\n" +
                    "30+ artists on stage\n" +
                    "More than 30 artistes on stage giving everyone maximum entertainment and fun during the Festival. You can't miss this opportunity to join the best Asian Artistes.\n" +
                    "\n" +
                    "16 experts on talks\n" +
                    "The Rise of Asia event will reunite over 100 experts of Digital Media Business from all over Asia. 8 hours of the best talks just for you.");
        } else if (typeUser == USER) {
            ll_action.setVisibility(View.VISIBLE);
            ll_update.setVisibility(View.GONE);
            setImage = new int[0];
            setImage = addElement(setImage, R.drawable.user_event_detail1);
            setImage = addElement(setImage, R.drawable.user_event_detail2);
            setImage = addElement(setImage, R.drawable.user_event_detail3);
            setImage = addElement(setImage, R.drawable.user_event_detail4);
            title.setText("Ultra Music Festival");
            datetime.setText("07:30PM \n29 March 2019");
            location.setText("Miami, USA");
            artist.setText("Marshmallow");
            type.setText("EDM");
            description.setText("2019 TICKETS ON SALE NOW\n" +
                    "Following Ultra Music Festival’s record-breaking 20th anniversary this past March, the now three-time #1 festival award winner* presents its mind-blowing 2018 Official Aftermovie, alongside the 2019 ticket on sale release.\n" +
                    "\n" +
                    "Ultra’s longstanding home, Bayfront Park is captured through the lens of critically acclaimed filmmaker FINAL KID, who immerses the viewer in a state of pure euphoria and exquisite detail by way of RED 8K Cameras. Exceeding the quality of its predecessor’s, this year’s Aftermovie showcases ULTRA’s monumental production, star-studded lineup and breath-taking scenery, all of which have solidified the festival’s position as the world’s premier electronic music festival.");
        } else if (typeUser == VERIFY) {
            ll_update.setVisibility(View.VISIBLE);
            ll_action.setVisibility(View.GONE);
            accept.setText("ACCEPT");
            decline.setText("DECLINE");
            setImage = new int[0];
            setImage = addElement(setImage, R.drawable.pop_event2);
            setImage = addElement(setImage, R.drawable.pop_event1);
            setImage = addElement(setImage, R.drawable.pop_event3);
            setImage = addElement(setImage, R.drawable.pop_event4);
            title.setText("Music Contest in HCM");
            type.setText("Rock");
            artist.setText("Black Pink");
            datetime.setText("07:30PM \n29 March 2019");
            location.setText("Tan Binh, Ho Chi Minh");
            description.setText("This is an music event, have many singers, celebs, artist from over the world");
        } else if (typeUser == 4) {
            ll_action.setVisibility(View.GONE);
            ll_update.setVisibility(View.VISIBLE);
            setImage = new int[0];
            setImage = addElement(setImage, R.drawable.search_edm_4);
            setImage = addElement(setImage, R.drawable.search_edm_1);
            setImage = addElement(setImage, R.drawable.search_edm_3);
            setImage = addElement(setImage, R.drawable.search_edm_2);
            artist.setText("Bích Phương, Chainsmokers");
            title.setText("Memory Asia Tour");
            datetime.setText("07:30PM \n14 Sep 2018");
            location.setText("District 2, Ho Chi Minh City");
            description.setText("This is the first Asian tour of The Chainsmokers in 2019. In Vietnam, the tour will take place on September 14th at the Football Stadium (3ha). District Administrative Center (Truong Van Bang Street, Thanh Ward My Loi, District 2, Ho Chi Minh City.\n" +
                    "\n" +
                    "Tour in Ho Chi Minh City on 14/9/2017. Tickets will commence on the first sale from 02 - 04/08/2017\n" +
                    "\n" +
                    "Five months ago, the DJ duo, including Alex Pall and Drew Taggart, kicked off the \"Memories ... Do Not Open\" tour of 40 US cities, starting at the American Airlines Arena in Miami. So far, the duo's attraction # 18 DJ Mag is not only in the United States but has swept across the Asian and Canadian countries as the shows are always covered by the ticket fire.");
        } else {
            ll_action.setVisibility(View.VISIBLE);
            ll_update.setVisibility(View.GONE);
            setImage = new int[0];
            setImage = addElement(setImage, R.drawable.image_bp1);
            setImage = addElement(setImage, R.drawable.edm_event5);
            setImage = addElement(setImage, R.drawable.search_event_detail1);
            setImage = addElement(setImage, R.drawable.edm_event2);
        }
        ssv_image.setImageResources(setImage);
    }

    private int[] addElement(int[] a, int e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    public void clickToUpdateEvent(View view) {
        Intent intent = new Intent(this, UpdateGigsActivity.class);
        startActivity(intent);
    }

    public void clickToOpenMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void clickToOpenComment(View view) {
        Intent intent = new Intent(this, CommentActivity.class);
        startActivity(intent);
    }

    public void clickToBuyTicket(View view) {
        Intent intent = new Intent(this, BuyTicketActivity.class);
        startActivity(intent);
    }

    public void clickToAddEvent(View view) {

        if (typeUser == VERIFY) {
            Toast.makeText(this, "This event accepted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You add new event you want to participate", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void clickToExit(View view) {
        finish();
    }

}
