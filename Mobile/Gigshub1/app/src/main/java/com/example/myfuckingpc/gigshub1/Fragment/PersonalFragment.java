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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.Activity.ChooseMethodToAddBudget;
import com.example.myfuckingpc.gigshub1.Activity.DetailGigsActivity;
import com.example.myfuckingpc.gigshub1.Activity.EditProfileActivity;
import com.example.myfuckingpc.gigshub1.Activity.FollowActivity;
import com.example.myfuckingpc.gigshub1.Activity.LoginActivity;
import com.example.myfuckingpc.gigshub1.Activity.RequestVerificationActivity;
import com.example.myfuckingpc.gigshub1.Adapter.EventSearchAdapter;
import com.example.myfuckingpc.gigshub1.FileUtils.LoadImageInternet;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.RecyclerTouchListener;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.CreateEventClient;
import com.example.myfuckingpc.gigshub1.api.CustomerClient;
import com.example.myfuckingpc.gigshub1.api.EditProfileClient;
import com.example.myfuckingpc.gigshub1.model.Event;
import com.example.myfuckingpc.gigshub1.model.EventItem;
import com.example.myfuckingpc.gigshub1.model.SavedToken;
import com.example.myfuckingpc.gigshub1.model.User;
import com.example.myfuckingpc.gigshub1.model.UserItem;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    ImageView addBudget, logout;
    private RecyclerView recyclerView;
    private List<EventItem> listEvent;
    private List<UserItem> userItems;
    private CircleImageView civ_image;
    private EventSearchAdapter adapter;
    private ImageView iv_verify, iv_followee, iv_follower, iv_edit_profile;
    private Intent intent;
    private TextView tv_verify, tv_name, tv_number_event, tv_numbers_event_title, tv_user_verify_status;
    public static final int FOLLOWER = 1;
    public static final int FOLLOWING = 2;
    public static final int VERIFIED = 1;
    public static final int NOT_VERIFY = 2;
    public static int typeUser;
    private String token = null;

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
        civ_image = view.findViewById(R.id.civ_personal_image);
        iv_followee = view.findViewById(R.id.iv_list_follow);
        iv_follower = view.findViewById(R.id.iv_list_follower);
        addBudget = view.findViewById(R.id.im_add_budget);
        iv_verify = view.findViewById(R.id.iv_verify_request);
        tv_verify = view.findViewById(R.id.tv_user_verify_status);
        recyclerView = view.findViewById(R.id.rv_personal_list_event);
        tv_number_event = view.findViewById(R.id.tv_numbers_event_attend);
        tv_numbers_event_title = view.findViewById(R.id.tv_numbers_event_title);
        tv_name = view.findViewById(R.id.tv_name_user);
        iv_edit_profile = view.findViewById(R.id.iv_edit_profile);
        //
        iv_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        iv_followee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), FollowActivity.class);
                intent.putExtra("TYPE", FOLLOWING);
                startActivity(intent);
            }
        });
        iv_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), FollowActivity.class);
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
        addBudget = getActivity().findViewById(R.id.im_add_budget);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseMethodToAddBudget.class);
                startActivity(intent);
            }
        });
        //get user info
        String[] userInfoArr = SavedToken.getUserInfo(getContext()).split("[|]");
        token = userInfoArr[0];
        userItems = new ArrayList<>();
        CustomerClient customerClient = ApiUtils.getCustomerClient(token);
        Call<User> call = customerClient.getUserInformation();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userItems = response.body().getData();
                tv_name.setText(userItems.get(0).getFullname().toString());
                if (userItems.get(0).getImgPath() != null) {
                    String url = userItems.get(0).getImgPath().toString();
                    LoadImageInternet load = new LoadImageInternet(civ_image);
                    load.execute(url);
                }
                if (userItems.get(0).getIsVerified() == true) {
                    iv_verify.setImageResource(R.drawable.ic_verified_user);
                    tv_verify.setText("Verified");

                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        //get attend list
        listEvent = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_personal_list_event);
        CreateEventClient serviceGetAttendEvent = ApiUtils.createEventClient(token);
        Call<Event> callAttend = serviceGetAttendEvent.getAttendEvent();
        callAttend.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()) {
                    listEvent = response.body().getData();
                    adapter = new EventSearchAdapter(listEvent);
                    recyclerView.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    tv_number_event.setText(listEvent.size() + "");
                } else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                return;
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String userInfo = SavedToken.getUserInfo(getActivity());
                String[] infoArr = userInfo.split("[|]");
                String username = infoArr[1];
                String ownerUsername = listEvent.get(position).getOwnerName();
                int REQ_DETAIL = 1;
                if(username.equals(ownerUsername)){
                    REQ_DETAIL = 2;
                    Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", REQ_DETAIL);
                    bundle.putLong("EventId", listEvent.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), DetailGigsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", REQ_DETAIL);
                    bundle.putLong("EventId", listEvent.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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
                        SavedToken.setUserInfo(getContext(), "");
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
        //listEvent.clear();
        //List<EventSearch> data = new ArrayList<>();
        //data.add(new EventSearch("Viral Fest Asia", "Award competition for young singer for asia singer, band. Now return to Bankok, Thailand", "Sat, Jan 30, 2019", R.drawable.pop_event3));
        //data.add(new EventSearch("Hyperplay", "Day trips to world class attractions, and a chance to compete for the Grand Prize against the best of Southeast Asia", "Thu, Sep 13, 2019", R.drawable.pop_event4));
        //data.add(new EventSearch("Countdown NYE", "There are few better ways to welcome in the new year than at a huge Insomniac party. Brought to San Bernardino by the incredible minds behind Electric Daisy Carnival, Nocturnal Wonderland, Escape, Life is Beautiful, Dreamstate and Middlelands", "Sun, Dec 31, 2018", R.drawable.edm_event1));
        //data.add(new EventSearch("Electric Zoo", "Randall's Island, East Manhattan, parks a full-scale electronic festival right in the heart of New York City", "Sun, Mar 2, 2019", R.drawable.edm_event2));
        //data.add(new EventSearch("Black Sun Empire", "Returning in late December on the beautiful west coast of Vietnam, the electronic dance music festival extravaganza EPIZODE³ will be welcoming the bigges", "Sun, Dec 20, 2018", R.drawable.rock_event2));
        //listEvent.addAll(data);
        //adapter.notifyDataSetChanged();
    }
}
