package com.example.myfuckingpc.gigshub1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserVerifyAdapter extends RecyclerView.Adapter<UserVerifyAdapter.MyViewHolder> {
    private ClickListener listener;
    private List<VerifyUser> usersList;

    public UserVerifyAdapter(ClickListener listener, List<VerifyUser> usersList) {
        this.listener = listener;
        this.usersList = usersList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView name, mail;
        public CircleImageView image;
        public ImageView accept, cancel;
        public WeakReference<ClickListener> listenerRef;

        public MyViewHolder(View view, ClickListener clickListener) {
            super(view);
            listenerRef = new WeakReference<>(clickListener);
            name = view.findViewById(R.id.tv_user_fullname);
            mail = view.findViewById(R.id.tv_user_email);
            image = view.findViewById(R.id.civ_user_image);
            accept = view.findViewById(R.id.iv_accept_verify);
            cancel = view.findViewById(R.id.iv_cancel_verify);
            accept.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == accept.getId()) {
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            return false;
        }
    }

    @NonNull
    @Override
    public UserVerifyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_user_verify, viewGroup, false);
        return new UserVerifyAdapter.MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVerifyAdapter.MyViewHolder myViewHolder, int i) {
        VerifyUser user = usersList.get(i);
        myViewHolder.name.setText(user.getFullname());
        myViewHolder.mail.setText(user.getMail());
        myViewHolder.image.setImageResource(user.getImage());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
