package com.example.myfuckingpc.gigshub1;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private EventSearchAdapter adapter;
    private List<EventSearch> listEvent;
    private RelativeLayout rl_rock, rl_pop, rl_edm;
    List<EventSearch> data1, data2, data3;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(int page, String title) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        searchFragment.setArguments(args);
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        data1 = new ArrayList<>();
        data1.add(new EventSearch("Music contest in Ho Chi Minh City", "One of the best music event in Ho Chi Minh City. Join it with us", "Sat, Dec 20, 2018", R.drawable.pop_event1));
        data1.add(new EventSearch("Pop Festival in Da Nang", "Beautiful time to participate on of the best music concert of the year", "Mon, Jul 2, 2018", R.drawable.pop_event2));
        data1.add(new EventSearch("Viral Fest Asia", "Award competition for young singer for asia singer, band. Now return to Bankok, Thailand", "Sat, Jan 30, 2019", R.drawable.pop_event3));
        data1.add(new EventSearch("Hyperplay", "Day trips to world class attractions, and a chance to compete for the Grand Prize against the best of Southeast Asia", "Thu, Sep 13, 2019", R.drawable.pop_event4));

        data2 = new ArrayList<>();
        data2.add(new EventSearch("Countdown NYE", "There are few better ways to welcome in the new year than at a huge Insomniac party. Brought to San Bernardino by the incredible minds behind Electric Daisy Carnival, Nocturnal Wonderland, Escape, Life is Beautiful, Dreamstate and Middlelands", "Sun, Dec 31, 2018", R.drawable.edm_event1));
        data2.add(new EventSearch("Electric Zoo", "Randall's Island, East Manhattan, parks a full-scale electronic festival right in the heart of New York City", "Sun, Mar 2, 2019", R.drawable.edm_event2));
        data2.add(new EventSearch("Ultra Music Festival", "Kicking off festival season with a bang, you could easily make a case for Ultra Music Festival being the best festival in the country. ", "Sun, Mar 29, 2019", R.drawable.edm_event3));

        data3 = new ArrayList<>();
        data3.add(new EventSearch("Rock Concert 2018 Vietnam", "Rock Concert 2018 với chủ đề “Battleship” (tạm dịch là Chiến hạm) sẽ ra mắt khán giả yêu rock năm đầu tiên với 2 live-show vào các ngày 26-4 tại sân vận động Hàng Đẫy (Hà Nội) ", "Mon, April 26, 2019", R.drawable.rock_event1));
        data3.add(new EventSearch("Black Sun Empire", "Returning in late December on the beautiful west coast of Vietnam, the electronic dance music festival extravaganza EPIZODE³ will be welcoming the bigges", "Sun, Dec 20, 2018", R.drawable.rock_event2));
        data3.add(new EventSearch("Vietnam’s Epizode festival enlists ", "The end-of-year EDM showdown is on! Vietnam’s Epizode festival is coming back with an epic 2017 edition", "Sat, Jan 20, 2019", R.drawable.rock_event3));

        View inputFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        rl_rock = inputFragmentView.findViewById(R.id.rl_type_rock);
        rl_pop = inputFragmentView.findViewById(R.id.rl_type_pop);
        rl_edm = inputFragmentView.findViewById(R.id.rl_type_EDM);
        rl_rock.setOnClickListener(this);
        rl_pop.setOnClickListener(this);
        rl_edm.setOnClickListener(this);
        listEvent = new ArrayList<>();
        adapter = new EventSearchAdapter(listEvent);
        recyclerView = inputFragmentView.findViewById(R.id.rv_search_list_event);
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
                bundle.putInt("TYPE", 3);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return inputFragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void listPopMusic() {
        listEvent.clear();
        listEvent.addAll(data1);
        adapter.notifyDataSetChanged();
    }

    private void listRockMusic() {
        listEvent.clear();
        listEvent.addAll(data2);
        adapter.notifyDataSetChanged();
    }

    private void listEDMMusic() {
        listEvent.clear();
        listEvent.addAll(data3);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_type_pop:
                listPopMusic();
                rl_pop.setClickable(false);
                rl_pop.setFocusable(false);
                rl_edm.setClickable(true);
                rl_edm.setFocusable(true);
                rl_rock.setClickable(true);
                rl_rock.setFocusable(true);
                break;
            case R.id.rl_type_EDM:
                listEDMMusic();
                rl_pop.setClickable(true);
                rl_pop.setFocusable(true);
                rl_edm.setClickable(false);
                rl_edm.setFocusable(false);
                rl_rock.setClickable(true);
                rl_rock.setFocusable(true);
                break;
            case R.id.rl_type_rock:
                listRockMusic();
                rl_pop.setClickable(true);
                rl_pop.setFocusable(true);
                rl_edm.setClickable(true);
                rl_edm.setFocusable(true);
                rl_rock.setClickable(false);
                rl_rock.setFocusable(false);
                break;
        }
    }
}
