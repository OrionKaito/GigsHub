package com.example.myfuckingpc.gigshub1.Adapter;

import android.content.Context;
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

public class UserFollowAdapter extends RecyclerView.Adapter<UserFollowAdapter.MyViewHolder> {
    private List<UserFollow> usersList;
    private Intent intent;
    private Context context;

    public UserFollowAdapter(List<UserFollow> usersList, Intent intent, Context context) {
        this.usersList = usersList;
        this.intent = intent;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, fullname, following, notfollow;
        public CircleImageView image;
        private ImageView verify;
        private RelativeLayout rl_user;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_user_name_follow);
            fullname = view.findViewById(R.id.tv_user_fullname_follow);
            image = view.findViewById(R.id.civ_user_follow);
            verify = view.findViewById(R.id.iv_user_verify_follow);
            following = view.findViewById(R.id.tv_following);
            notfollow = view.findViewById(R.id.tv_not_follow);
            rl_user = view.findViewById(R.id.rl_user_follow);
            rl_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UserFollowActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
            following.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    following.setVisibility(View.GONE);
                    notfollow.setVisibility(View.VISIBLE);
                }
            });
            notfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    following.setVisibility(View.VISIBLE);
                    notfollow.setVisibility(View.GONE);
                }
            });
        }
    }


    @NonNull
    @Override
    public UserFollowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_user_follow, viewGroup, false);
        return new UserFollowAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFollowAdapter.MyViewHolder myViewHolder, int i) {
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
