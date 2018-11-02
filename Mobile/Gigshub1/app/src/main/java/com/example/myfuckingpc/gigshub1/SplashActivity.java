package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myfuckingpc.gigshub1.model.SavedToken;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Intent intentLogin = new Intent(this, LoginActivity.class);
        final Intent intentMain = new Intent(this, MainActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(SavedToken.getUserToken(SplashActivity.this).equals("")){
                    startActivity(intentLogin);
                    finish();
                }
                else {
                    startActivity(intentMain);
                    finish();
                }

            }
        }, 2000);

    }
}
