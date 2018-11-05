package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AdminActivity extends AppCompatActivity {
    private LinearLayout ll_verified_account, ll_verified_list;
    private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7, rl8;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ll_verified_account = findViewById(R.id.ll_verify_request_list);
        ll_verified_list = findViewById(R.id.ll_verified_list);
        rl1 = findViewById(R.id.rl_user1);
        rl2 = findViewById(R.id.rl_user2);
        rl3 = findViewById(R.id.rl_user3);
        rl4 = findViewById(R.id.rl_user4);
        rl5 = findViewById(R.id.rl_user5);
        rl6 = findViewById(R.id.rl_user6);
        rl7 = findViewById(R.id.rl_user7);
        rl8 = findViewById(R.id.rl_user8);
        iv1 = findViewById(R.id.iv_accept_verify_1);
        iv2 = findViewById(R.id.iv_accept_verify_2);
        iv3 = findViewById(R.id.iv_accept_verify_3);
        iv4 = findViewById(R.id.iv_accept_verify_4);
        iv5 = findViewById(R.id.iv_cancel_verify_5);
        iv6 = findViewById(R.id.iv_cancel_verify_6);
        iv7 = findViewById(R.id.iv_cancel_verify_7);
        iv8 = findViewById(R.id.iv_cancel_verify_8);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl1.setVisibility(View.GONE);
                rl5.setVisibility(View.VISIBLE);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl2.setVisibility(View.GONE);
                rl6.setVisibility(View.VISIBLE);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl3.setVisibility(View.GONE);
                rl7.setVisibility(View.VISIBLE);
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl4.setVisibility(View.GONE);
                rl8.setVisibility(View.VISIBLE);
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl1.setVisibility(View.VISIBLE);
                rl5.setVisibility(View.GONE);
            }
        });
        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl2.setVisibility(View.VISIBLE);
                rl6.setVisibility(View.GONE);
            }
        });
        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl3.setVisibility(View.VISIBLE);
                rl7.setVisibility(View.GONE);
            }
        });
        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl4.setVisibility(View.VISIBLE);
                rl8.setVisibility(View.GONE);
            }
        });

    }

    public void clickToViewListUser(View view) {
        ll_verified_account.setVisibility(View.VISIBLE);
        ll_verified_list.setVisibility(View.GONE);
    }

    public void clickToViewListUserVerify(View view) {
        ll_verified_list.setVisibility(View.VISIBLE);
        ll_verified_account.setVisibility(View.GONE);
    }
}
