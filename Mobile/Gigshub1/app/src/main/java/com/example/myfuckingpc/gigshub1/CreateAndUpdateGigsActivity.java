package com.example.myfuckingpc.gigshub1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateAndUpdateGigsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_update_gigs);
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
