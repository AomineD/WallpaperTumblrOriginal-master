package com.gay.xmen.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.gay.xmen.Adapters.WallpaperAdapter;
import com.gay.xmen.R;
import com.gay.xmen.api.ResponseApi;
import com.gay.xmen.api.TumblrApi;
import com.gay.xmen.config.Constant;
import com.gay.xmen.models.TumblrItem;

import java.util.ArrayList;


public class WallpapersAutorFragment extends Fragment {


    private ArrayList<TumblrItem> tumblrItemArrayList = new ArrayList<>();
    private RecyclerView List_wallpaper;
    TextView name_autor;
    private Button botonBack;
    private String url_m;
    private String name;
    private int aki;

    public WallpapersAutorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ResponseApi responseApi = new ResponseApi() {
            @Override
            public void Correct(ArrayList<TumblrItem> response) {
                tumblrItemArrayList.addAll(response);

                aki = Constant.NATIVE_FRECUENCY;

                for(int i=0; i < tumblrItemArrayList.size(); i++){
                    if(aki - 1 == i && !tumblrItemArrayList.get(i).isAD()){

                        TumblrItem itemAd = new TumblrItem();

                        itemAd.setAD(true);


                        tumblrItemArrayList.add(i, itemAd);

                        aki += Constant.NATIVE_FRECUENCY;
                    }
                }

                List_wallpaper.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void Incorrect(String errno) {

            }
        };

        Bundle bondol = getArguments();
        url_m = bondol.getString(Constant.key_bundle1);
name = bondol.getString(Constant.key_name_autor);

        String main_url = Constant.base_url+url_m+Constant.base_url2+Constant.limite_por_pagina+Constant.base_url3;

        TumblrApi api = new TumblrApi(main_url, getContext(), responseApi);

        api.RunApi();


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpapers_autor, container, false);

        List_wallpaper = view.findViewById(R.id.rec);

        WallpaperAdapter adapter = new WallpaperAdapter(getContext(), tumblrItemArrayList, getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        List_wallpaper.setLayoutManager(gridLayoutManager);
        List_wallpaper.setAdapter(adapter);

name_autor = view.findViewById(R.id.autor_name);
botonBack = view.findViewById(R.id.btn_back);



botonBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.button_back);

        botonBack.startAnimation(animation);

        if (getActivity() != null) {
final Activity activity = getActivity();



        activity.onBackPressed();


          //  Log.e("MAIN", "onClick: SI");
        }
    }
});
name_autor.setText(name);

        return view;
    }



}


