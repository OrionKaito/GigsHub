package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateMusicGigsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_music_gigs);
    }


    public void clickToShowTime(View view) {
    }

    public void clickToShowDate(View view) {
    }

    public void clickToAddImage(View view) {
    }

    public void clickToCreate(View view) {
        Intent intent = new Intent(this, DetailGigsActivity.class);
        startActivity(intent);
    }
}
