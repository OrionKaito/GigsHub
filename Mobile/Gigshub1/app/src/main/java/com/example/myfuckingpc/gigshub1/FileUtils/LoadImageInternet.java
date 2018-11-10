package com.example.myfuckingpc.gigshub1.FileUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.BitSet;

public class LoadImageInternet extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public LoadImageInternet(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        String pathToFile = "http://192.168.1.213:8080"+urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(pathToFile).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        bmImage.setImageBitmap(bitmap);
    }
}
