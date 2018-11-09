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
import com.example.myfuckingpc.gigshub1.FileUtils.LoadImageInternet;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.EventClient;
import com.example.myfuckingpc.gigshub1.model.EventItem;

import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailGigsActivity extends AppCompatActivity {
    private TextView title, datetime, description, location, category, price, artist, numberAttender, hostedUsername;
    private ImageView ssv_image;
    private int[] setImage;
    private int typeUser;
    long eventId;

    private static final int USER = 1;
    private static final int OWNER = 2;
    private LinearLayout ll_action;
    private LinearLayout ll_update;
    private ImageView iv_price;
    EventItem eventItem = new EventItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gigs);
        Bundle bundle = getIntent().getExtras();
        typeUser = bundle.getInt("TYPE");
        eventId = bundle.getLong("EventId");
        ssv_image = findViewById(R.id.ssv_detail_gigs_image);
        ll_action = findViewById(R.id.ll_action_button);
        ll_update = findViewById(R.id.ll_update_button);
        title = findViewById(R.id.tv_gigs_title);
        datetime = findViewById(R.id.tv_gigs_details_datetime);
        location = findViewById(R.id.tv_location);
        description = findViewById(R.id.tv_description);
        iv_price = findViewById(R.id.iv_buy_ticket);
        category = findViewById(R.id.tv_gigs_details_category);
        price = findViewById(R.id.tv_gigs_details_price);
        artist = findViewById(R.id.tv_gigs_details_artist);
        numberAttender = findViewById(R.id.tv_gigs_details_number_of_attender);
        hostedUsername = findViewById(R.id.tv_gigs_hosted_name);
        //get event information
        final EventClient service = ApiUtils.eventClient();
        Call<EventItem> call = service.getEventById(eventId);
        call.enqueue(new Callback<EventItem>() {
            @Override
            public void onResponse(Call<EventItem> call, Response<EventItem> response) {
                if (response.isSuccessful()) {
                    eventItem = response.body();
                    String url = eventItem.getImgPath();
                    LoadImageInternet load = new LoadImageInternet(ssv_image);
                    load.execute(url);
                    title.setText(eventItem.getTitle());
                    datetime.setText(eventItem.getDate()+"\n"+eventItem.getTime());
                    location.setText(eventItem.getAddress()+", "+eventItem.getCity());
                    description.setText(eventItem.getDescription());
                    category.setText(eventItem.getCategory());
                    if(eventItem.getPrice() != 0){
                        price.setText("Tiket price: "+eventItem.getPrice().toString()+"$");
                    }
                    artist.setText(eventItem.getArtist());
                    numberAttender.setText(eventItem.getNumberOfAttender()+" people will go");
                    hostedUsername.setText(eventItem.getOwnerName());


                }
                else {
                    Toast.makeText(DetailGigsActivity.this, "CONNECTED", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventItem> call, Throwable t) {
                Toast.makeText(DetailGigsActivity.this, "K", Toast.LENGTH_SHORT).show();
            }
        });


        if (typeUser == OWNER) {
            ll_action.setVisibility(View.GONE);
            ll_update.setVisibility(View.VISIBLE);
            iv_price.setVisibility(View.GONE);


        } else if (typeUser == USER) {
            ll_action.setVisibility(View.VISIBLE);
            ll_update.setVisibility(View.GONE);

        }else {
            ll_action.setVisibility(View.VISIBLE);
            ll_update.setVisibility(View.GONE);
        }
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
        Toast.makeText(this, "You add new event you want to participate", Toast.LENGTH_SHORT).show();
        finish();
    }
}
