package com.gay.xmen.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gay.xmen.MainActivity;
import com.gay.xmen.R;
import com.gay.xmen.activities.WallpaperActivity;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewWallpaper extends Fragment {

private Uri urr;
public ImageView image;
private boolean isAD;

    public Uri getUrr() {
        return urr;
    }

    public ViewWallpaper() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bondol = getArguments();

        if(bondol != null){
            urr = Uri.parse(bondol.getString(WallpaperActivity.key_wall));
            isAD = bondol.getBoolean(WallpaperActivity.key_type);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_wallpaper, container, false);

        image = view.findViewById(R.id.wallpaper);

if(urr != null && !isAD) {
    Picasso.get().load(urr).fit().into(image);
}else if(isAD){
    RelativeLayout relativeLayout_ad = view.findViewById(R.id.ad_container);
    AdIconView adIconView = view.findViewById(R.id.ad_icon_view);
    MediaView mediaView = view.findViewById(R.id.media_fb_ad);
    TextView title_ad = view.findViewById(R.id.title_ad);
    Button install_button = view.findViewById(R.id.button_install);

    relativeLayout_ad.setVisibility(View.VISIBLE);

    ArrayList<View> Clickables = new ArrayList<>();

    if(MainActivity.nativeAds.isAdLoaded()){

        title_ad.setText(MainActivity.nativeAds.getAdHeadline());

        install_button.setText(MainActivity.nativeAds.getAdCallToAction());

        Clickables.add(mediaView);
        Clickables.add(adIconView);
        Clickables.add(install_button);
        Clickables.add(title_ad);

        MainActivity.nativeAds.registerViewForInteraction(relativeLayout_ad, mediaView, adIconView, Clickables);

    }






}
        //Log.e("MAIN", "FRAGMENTO: QUESESTE = "+urr.toString());

        return view;
    }

}
