package com.example.myfuckingpc.gigshub1.Adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myfuckingpc.gigshub1.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private List<Bitmap> imageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_display;

        public MyViewHolder(View view) {
            super(view);
            iv_display = view.findViewById(R.id.iv_image);

        }
    }

    public ImageAdapter(List<Bitmap> imageList) {
        this.imageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_image, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bitmap image = imageList.get(position);
        holder.iv_display.setImageBitmap(image);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}