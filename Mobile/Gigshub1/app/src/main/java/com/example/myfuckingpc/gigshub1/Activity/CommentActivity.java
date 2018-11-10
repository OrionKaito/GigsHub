package com.example.myfuckingpc.gigshub1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfuckingpc.gigshub1.Adapter.EventCommentAdapter;
import com.example.myfuckingpc.gigshub1.Comment;
import com.example.myfuckingpc.gigshub1.R;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EventCommentAdapter adapter;
    private List<Comment> commentList;
    private List<Comment> data;
    private EditText et_search;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView = findViewById(R.id.rv_list_comment);
        et_search = findViewById(R.id.et_search);
        tv_title = findViewById(R.id.tv_event_title);
        commentList = new ArrayList<>();
        adapter = new EventCommentAdapter(commentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        listComment();
    }


    private void listComment() {
        commentList.clear();
        data = new ArrayList<>();
        commentList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    public void clickToReturn(View view) {
        this.finish();
    }

    public void clickToAddComment(View view) {
        if (!et_search.getText().toString().isEmpty()) {
            commentList.add(new Comment(R.drawable.ic_thai, "Trần Đức Thái", et_search.getText().toString()));
            et_search.setText("");
            adapter.notifyDataSetChanged();
        }
    }
}
