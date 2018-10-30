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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gay.xmen.Adapters.AutorAdapter;
import com.gay.xmen.R;
import com.gay.xmen.api.ResponseAutor;
import com.gay.xmen.api.TumblrApi;
import com.gay.xmen.config.Constant;
import com.gay.xmen.models.TumblrAutor;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements ResponseAutor {

    private ArrayList<TumblrAutor> List_autor = new ArrayList<>();
private AutorAdapter autorAdapter;
private FrameLayout frameLayout;
    private GifImageView loading;
    private TextView message;
    private SwipeRefreshLayout swipeRefreshLayout;


    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


GetData();

        autorAdapter = new AutorAdapter(List_autor, getContext(), getFragmentManager());



    }

   private void GetData(){
       for(int i = 0; i < Constant.usuariosTumblr.length; i++){

           String main_url = Constant.base_url+Constant.usuariosTumblr[i]+Constant.base_url2+Constant.limite_por_pagina+Constant.base_url3;
           Log.e("MAIN", "GetData: "+main_url);
           TumblrApi api = new TumblrApi(main_url, getContext(), this);

           TumblrAutor autor = new TumblrAutor();
           autor.setNameAutor(Constant.usuariosTumblr[i]);

           List_autor.add(autor);

           api.RunApi();


       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_category, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.rec);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
frameLayout = view.findViewById(R.id.fragm);
autorAdapter.SetFrameLayout(frameLayout);


        loading = view.findViewById(R.id.gifloading);

        message = view.findViewById(R.id.errortext);

        swipeRefreshLayout = view.findViewById(R.id.swipe);


        SetupSwipe();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(autorAdapter);

        return view;
    }

    @Override
    public void OnDataLoaded(TumblrAutor autor) {

        for(int i =0; i < List_autor.size(); i++) {
            if(List_autor.get(i).getNameAutor().equals(autor.getNameAutor())) {
                Log.e("MAIN", "OnDataLoaded: " + autor.getNameAutor() + " data: " + autor.getCantidadWallpaper());
                List_autor.set(i , autor);
                autorAdapter.notifyDataSetChanged();
            }else{
                Log.e("MAIN", "OnDataLoaded: el autor se llamaba "+autor.getNameAutor());
            }

        }
        loading.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void OnDataFailedToLoad(String error) {
        loading.setVisibility(View.GONE);
        message.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }


    private void SetupSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List_autor.clear();
                autorAdapter.notifyDataSetChanged();
                GetData();
            }
        });
    }
}
