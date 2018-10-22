package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseEventTypeActivity extends AppCompatActivity {
    private Spinner spnChoose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event_type);
        spnChoose = findViewById(R.id.spnEventType);
        List<String> listType = new ArrayList<>();
        listType.add("Music Concern");
        listType.add("Talk Show");
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listType);

        spnChoose.setAdapter(spnAdapter);
    }

    public void clickToSubmit(View view) {
        String type = spnChoose.getSelectedItem().toString();
        if(type.equals("Music Concern")){
            Intent intent = new Intent(this, CreateMusicGigsActivity.class);
            startActivity(intent);
        }
        else if(type.equals("Talk Show")){
            Intent intent = new Intent(this, CreateTalkshowGigsActivity.class);
            startActivity(intent);
        }
        else {
            return;
        }
    }
}
