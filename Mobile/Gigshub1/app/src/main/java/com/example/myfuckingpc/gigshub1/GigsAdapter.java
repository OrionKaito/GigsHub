package com.example.myfuckingpc.gigshub1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.model.Event;

import java.io.File;
import java.util.List;

public class GigsAdapter extends RecyclerView.Adapter<GigsAdapter.MyViewHolder> {
    private List<Event> gigsList;
    Bitmap bitmap = null;

    public GigsAdapter(List<Event> gigsList) {
        this.gigsList = gigsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, location, number, hosted, category, artist, price;
        public ImageView image;
        public RatingBar star;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_gigs_title);
            location = view.findViewById(R.id.tv_gigs_place);
            number = view.findViewById(R.id.tv_gigs_number);
            star = view.findViewById(R.id.rb_gigs_star);
            image = view.findViewById(R.id.iv_gigs);
            hosted = view.findViewById(R.id.tv_hosted_user);
            category = view.findViewById(R.id.tv_gigs_category);
            artist = view.findViewById(R.id.tv_gigs_artist);
            price = view.findViewById(R.id.tv_gigs_price);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_gigs, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event gigs = gigsList.get(position);
        holder.title.setText(gigs.getTitle());
        holder.location.setText(gigs.getAddress()+", "+gigs.getCity());
        holder.number.setText(gigs.getNumberOfAttender() + " people will go");
        holder.star.setRating(gigs.getRating().floatValue());
        File imgFile = new File(gigs.getImgPath());


        if(imgFile.exists()){
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        holder.image.setImageBitmap(bitmap);
        holder.hosted.setText("Hosted by: "+gigs.getOwnerName());
        holder.artist.setText("Artist: "+gigs.getArtist());
        holder.category.setText("Category: "+gigs.getCategory());
        if(gigs.getIsSale()==false){
            holder.price.setText("FREE");
        }
        else {
            holder.price.setText(gigs.getPrice().intValue());
        }


    }

    @Override
    public int getItemCount() {
        return gigsList.size();
    }
    public void updateAnswers(List<Event> items) {
        gigsList = items;
        notifyDataSetChanged();
    }
}
