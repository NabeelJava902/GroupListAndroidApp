package com.example.grouplist;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBuilder {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    public RecyclerViewBuilder(RecyclerView recyclerView, RecyclerAdapter adapter, Context context){
        mRecyclerView = recyclerView;
        mRecyclerView.setHasFixedSize(true);
        mAdapter = adapter;
        mLayout = new LinearLayoutManager(context);

        mRecyclerView.setLayoutManager(mLayout);
        mRecyclerView.setAdapter(mAdapter);
    }

    public RecyclerAdapter getAdapter() {
        return mAdapter;
    }
}
