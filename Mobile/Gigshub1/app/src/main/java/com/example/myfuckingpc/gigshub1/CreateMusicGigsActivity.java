package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMusicGigsActivity extends AppCompatActivity {
    private EditText gigsname, title, descrip,celeb,place,date,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_music_gigs);
        gigsname = findViewById(R.id.txtGigsName);
        title = findViewById(R.id.txtTitle);
        descrip = findViewById(R.id.txtDescription);
        celeb = findViewById(R.id.txtCelebName);
        place = findViewById(R.id.txtPlace);
        date = findViewById(R.id.txtDate);
        time = findViewById(R.id.txtTime);
    }


    public void clickToShowTime(View view) {
    }

    public void clickToShowDate(View view) {
    }

    public void clickToAddImage(View view) {
    }

    public void clickToCreate(View view) {
        Intent intent = new Intent(this, DetailGigsActivity.class);
        intent.putExtra("gigsname", gigsname.getText().toString());
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("descrip", descrip.getText().toString());
        intent.putExtra("celeb", celeb.getText().toString());
        intent.putExtra("place", place.getText().toString());
        intent.putExtra("date", date.getText().toString());
        intent.putExtra("time", time.getText().toString());
        startActivity(intent);
    }

    public void clickToCancel(View view) {
        onBackPressed();
    }

    public void clickToResetForm(View view) {
        gigsname.setText("");
        title.setText("");
        descrip.setText("");
        celeb.setText("");
        place.setText("");
        date.setText("");
        time.setText("");


    }
}
