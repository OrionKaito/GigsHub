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
        if (typeUser == ADMIN) {
            ll_action.setVisibility(View.GONE);
            ll_update.setVisibility(View.VISIBLE);
            iv_price.setVisibility(View.GONE);
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
            ll_action.setVisibility(View.VISIBLE);
            accept.setText("ACCEPT");
            decline.setText("DECLINE");
            ll_update.setVisibility(View.GONE);
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
        } else {
            ll_action.setVisibility(View.VISIBLE);
            ll_update.setVisibility(View.GONE);
            setImage = new int[0];
            setImage = addElement(setImage, R.drawable.search_event_detail2);
            setImage = addElement(setImage, R.drawable.search_event_detail3);
            setImage = addElement(setImage, R.drawable.search_event_detail1);
            setImage = addElement(setImage, R.drawable.edm_event2);
            title.setText("Electric Zoo");
            datetime.setText("05:30PM \n17 Dec 2018");
            location.setText("New York, USA");
            description.setText("About Electric Zoo\n" +
                    "\n" +
                    "Established in 2009 by Made Event, the internationally renowned Electric Zoo Festival is one of New York City’s largest music festivals and features the top names in electronic music, bringing a wide variety of acts from around the world and across the spectrum of electronic music’s various sub-genres. International editions of Electric Zoo have taken place in Mexico City, Tokyo, Shanghai and São Paulo. This year’s 10th annual New York edition, themed ‘The Big Ten’ will take place on Aug 31/Sep 1-2 at Randall’s Island Park.\n" +
                    "\n" +
                    "About Electric Zoo: The Big Ten\n" +
                    "\n" +
                    "All those moons ago, who’da thought that the weekender that roared to life on NYC’s Randall’s Island in 2009 —which featured a fresh faced deadmau5, a pre-cake Aoki and the late-great Frankie Knuckles—would become a household name in the global dance festival scene?\n" +
                    "\n" +
                    "We’ve come a long, long way together, through the hard times and the good. From our earliest expeditions through the chaotic urban jungle, to scaling the feral lands of the Wild Island, and getting our minds blown by the gritty 6th Boro. What a ride it’s been!\n" +
                    "\n" +
                    "Since our first outing in 2009, we’ve been welcome all across the globe, taking our brand to Shanghai, Tokyo, Sao Paulo, and Mexico City.\n" +
                    "\n" +
                    "But New York City, baby, you’ll always be our home.\n" +
                    "\n" +
                    "From your great gleaming skyscrapers to your filthy-a** subway. The neon lights of Time Square to the fading memories of Coney Island. From the famous fuggedaboutit wiseguys to the lifelong friendships forged on dance floors. New York, you are the Big Apple in our eye, and our constant source of inspiration. It’s been an honor to make you dance.\n" +
                    "\n" +
                    "2018 is Electric Zoo’s 10th birthday, and we’re going to make sure that this is the biggest, wildest fiesta that we’ve ever thrown down! #EzooTen will be a blow-out of epic proportions, featuring the world’s finest DJs and most mind-melting production in the heart of The City That Never Sleeps.\n" +
                    "\n" +
                    "We are preparing the biggest party New York City has ever seen. At Electric Zoo: The BIG 10 we want to dance with all of our friends at once. All the hardcore heroes from the past decade, and the fledgling fauns making their first trip to the island. We want to see the old skool trance tigers, the crunchy bass bears, the techno penguins, and the classic house hippos, all returning to the watering hole to shake it out again, and soak up the infectious vibes.\n" +
                    "\n" +
                    "We can’t wait to dance with you again New York!.");
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
