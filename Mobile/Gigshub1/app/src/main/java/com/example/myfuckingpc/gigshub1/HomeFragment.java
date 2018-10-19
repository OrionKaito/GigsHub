package com.example.myfuckingpc.gigshub1;


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
        recyclerView = getActivity().findViewById(R.id.rv_gigshome);
        mAdapter = new GigsAdapter(gigsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        setGigsList();

    }

    private void setGigsList() {
        Gigs gigs = new Gigs("FPT iz the bezt", "District 12", (float) 3.5, R.drawable.event, 256);
        gigsList.add(gigs);
        gigs = new Gigs("Tuổi loz sánh vai", "Trên núi", (float) 4.5, R.drawable.event2, 875);
        gigsList.add(gigs);
        mAdapter.notifyDataSetChanged();
    }
}
