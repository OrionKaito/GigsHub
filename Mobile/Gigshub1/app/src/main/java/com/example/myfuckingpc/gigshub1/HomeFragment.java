package com.example.myfuckingpc.gigshub1;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<Gigs> gigsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GigsAdapter mAdapter;
    private String title;
    private int page;
    private LinearLayout topBar;
    private PagerSlidingTabStrip psts;
    private ImageView ivAddEvents;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(int page, String title) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivAddEvents = getActivity().findViewById(R.id.iv_add_gigs);
        recyclerView = getActivity().findViewById(R.id.rv_gigshome);
        recyclerView.setVisibility(View.VISIBLE);
//        topBar = getActivity().findViewById(R.id.ll_main_top_bar);
        psts = getActivity().findViewById(R.id.tabs);
        psts.setVisibility(View.VISIBLE);
        mAdapter = new GigsAdapter(gigsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        setGigsList();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        ivAddEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateGigsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setGigsList() {
        Gigs gigs = new Gigs("FPT Graduation Ceremony", "District 12", (float) 3.5, R.drawable.event, 256);
        gigsList.add(gigs);
        gigs = new Gigs("Sơn Tùng MTP", "Quân khu 7 Stadium", (float) 4.5, R.drawable.event2, 875);
        gigsList.add(gigs);
        mAdapter.notifyDataSetChanged();
    }
}
