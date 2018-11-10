package com.example.myfuckingpc.gigshub1.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myfuckingpc.gigshub1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateGigsActivity extends AppCompatActivity {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private TimePickerDialog timePickerDialog;
    private String date_time = "";
    private TextView tv_date_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gigs);
    }

    private void datePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = (dayOfMonth + "/" + monthOfYear + "/" + year);
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        mHour = hourOfDay;
                        mMinute = minute;
                        date_time += " " + hourOfDay + ":" + minute;
                        Date date = null;
                        try {
                            date = formatter.parse(date_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm");
                        tv_date_time.setText(formatter2.format(date));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

}
