package com.example.myfuckingpc.gigshub1.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.FileUtils.LoadImageInternet;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.model.EventItem;

import java.util.List;

public class GigsAdapter extends RecyclerView.Adapter<GigsAdapter.MyViewHolder> {
    private List<EventItem> gigsList;

    public GigsAdapter(List<EventItem> gigsList) {
        this.gigsList = gigsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, location, number, hosted, category, artist, price, time;
        public ImageView image, verified;
        public RatingBar star;
        String IMAGE_PATH;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_gigs_title);
            location = view.findViewById(R.id.tv_gigs_place);
            number = view.findViewById(R.id.tv_gigs_number);
            image = view.findViewById(R.id.iv_gigs);
            hosted = view.findViewById(R.id.tv_hosted_user);
            category = view.findViewById(R.id.tv_gigs_category);
            artist = view.findViewById(R.id.tv_gigs_artist);
            price = view.findViewById(R.id.tv_gigs_price_1);
            time = view.findViewById(R.id.tv_gigs_time);
            verified = view.findViewById(R.id.iv_is_verified);


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
        EventItem gigs = gigsList.get(position);
        holder.title.setText(gigs.getTitle());
        holder.location.setText(gigs.getAddress()+", "+gigs.getCity());
        holder.number.setText(gigs.getNumberOfAttender() + " people will go");
        String url = gigs.getImgPath();
        LoadImageInternet load = new LoadImageInternet(holder.image);
        load.execute(url);
        //Bitmap dr = ReadImage.LoadImageFromWebOperations("http://192.168.1.213:8080"+gigs.getImgPath());

        holder.hosted.setText("Hosted by: "+gigs.getOwnerName());
        holder.artist.setText("Artist: "+gigs.getArtist());
        holder.category.setText("Category: "+gigs.getCategory());
        if(gigs.getIsSale()== true){
            holder.price.setText("Ticket Price:"+gigs.getPrice().toString()+"$");
        }
        holder.time.setText(gigs.getDate()+","+gigs.getTime());
        if(gigs.getICusVerified()){
            holder.verified.setImageResource(R.drawable.ic_verified_user);
        }


    }


    @Override
    public int getItemCount() {
        return gigsList.size();
    }

}
