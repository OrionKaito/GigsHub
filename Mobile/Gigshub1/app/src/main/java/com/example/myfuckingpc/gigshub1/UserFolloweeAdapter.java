package com.example.myfuckingpc.gigshub1;

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
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFolloweeAdapter extends RecyclerView.Adapter<UserFolloweeAdapter.MyViewHolder> {
    private List<UserFollowee> usersList;
    private Intent intent;
    private Context context;

    public UserFolloweeAdapter(List<UserFollowee> usersList, Intent intent, Context context) {
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
    public UserFolloweeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_user_followee, viewGroup, false);
        return new UserFolloweeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFolloweeAdapter.MyViewHolder myViewHolder, int i) {
        UserFollowee uf = usersList.get(i);
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
