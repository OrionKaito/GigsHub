package com.example.myfuckingpc.gigshub1.Fragment;

import android.content.DialogInterface;
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

import com.example.myfuckingpc.gigshub1.Activity.ChooseMethodToAddBudget;
import com.example.myfuckingpc.gigshub1.Activity.DetailGigsActivity;
import com.example.myfuckingpc.gigshub1.Activity.LoginActivity;
import com.example.myfuckingpc.gigshub1.Adapter.EventSearchAdapter;
import com.example.myfuckingpc.gigshub1.EventSearch;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.RecyclerTouchListener;
import com.example.myfuckingpc.gigshub1.model.SavedToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    ImageView addBudget, logout;
    private RecyclerView recyclerView;
    private List<EventSearch> listEvent;
    private EventSearchAdapter adapter;


    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance(int page, String title) {
        PersonalFragment personalFragment = new PersonalFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        personalFragment.setArguments(args);
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
        addBudget = getActivity().findViewById(R.id.im_add_budget);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseMethodToAddBudget.class);
                startActivity(intent);
            }
        });
        listEvent = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_personal_list_event);
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
                Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE", 2);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        listFollow();
        //log out
        logout = getActivity().findViewById(R.id.iv_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Logout:");
                builder.setMessage("Do you want Logout?");
                builder.setCancelable(false);
                builder.setPositiveButton("No, thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SavedToken.setUserInfo(getContext(),"");
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                android.support.v7.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void listFollow() {
        listEvent.clear();
        List<EventSearch> data = new ArrayList<>();
        data.add(new EventSearch("Viral Fest Asia", "Award competition for young singer for asia singer, band. Now return to Bankok, Thailand", "Sat, Jan 30, 2019", R.drawable.pop_event3));
        data.add(new EventSearch("Hyperplay", "Day trips to world class attractions, and a chance to compete for the Grand Prize against the best of Southeast Asia", "Thu, Sep 13, 2019", R.drawable.pop_event4));
        data.add(new EventSearch("Countdown NYE", "There are few better ways to welcome in the new year than at a huge Insomniac party. Brought to San Bernardino by the incredible minds behind Electric Daisy Carnival, Nocturnal Wonderland, Escape, Life is Beautiful, Dreamstate and Middlelands", "Sun, Dec 31, 2018", R.drawable.edm_event1));
        data.add(new EventSearch("Electric Zoo", "Randall's Island, East Manhattan, parks a full-scale electronic festival right in the heart of New York City", "Sun, Mar 2, 2019", R.drawable.edm_event2));
        data.add(new EventSearch("Black Sun Empire", "Returning in late December on the beautiful west coast of Vietnam, the electronic dance music festival extravaganza EPIZODE³ will be welcoming the bigges", "Sun, Dec 20, 2018", R.drawable.rock_event2));
        listEvent.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
