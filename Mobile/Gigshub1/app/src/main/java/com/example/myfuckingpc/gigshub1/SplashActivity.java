package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myfuckingpc.gigshub1.model.SavedToken;

public class SplashActivity extends AppCompatActivity {
    String[] arrInfo = {"",""};
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Intent intentLogin = new Intent(this, LoginActivity.class);
        final Intent intentMain = new Intent(this, MainActivity.class);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                arrInfo = SavedToken.getUserInfo(getApplicationContext()).split("[|]");
                if(arrInfo.length==1){
                    startActivity(intentLogin);
                    finish();
                }
                else {
                    String token = arrInfo[0].toString();
                    String username = arrInfo[1].toString();
                    if(token.equals("") || username.equals("")){
                        startActivity(intentLogin);
                        finish();
                    }
                    else {
                        startActivity(intentMain);
                        finish();
                    }
                }


            }
        }, 2000);

    }
}
