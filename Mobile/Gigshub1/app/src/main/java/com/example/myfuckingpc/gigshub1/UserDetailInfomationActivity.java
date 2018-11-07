package com.example.myfuckingpc.gigshub1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chabbal.slidingdotsplash.SlidingSplashView;

import java.util.Arrays;

public class UserDetailInfomationActivity extends AppCompatActivity {
    private SlidingSplashView ssv;
    private int[] setImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_infomation);
        ssv = findViewById(R.id.ssv_details_user_view);
        setImage = new int[0];
        setImage = addElement(setImage, R.drawable.image_st2);
        setImage = addElement(setImage, R.drawable.image_st4);
        setImage = addElement(setImage, R.drawable.image_st3);
        setImage = addElement(setImage, R.drawable.image_st1);
        setImage = addElement(setImage, R.drawable.image_st5);
        ssv.setImageResources(setImage);
    }

    private int[] addElement(int[] a, int e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    public void clickToAcceptUser(View view) {
        Toast.makeText(this, "User's request was accepted", Toast.LENGTH_SHORT).show();
        final Intent data = new Intent();
        // Truyền data vào intent
        data.putExtra("OK","OK");
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void clickToDenyUser(View view) {
        Toast.makeText(this, "User's request was denied", Toast.LENGTH_SHORT).show();
        finish();
    }
}
