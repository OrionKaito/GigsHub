package com.example.myfuckingpc.gigshub1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<String> listCategory;
    private Context context;
    private int row_click;

    public CategoryAdapter(List<String> listCategory, Context context) {
        this.listCategory = listCategory;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_category);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_category, viewGroup, false);
        return new CategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(listCategory.get(i));
        myViewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_click = i;
                notifyDataSetChanged();
            }
        });
        if (row_click == i) {
            myViewHolder.name.setBackgroundColor(Color.BLACK);
            myViewHolder.name.setTextColor(Color.WHITE);
        } else {
            myViewHolder.name.setBackgroundColor(Color.WHITE);
            myViewHolder.name.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
}
