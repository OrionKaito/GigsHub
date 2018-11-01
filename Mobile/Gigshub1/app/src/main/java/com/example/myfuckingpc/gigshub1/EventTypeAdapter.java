package com.example.myfuckingpc.gigshub1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.MyViewHolder> {
    private List<EventType> typeList;

    public EventTypeAdapter(List<EventType> typeList) {
        this.typeList = typeList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_type;
        public LinearLayout ll_background;

        public MyViewHolder(View view) {
            super(view);
            ll_background = view.findViewById(R.id.ll_background_type);
            tv_type = view.findViewById(R.id.tv_event_title);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_event_types, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        EventType type = typeList.get(i);
        myViewHolder.tv_type.setText(type.getType());
        myViewHolder.ll_background.setBackgroundColor(type.getColor());
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }


}
