package com.example.myfuckingpc.gigshub1.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.Adapter.CategoryAdapter;
import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView rv_list_category;
    private RelativeLayout rl_admin_layout;
    private CategoryAdapter adapter;
    private List<String> listCategory;
    private PopupWindow pw_add;
    private TextView tv_popup_title;
    private EditText et_category;
    private int action;
    private int item_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);
        action = 0;
        listCategory = new ArrayList<>();
        adapter = new CategoryAdapter(listCategory, getApplicationContext());
        rv_list_category = findViewById(R.id.rv_list_category);
        rl_admin_layout = findViewById(R.id.rl_admin_layout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_list_category.setLayoutManager(mLayoutManager);
        rv_list_category.setItemAnimator(new DefaultItemAnimator());
        rv_list_category.setAdapter(adapter);
        rv_list_category.addOnItemTouchListener(new RecyclerTouchListener(this, rv_list_category, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                item_click = position;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        addList();
    }

    private void addList() {
        List<String> data = new ArrayList<>();
        data.add("Rock");
        data.add("Pop");
        data.add("EDM");
        listCategory.addAll(data);
        adapter.notifyDataSetChanged();
    }

    private void openPopup(View view) {
        rl_admin_layout.setClickable(false);
        rl_admin_layout.setFocusable(false);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pw_add = new PopupWindow(inflater.inflate(R.layout.popup_category, null, false), LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw_add.setAnimationStyle(R.style.popup_window_animation_phone);
        pw_add.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    public void clickToAddCategory(View view) {
        openPopup(view);
        action = 1;
    }

    public void clickToUpdateCategory(View view) {
        action = 2;
        openPopup(view);
    }

    public void clickToDeleteCategory(View view) {
        listCategory.remove(item_click);
        adapter.notifyItemRemoved(item_click);
    }

    public void clickToCancelCategory(final View view) {
        pw_add.dismiss();
        return;
    }

    public void clickToChangeCategory(final View view) {
        View v = pw_add.getContentView();
        et_category = v.findViewById(R.id.et_add_category);
        pw_add.dismiss();
        rl_admin_layout.setClickable(true);
        rl_admin_layout.setFocusable(true);
        if (action == 1) {
            listCategory.add(et_category.getText().toString());
            adapter.notifyDataSetChanged();
        } else {
            listCategory.set(item_click, et_category.getText().toString());
            adapter.notifyItemChanged(item_click);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
