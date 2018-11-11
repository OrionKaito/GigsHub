package com.example.myfuckingpc.gigshub1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.FileUtils.LoadImageInternet;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.AttendClient;
import com.example.myfuckingpc.gigshub1.api.EventClient;
import com.example.myfuckingpc.gigshub1.model.Event;
import com.example.myfuckingpc.gigshub1.model.EventItem;
import com.example.myfuckingpc.gigshub1.model.SavedToken;

import java.util.ArrayList;
import java.util.List;

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
    private LinearLayout ll_update, ll_not_interest, ll_join;
    private ImageView iv_price;
    private List<EventItem> eventItems;
    private List<Event> eventList;
    private String token;

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
        ll_not_interest = findViewById(R.id.ll_detail_not_interest);
        ll_join = findViewById(R.id.ll_detail_join);
        String[] userInfoArr = SavedToken.getUserInfo(this).split("[|]");
        token = userInfoArr[0];
        //get event information
        eventList = new ArrayList<>();
        final EventClient service = ApiUtils.eventClient();
        Call<Event> call = service.getEventById(eventId);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                eventItems = response.body().getData();
                String url = eventItems.get(0).getImgPath();
                LoadImageInternet load = new LoadImageInternet(ssv_image);
                load.execute(url);
                title.setText(eventItems.get(0).getTitle());
                datetime.setText(eventItems.get(0).getDate()+"\n"+eventItems.get(0).getTime());
                location.setText(eventItems.get(0).getAddress()+", "+eventItems.get(0).getCity());
                description.setText(eventItems.get(0).getDescription());
                category.setText(eventItems.get(0).getCategory());
                if(eventItems.get(0).getPrice() != 0){
                    price.setText("Tiket price: "+eventItems.get(0).getPrice().toString()+"$");
                }
                artist.setText(eventItems.get(0).getArtist());
                numberAttender.setText(eventItems.get(0).getNumberOfAttender()+" people will go");
                hostedUsername.setText(eventItems.get(0).getOwnerName());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
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
        ll_not_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ll_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AttendClient service = ApiUtils.attend(token);
                Call<ResponseBody> call = service.attend(eventId);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DetailGigsActivity.this, "Successful follow: "+eventItems.get(0).getTitle() , Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else {
                            Toast.makeText(DetailGigsActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(DetailGigsActivity.this, "Network fail.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void clickToUpdateEvent(View view) {
        Intent intent = new Intent(this, UpdateGigsActivity.class);
        startActivity(intent);
    }

    public void clickToOpenMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("Address",eventItems.get(0).getAddress()+","+eventItems.get(0).getCity());
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
