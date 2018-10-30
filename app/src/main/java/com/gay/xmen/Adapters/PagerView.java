package com.gay.xmen.Adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.gay.xmen.activities.WallpaperActivity;
import com.gay.xmen.fragments.ViewWallpaper;
import com.gay.xmen.models.TumblrItem;

import java.util.ArrayList;

public class PagerView extends FragmentStatePagerAdapter {


    ArrayList<TumblrItem> ArrayTumblr;
    private ViewWallpaper fragment;

    public void setDefaultpost(int defaultpost) {
        this.defaultpost = defaultpost;
    }

    private int defaultpost = 1030;

    public PagerView(FragmentManager fm, ArrayList<TumblrItem> items) {
        super(fm);

        this.ArrayTumblr = items;
    }

    @Override
    public ViewWallpaper getItem(int position) {
        ViewWallpaper viewWallpaper = new ViewWallpaper();

        Bundle arguments = new Bundle();


    //    Log.e("MAIN", "getItem: defaultposition = "+defaultpost + " position = "+position);

if(position == 0) {
    arguments.putString(WallpaperActivity.key_wall, String.valueOf(ArrayTumblr.get(0).getUrl_image()));
    if(ArrayTumblr.get(defaultpost).isAD()){
        arguments.putBoolean(WallpaperActivity.key_type, true);
    }
    //Log.e("MAIn", "getURL default: "+ArrayTumblr.get(0).getUrl_image().toString());
}else if(defaultpost != position){
    arguments.putString(WallpaperActivity.key_wall, String.valueOf(ArrayTumblr.get(position).getUrl_image()));
    if(ArrayTumblr.get(position).isAD()){
        arguments.putBoolean(WallpaperActivity.key_type, true);
    }
   // Log.e("MAIn", "getURL pos: "+ArrayTumblr.get(defaultpost).getUrl_image().toString());
}else{
    arguments.putString(WallpaperActivity.key_wall, String.valueOf(ArrayTumblr.get(defaultpost).getUrl_image()));
    if(ArrayTumblr.get(defaultpost).isAD()){
        arguments.putBoolean(WallpaperActivity.key_type, true);
    }
//    Log.e("MAIn", "getURL default 2: "+ArrayTumblr.get(defaultpost).getUrl_image().toString());
}



        viewWallpaper.setArguments(arguments);

if(defaultpost == position) {

    fragment = viewWallpaper;

 /*  Log.e("MAIN", "getItem: TO FRAGMENT = "+arguments.getString(WallpaperActivity.key_wall) +"      " +
            "" +
            " pos "+position);*/

}
        return viewWallpaper;


    }

    public ViewWallpaper getFragment(){
      //  Log.e("MAIN", "getFragment: URL -> "+fragment.getUrr().toString() );
        return fragment;
    }

    @Override
    public int getCount() {
        return ArrayTumblr.size();
    }
}
