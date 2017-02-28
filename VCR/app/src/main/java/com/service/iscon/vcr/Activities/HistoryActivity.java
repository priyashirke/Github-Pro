package com.service.iscon.vcr.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.service.iscon.vcr.R;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rv_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_user_history);

        rv_history=(RecyclerView)findViewById(R.id.rv_history);


    }
}
