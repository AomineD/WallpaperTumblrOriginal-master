package com.gay.xmen.Adapters;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gay.xmen.api.OnLoadMore;

public class OnScrollChangeAdapter extends RecyclerView.OnScrollListener {

private GridLayoutManager manager;
private OnLoadMore loadMoreinterface;


    public OnScrollChangeAdapter(RecyclerView list, OnLoadMore interfneed) {
        if(list.getLayoutManager() != null){
            this.manager = (GridLayoutManager) list.getLayoutManager();
        }
        this.loadMoreinterface = interfneed;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        int last = manager.getItemCount();
        int last_visible = manager.findLastVisibleItemPosition();


        if(last_visible == last - 1){
           // Log.e("MAIN", "onScrolled: Es ultima");
            loadMoreinterface.LoadMore();
        }

    }
}
