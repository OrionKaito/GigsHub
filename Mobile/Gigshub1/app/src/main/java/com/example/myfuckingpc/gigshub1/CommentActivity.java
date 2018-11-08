package com.example.myfuckingpc.gigshub1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EventCommentAdapter adapter;
    private List<Comment> commentList;
    private List<Comment> data;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView = findViewById(R.id.rv_list_comment);
        et_search = findViewById(R.id.et_search);
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
        data.add(new Comment(R.drawable.ic_personal1, "Jess Martin", "I think i might have beautiful memory here"));
        data.add(new Comment(R.drawable.ic_personal2, "Sarah Loreth", "Love those singers, definitely good"));
        data.add(new Comment(R.drawable.ic_personal3, "Joker Ree", "Thank you for your support"));
        data.add(new Comment(R.drawable.ic_personal4, "Maitrik Kataria", "What a nice concert, that sure wil be good time for enjoy"));
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
