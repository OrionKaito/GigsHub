package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VerifyEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_event);
    }

    public void clickToVerify(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", 3);

        Intent intent = new Intent(VerifyEventActivity.this, DetailGigsActivity.class);

        intent.putExtras(bundle);
        startActivity(intent);

    }
}
