package com.gay.xmen.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gay.xmen.Adapters.OnScrollChangeAdapter;
import com.gay.xmen.Adapters.WallpaperAdapter;
import com.gay.xmen.R;
import com.gay.xmen.api.OnLoadMore;
import com.gay.xmen.api.ResponseApi;
import com.gay.xmen.api.TumblrApi;
import com.gay.xmen.config.Constant;
import com.gay.xmen.models.TumblrItem;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements OnLoadMore {


    private ArrayList<TumblrItem> tumblrItemArrayList = new ArrayList<>();
    private RecyclerView List_wallpaper;
    private GifImageView loading;
    private TextView message;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static WallpaperAdapter adapterstt;

    private int id = 0;

    private int last_id = 3;

    private int aki = 0;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

   GetData();





    }

    public static void UpdateNotify() {
        if (adapterstt != null) {
            adapterstt.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        List_wallpaper = view.findViewById(R.id.rec);

        WallpaperAdapter adapter = new WallpaperAdapter(getContext(), tumblrItemArrayList, getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        loading = view.findViewById(R.id.gifloading);

        message = view.findViewById(R.id.errortext);

        swipeRefreshLayout = view.findViewById(R.id.swipe);


adapterstt = adapter;

List_wallpaper.setLayoutManager(gridLayoutManager);
List_wallpaper.setAdapter(adapter);

        SetupSwipe();
        aki = Constant.NATIVE_FRECUENCY;

        return view;
    }

    private void SetupSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tumblrItemArrayList.clear();
                List_wallpaper.getAdapter().notifyDataSetChanged();
                GetDataFromZero();
            }
        });

OnScrollChangeAdapter changeAdapter = new OnScrollChangeAdapter(List_wallpaper, this);

List_wallpaper.setOnScrollListener(changeAdapter);

    }

    private void GetData(){

        ResponseApi responseApi = new ResponseApi() {
            @Override
            public void Correct(ArrayList<TumblrItem> response) {
                tumblrItemArrayList.addAll(response);



                for(int i=0; i < tumblrItemArrayList.size(); i++){
                    if(aki - 1 == i && !tumblrItemArrayList.get(i).isAD()){

                        TumblrItem itemAd = new TumblrItem();

                        itemAd.setAD(true);


                        tumblrItemArrayList.add(i, itemAd);

                        aki += Constant.NATIVE_FRECUENCY;
                    }
                }


                List_wallpaper.getAdapter().notifyDataSetChanged();
                loading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                Log.e("MAIN", "Correct: "+response.size());
            }

            @Override
            public void Incorrect(String errno) {
                loading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                message.setVisibility(View.VISIBLE);
            }
        };


     //   Log.e("MAIN", "GetData: "+Constant.usuariosTumblr[id]);
            String main_url = Constant.base_url + Constant.usuariosTumblr[id] + Constant.base_url2 + Constant.limite_por_pagina + Constant.base_url3;

            TumblrApi api = new TumblrApi(main_url, getContext(), responseApi);

            api.RunApi();

    }

    private void GetDataFromZero(){
aki = Constant.NATIVE_FRECUENCY;


        ResponseApi responseApi = new ResponseApi() {
            @Override
            public void Correct(ArrayList<TumblrItem> response) {
                tumblrItemArrayList.addAll(response);



                for(int i=0; i < tumblrItemArrayList.size(); i++){
                    if(aki - 1 == i && !tumblrItemArrayList.get(i).isAD()){

                        TumblrItem itemAd = new TumblrItem();

                        itemAd.setAD(true);


                        tumblrItemArrayList.add(i, itemAd);

                        aki += Constant.NATIVE_FRECUENCY;
                    }
                }


                List_wallpaper.getAdapter().notifyDataSetChanged();
                loading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                Log.e("MAIN", "Correct: "+response.size());
            }

            @Override
            public void Incorrect(String errno) {
                loading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                message.setVisibility(View.VISIBLE);
            }
        };


        //   Log.e("MAIN", "GetData: "+Constant.usuariosTumblr[id]);
        String main_url = Constant.base_url + Constant.usuariosTumblr[0] + Constant.base_url2 + Constant.limite_por_pagina + Constant.base_url3;

        TumblrApi api = new TumblrApi(main_url, getContext(), responseApi);

        api.RunApi();
    }

    @Override
    public void LoadMore() {
        id++;

        if(id < Constant.usuariosTumblr.length && last_id != id) {
            swipeRefreshLayout.setRefreshing(true);
            last_id = id;
            GetData();
        }
    }
}
