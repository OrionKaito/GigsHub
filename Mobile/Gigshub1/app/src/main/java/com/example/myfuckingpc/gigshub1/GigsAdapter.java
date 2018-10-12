package com.example.myfuckingpc.gigshub1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class GigsAdapter extends RecyclerView.Adapter<GigsAdapter.MyViewHolder> {
    private List<Gigs> gigsList;

    public GigsAdapter(List<Gigs> gigsList) {
        this.gigsList = gigsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, location, number;
        public ImageView image;
        public RatingBar star;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_gigs_title);
            location = view.findViewById(R.id.tv_gigs_place);
            number = view.findViewById(R.id.tv_gigs_number);
            star = view.findViewById(R.id.rb_gigs_star);
            image = view.findViewById(R.id.iv_gigs);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gigs_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gigs gigs = gigsList.get(position);
        holder.title.setText(gigs.getGigsTitle());
        holder.location.setText(gigs.getGigsLocation());
        holder.number.setText(gigs.getGigsNumber() + " people interested");
        holder.star.setRating(gigs.getGigsStar());
        holder.image.setImageResource(gigs.getGigsImage());
    }

    @Override
    public int getItemCount() {
        return gigsList.size();
    }
}
