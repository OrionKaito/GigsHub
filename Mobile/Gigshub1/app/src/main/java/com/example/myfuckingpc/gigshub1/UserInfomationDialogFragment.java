package com.example.myfuckingpc.gigshub1;


import android.app.Dialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chabbal.slidingdotsplash.SlidingSplashView;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfomationDialogFragment extends DialogFragment {
    private SlidingSplashView ssv;
    private int[] setImage;

    public UserInfomationDialogFragment() {
        // Required empty public constructor
    }

    public static UserInfomationDialogFragment newInstance() {
        Bundle args = new Bundle();
        UserInfomationDialogFragment fragment = new UserInfomationDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_infomation_dialog, container, false);
        getDialog().setTitle("Infomation");
        LinearLayout ll_1 = v.findViewById(R.id.ll_user_accept);
        LinearLayout ll_2 = v.findViewById(R.id.ll_user_deny);
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You click Accept", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ssv = view.findViewById(R.id.ssv_details_user_view);
        setImage = new int[0];
        setImage = addElement(setImage, R.drawable.image_st2);
        setImage = addElement(setImage, R.drawable.image_st4);
        setImage = addElement(setImage, R.drawable.image_st3);
        setImage = addElement(setImage, R.drawable.image_st1);
        setImage = addElement(setImage, R.drawable.image_st5);
        ssv.setImageResources(setImage);
        super.onViewCreated(view, savedInstanceState);
    }

    private int[] addElement(int[] a, int e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }


}
