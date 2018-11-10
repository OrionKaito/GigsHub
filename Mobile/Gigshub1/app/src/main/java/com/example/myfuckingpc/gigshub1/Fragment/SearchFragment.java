package com.example.myfuckingpc.gigshub1.Fragment;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.myfuckingpc.gigshub1.Activity.DetailGigsActivity;
import com.example.myfuckingpc.gigshub1.Adapter.EventSearchAdapter;
import com.example.myfuckingpc.gigshub1.EventSearch;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.RecyclerTouchListener;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.CategoryClient;
import com.example.myfuckingpc.gigshub1.model.Category;
import com.example.myfuckingpc.gigshub1.model.CategoryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    //
    List<CategoryItem> categoryList = new ArrayList<>();
    String[] categoryArr;
    //
    private EditText searchText;
    private RecyclerView recyclerView;
    private EventSearchAdapter adapter;
    private List<EventSearch> listEvent;
    private RelativeLayout rl_rock, rl_pop, rl_edm;
    List<EventSearch> data1, data2, data3;
    private Spinner spn_category, spn_type;

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
        //
        View inputFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        spn_type = inputFragmentView.findViewById(R.id.spn_search_type);
        spn_category = inputFragmentView.findViewById(R.id.spn_search_category);
        searchText = inputFragmentView.findViewById(R.id.edt_search);
        String[] typeArr = {"Title","Location","Category"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,typeArr);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type.setAdapter(adapterCategory);
        //
        CategoryClient service = ApiUtils.categoryClient();
        Call<Category> call = service.getAllCategory();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body().getData();
                    categoryArr = new String[categoryList.size()];
                    for(int i = 0; i<categoryList.size();i++){
                        categoryArr[i] = categoryList.get(i).getName();


                    }
                    ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,categoryArr);
                    adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spn_category.setAdapter(adapterCategory);
                }
                else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                return;
            }
        });
        //
        spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spn_type.getSelectedItem()=="Category"){
                    spn_category.setVisibility(View.VISIBLE);
                    searchText.setVisibility(View.GONE);
                }
                else {
                    spn_category.setVisibility(View.GONE);
                    searchText.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        }
    }
}
