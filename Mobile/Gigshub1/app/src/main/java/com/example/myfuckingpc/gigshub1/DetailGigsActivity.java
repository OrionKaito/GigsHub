package com.example.myfuckingpc.gigshub1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;



import com.chabbal.slidingdotsplash.SlidingSplashView;

import java.util.Arrays;

public class DetailGigsActivity extends AppCompatActivity {
    private TextView gigsname, title, date, time, descrip;
    private SlidingSplashView ssv_image;
    private int[] setImage;
    private ImageView iv_gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gigs);
        ssv_image = findViewById(R.id.ssv_detail_gigs_image);
        iv_gif = findViewById(R.id.iv_gigs_gif);
//        String gifUrl = "http://i.kinja-img.com/gawker-media/image/upload/s--B7tUiM5l--/gf2r69yorbdesguga10i.gif";
//        GlideApp
//                .with(this)
//                .load(gifUrl)
//                .error(R.drawable.ic_logo)
//                .into(iv_gif);
        setImage = new int[0];
        setImage = addElement(setImage, R.drawable.image_event_1);
        setImage = addElement(setImage, R.drawable.image_event_2);
        setImage = addElement(setImage, R.drawable.image_event_3);
        setImage = addElement(setImage, R.drawable.image_event_4);
        ssv_image.setImageResources(setImage);
    }

    private int[] addElement(int[] a, int e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

}
