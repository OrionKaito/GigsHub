package com.example.myfuckingpc.gigshub1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    ImageView addBudget;
    private CircleImageView civ_image;
    private RecyclerView recyclerView;
    private List<EventSearch> listEvent;
    private EventSearchAdapter adapter;
    private ImageView iv_verify, iv_followee, iv_follower;
    private Intent intent;
    private TextView tv_verify, tv_name, tv_number_event, tv_numbers_event_title;
    public static final int FOLLOWER = 1;
    public static final int FOLLOWING = 2;
    public static final int VERIFIED = 1;
    public static final int NOT_VERIFY = 2;
    public static int typeUser;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance(int page, String title, int type) {
        PersonalFragment personalFragment = new PersonalFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putInt("type", type);
        args.putString("someTitle", title);
        personalFragment.setArguments(args);
        typeUser = type;
        return personalFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        civ_image = view.findViewById(R.id.civ_personal_image);
        iv_followee = view.findViewById(R.id.iv_list_follow);
        iv_follower = view.findViewById(R.id.iv_list_follower);
        addBudget = view.findViewById(R.id.im_add_budget);
        iv_verify = view.findViewById(R.id.iv_verify_request);
        tv_verify = view.findViewById(R.id.tv_user_verify_status);
        recyclerView = view.findViewById(R.id.rv_personal_list_event);
        tv_number_event = view.findViewById(R.id.tv_numbers_event);
        tv_numbers_event_title = view.findViewById(R.id.tv_numbers_event_title);
        tv_name = view.findViewById(R.id.tv_name_user);
        iv_followee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), FolloweeActivity.class);
                intent.putExtra("TYPE", FOLLOWING);
                startActivity(intent);
            }
        });
        iv_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), FolloweeActivity.class);
                intent.putExtra("TYPE", FOLLOWER);
                startActivity(intent);
            }
        });

        iv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser != 2) {
                    Intent intent = new Intent(getActivity(), RequestVerificationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "This profile was verified", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseMethodToAddBudget.class);
                startActivity(intent);
            }
        });
        listEvent = new ArrayList<>();
        adapter = new EventSearchAdapter(listEvent);
        recyclerView.setVisibility(View.VISIBLE);
//        listPopMusic();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (typeUser == 1) {
                    Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        if (typeUser == 2) {
            civ_image.setImageResource(R.drawable.image_bp1);
            iv_verify.setImageResource(R.drawable.ic_verified_user);
            tv_verify.setText("Verified");
            tv_name.setText("Bùi Thị Bích Phương");
            listFollow2();
            tv_numbers_event_title.setText("Gigs");
            tv_number_event.setText("1");
        } else {
            listFollow();
            tv_number_event.setText("5");
            tv_numbers_event_title.setText("Attended");
        }

    }

    private void listFollow2() {
        listEvent.clear();
        List<EventSearch> data = new ArrayList<>();
        data.add(new EventSearch("Memory Asia Tour", "This is the first Asian tour of The Chainsmokers in 2017", "Thu, Sep 13, 2019", "Ho Chi Minh City, Vietnam", R.drawable.edm_event5));
        listEvent.addAll(data);
        adapter.notifyDataSetChanged();
    }

    private void listFollow() {
        listEvent.clear();
        List<EventSearch> data = new ArrayList<>();
        data.add(new EventSearch("Viral Fest Asia", "Award competition for young singer for asia singer, band. Now return to Bankok, Thailand", "Sat, Jan 30, 2019", "Ho Chi Minh City, Vietname", R.drawable.pop_event3));
        data.add(new EventSearch("Hyperplay", "Day trips to world class attractions, and a chance to compete for the Grand Prize against the best of Southeast Asia", "Thu, Sep 13, 2019", "Ho Chi Minh City, Vietname", R.drawable.pop_event4));
        data.add(new EventSearch("Countdown NYE", "There are few better ways to welcome in the new year than at a huge Insomniac party. Brought to San Bernardino by the incredible minds behind Electric Daisy Carnival, Nocturnal Wonderland, Escape, Life is Beautiful, Dreamstate and Middlelands", "Sun, Dec 31, 2018", "Ho Chi Minh City, Vietname", R.drawable.edm_event1));
        data.add(new EventSearch("Electric Zoo", "Randall's Island, East Manhattan, parks a full-scale electronic festival right in the heart of New York City", "Sun, Mar 2, 2019", "Ho Chi Minh City, Vietname", R.drawable.edm_event2));
        data.add(new EventSearch("Black Sun Empire", "Returning in late December on the beautiful west coast of Vietnam, the electronic dance music festival extravaganza EPIZODE³ will be welcoming the bigges", "Sun, Dec 20, 2018", "Ho Chi Minh City, Vietname", R.drawable.rock_event2));
        listEvent.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
