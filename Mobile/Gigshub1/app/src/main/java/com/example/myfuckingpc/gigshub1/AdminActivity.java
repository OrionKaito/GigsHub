package com.example.myfuckingpc.gigshub1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    private LinearLayout ll_verified_account, ll_verified_list;
    private RelativeLayout rl1;
    private TextView tv_user, tv_event;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ll_verified_account = findViewById(R.id.ll_verify_request_list);
        ll_verified_list = findViewById(R.id.ll_verify_event_list);
        tv_user = findViewById(R.id.tv_verify_user);
        tv_event = findViewById(R.id.tv_verify_event);
        rl1 = findViewById(R.id.rl_user1);
    }

    public void clickToViewListUser(View view) {
        ll_verified_account.setVisibility(View.VISIBLE);
        ll_verified_list.setVisibility(View.GONE);
        tv_user.setBackgroundColor(Color.BLACK);
        tv_user.setTextColor(Color.WHITE);
        tv_event.setBackgroundColor(Color.WHITE);
        tv_event.setTextColor(Color.BLACK);
    }

    public void clickToViewListUserVerify(View view) {
        ll_verified_list.setVisibility(View.VISIBLE);
        ll_verified_account.setVisibility(View.GONE);
        tv_event.setBackgroundColor(Color.BLACK);
        tv_event.setTextColor(Color.WHITE);
        tv_user.setBackgroundColor(Color.WHITE);
        tv_user.setTextColor(Color.BLACK);

    }


    public void clickToVerify(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", 3);
        intent = new Intent(this, DetailGigsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void clickToViewUser(View view) {
        intent = new Intent(this, UserDetailInfomationActivity.class);
        startActivityForResult(intent, 2);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                rl1.setVisibility(View.GONE);
            }
        }
    }
}
