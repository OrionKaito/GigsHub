package com.example.myfuckingpc.gigshub1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.EventSearch;
import com.example.myfuckingpc.gigshub1.FileUtils.LoadImageInternet;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.model.EventItem;

import java.util.List;

public class EventSearchAdapter extends RecyclerView.Adapter<EventSearchAdapter.MyViewHolder> {
    private List<EventItem> searchList;

    public EventSearchAdapter(List<EventItem> searchList) {
        this.searchList = searchList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, description, date;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.iv_search_event);
            title = view.findViewById(R.id.tv_search_title);
            description = view.findViewById(R.id.tv_search_description);
            date = view.findViewById(R.id.tv_search_event_date);
        }
    }

    @NonNull
    @Override
    public EventSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_search_event, viewGroup, false);
        return new EventSearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventSearchAdapter.MyViewHolder myViewHolder, int i) {
        EventItem event = searchList.get(i);
        myViewHolder.date.setText(event.getDate()+"\n"+event.getTime());
        myViewHolder.description.setText(event.getDescription());
        myViewHolder.title.setText(event.getTitle());
        String url = event.getImgPath();
        LoadImageInternet load = new LoadImageInternet(myViewHolder.image);
        load.execute(url);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

}
