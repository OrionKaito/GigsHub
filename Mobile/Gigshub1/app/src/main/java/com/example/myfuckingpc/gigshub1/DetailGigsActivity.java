package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailGigsActivity extends AppCompatActivity {
    private TextView gigsname, title, date, time, descrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gigs);
        gigsname = findViewById(R.id.txtGigsName);
        title = findViewById(R.id.txtTitle);
        date = findViewById(R.id.txtDate);
        time = findViewById(R.id.txtTime);
        descrip = findViewById(R.id.txtDescription);
        Intent intent = getIntent();
        gigsname.setText(intent.getStringExtra("gigsname"));
        title.setText(intent.getStringExtra("title"));
        date.setText(intent.getStringExtra("date"));
        time.setText(intent.getStringExtra("time"));
        descrip.setText(intent.getStringExtra("descrip"));
    }
}
