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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.Activity.DetailGigsActivity;
import com.example.myfuckingpc.gigshub1.Adapter.EventSearchAdapter;
import com.example.myfuckingpc.gigshub1.EventSearch;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.RecyclerTouchListener;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.CategoryClient;
import com.example.myfuckingpc.gigshub1.api.EventClient;
import com.example.myfuckingpc.gigshub1.model.Category;
import com.example.myfuckingpc.gigshub1.model.CategoryItem;
import com.example.myfuckingpc.gigshub1.model.Event;
import com.example.myfuckingpc.gigshub1.model.EventItem;
import com.example.myfuckingpc.gigshub1.model.SavedToken;

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
    //
    private List<EventSearch> listEvent;
    List<EventSearch> data1, data2, data3;
    private Spinner spn_category, spn_type;
    private ImageView search;

    //
    private List<EventItem> searchedList;

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
        final View inputFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = inputFragmentView.findViewById(R.id.rv_search_list_event);
        spn_type = inputFragmentView.findViewById(R.id.spn_search_type);
        spn_category = inputFragmentView.findViewById(R.id.spn_search_category);
        searchText = inputFragmentView.findViewById(R.id.edt_search);
        String[] typeArr = {"Title","City","Category"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,typeArr);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type.setAdapter(adapterCategory);
        search = inputFragmentView.findViewById(R.id.iv_search_event);
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
        //handle search

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchByText = searchText.getText().toString();
                switch (spn_type.getSelectedItem().toString()){
                    case "Title":
                        EventClient serviceTitle = ApiUtils.eventClient();
                        Call<Event> callTitle = serviceTitle.searchByTitle(searchByText);
                        callTitle.enqueue(new Callback<Event>() {
                            @Override
                            public void onResponse(Call<Event> call, Response<Event> response) {
                                if(response.isSuccessful()){
                                    searchedList = response.body().getData();
                                    adapter = new EventSearchAdapter(searchedList);
                                    recyclerView = inputFragmentView.findViewById(R.id.rv_search_list_event);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(getActivity(), "No result", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Event> call, Throwable t) {
                                Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                        break;
                    case "City":
                        EventClient serviceCity = ApiUtils.eventClient();
                        Call<Event> callCity = serviceCity.searchByCity(searchByText);
                        callCity.enqueue(new Callback<Event>() {
                            @Override
                            public void onResponse(Call<Event> call, Response<Event> response) {
                                if(response.isSuccessful()){
                                    searchedList = response.body().getData();
                                    adapter = new EventSearchAdapter(searchedList);
                                    recyclerView = inputFragmentView.findViewById(R.id.rv_search_list_event);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Event> call, Throwable t) {
                                Toast.makeText(getActivity(), "No result.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                        break;
                    case "Category":
                        String category = spn_category.getSelectedItem().toString();
                        EventClient serviceCategory = ApiUtils.eventClient();
                        Call<Event> callCategory = serviceCategory.searchByCategory(category);
                        callCategory.enqueue(new Callback<Event>() {
                            @Override
                            public void onResponse(Call<Event> call, Response<Event> response) {
                                if(response.isSuccessful()){
                                    searchedList = response.body().getData();
                                    adapter = new EventSearchAdapter(searchedList);
                                    recyclerView = inputFragmentView.findViewById(R.id.rv_search_list_event);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Event> call, Throwable t) {
                                Toast.makeText(getActivity(), "No result.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                }
            }
        });
        // Inflate the layout for this fragment

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String userInfo = SavedToken.getUserInfo(getActivity());
                String[] infoArr = userInfo.split("[|]");
                String username = infoArr[1];
                String ownerUsername = searchedList.get(position).getOwnerName();
                int REQ_DETAIL = 1;
                if(username.equals(ownerUsername)){
                    REQ_DETAIL = 2;
                    Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", REQ_DETAIL);
                    bundle.putLong("EventId", searchedList.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", REQ_DETAIL);
                    bundle.putLong("EventId", searchedList.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return inputFragmentView;

        //handle search
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onClick(View v) {

    }
}
