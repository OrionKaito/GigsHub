package com.example.myfuckingpc.gigshub1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.Comment;
import com.example.myfuckingpc.gigshub1.R;

import java.util.List;

public class EventCommentAdapter extends RecyclerView.Adapter<EventCommentAdapter.MyViewHolder> {
    private List<Comment> commentList;

    public EventCommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name, comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_user);
            name = itemView.findViewById(R.id.tv_user_name);
            comment = itemView.findViewById(R.id.tv_user_comment);
        }
    }

    @NonNull
    @Override
    public EventCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_comments_event, viewGroup, false);
        return new EventCommentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCommentAdapter.MyViewHolder myViewHolder, int i) {
        Comment comment = commentList.get(i);
        myViewHolder.name.setText(comment.getName());
        myViewHolder.comment.setText(comment.getComment());
        myViewHolder.image.setBackgroundResource(comment.getImage());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
