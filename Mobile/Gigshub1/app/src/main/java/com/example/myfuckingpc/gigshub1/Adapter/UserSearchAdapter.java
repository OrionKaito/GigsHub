package com.example.myfuckingpc.gigshub1.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.Activity.UserFollowActivity;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.model.UserFollow;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.MyViewHolder> {
    private List<UserFollow> usersList;

    public UserSearchAdapter(List<UserFollow> usersList) {
        this.usersList = usersList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, fullname, following, notfollow;
        public CircleImageView image;
        private ImageView verify;
        private RelativeLayout rl_user;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_user_name_search);
            fullname = view.findViewById(R.id.tv_user_fullname_search);
            image = view.findViewById(R.id.civ_user_search);
            verify = view.findViewById(R.id.iv_user_verify_search);
            rl_user = view.findViewById(R.id.rl_user_search);
            rl_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    @NonNull
    @Override
    public UserSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_user_search, viewGroup, false);
        return new UserSearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchAdapter.MyViewHolder myViewHolder, int i) {
        UserFollow uf = usersList.get(i);
        myViewHolder.fullname.setText(uf.getFullname());
        myViewHolder.name.setText(uf.getName());
        myViewHolder.image.setImageResource(uf.getImage());
        if (uf.isVerify()) {
            myViewHolder.verify.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.verify.setVisibility(View.GONE);
        }
        if (uf.isFollow()) {
            myViewHolder.following.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.notfollow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
