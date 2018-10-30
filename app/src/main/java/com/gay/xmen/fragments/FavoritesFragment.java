package com.gay.xmen.fragments;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gay.xmen.Adapters.WallpaperAdapter;
import com.gay.xmen.R;
import com.gay.xmen.config.FavoritesItem;
import com.gay.xmen.models.TumblrItem;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class FavoritesFragment extends Fragment {

    ArrayList<FavoritesItem> favoritesItemArrayList = new ArrayList<>();

    ArrayList<TumblrItem> itemFav = new ArrayList<>();

    private RecyclerView list_recycler;

    private GifImageView loading;
    private TextView message;
    private SwipeRefreshLayout swipeRefreshLayout;


    public FavoritesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

GetData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);


        list_recycler = view.findViewById(R.id.rec);

        WallpaperAdapter adapter = new WallpaperAdapter(getContext(), itemFav, getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        list_recycler.setLayoutManager(gridLayoutManager);
        list_recycler.setAdapter(adapter);

        loading = view.findViewById(R.id.gifloading);

        message = view.findViewById(R.id.errortext);

        swipeRefreshLayout = view.findViewById(R.id.swipe);
        loading.setVisibility(View.GONE);

        SetupSwipe();

        return view;
    }


    private void SetupSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                favoritesItemArrayList.clear();
                list_recycler.getAdapter().notifyDataSetChanged();
                GetData();
            }
        });
    }


    private void GetData(){
        try {
            favoritesItemArrayList = (ArrayList<FavoritesItem>) FavoritesItem.listAll(FavoritesItem.class);


            //Log.e("MAIN", "onCreate: SIZE = " + favoritesItemArrayList.size());

            for (int i = 0; i < favoritesItemArrayList.size(); i++) {
                TumblrItem item = new TumblrItem();
                item.setUrl_image(favoritesItemArrayList.get(i).getUrl());
                item.setName(favoritesItemArrayList.get(i).getName());

                itemFav.add(item);


            }
if(swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);

        }catch (SQLiteException e){
            Log.e("MAIN", "onCreate: "+e.getMessage());
            if(loading != null)
            loading.setVisibility(View.VISIBLE);
        }
    }


}
