package com.gay.xmen.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
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
import com.gay.xmen.config.Constant;
import com.gay.xmen.models.TumblrItem;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import pl.droidsonroids.gif.GifImageView;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.HolderWallpaper>{

    private Context mContext;
    private ArrayList<TumblrItem> tumblrItems;
    private Activity activity;

    private ArrayList<View> views = new ArrayList<>();

    private int akino = 0;
    private InterstitialAd interstitialAd_google;
    private com.facebook.ads.InterstitialAd interstitialAd_facebook;



    public WallpaperAdapter(Context context, ArrayList<TumblrItem> tumblrItemsarray, Activity mActivity){
this.tumblrItems = tumblrItemsarray;
this.mContext = context;
this.activity = mActivity;

    this.interstitialAd_google = new InterstitialAd(context);
    this.interstitialAd_google.setAdUnitId(context.getResources().getString(R.string.intersticial_id_google));


    this.interstitialAd_google.loadAd(new AdRequest.Builder().build());


    }

    @NonNull
    @Override
    public HolderWallpaper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.wallpaper_item, parent, false);

        return new HolderWallpaper(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderWallpaper holder, final int position) {


        if(!tumblrItems.get(position).isAD()) {
            holder.normal_view.setVisibility(View.VISIBLE);
            holder.banner_view.setVisibility(View.GONE);

            Picasso.get().load(tumblrItems.get(position).getUrl_image()).transform(new RoundedCornersTransformation(25, 5)).fit().into(holder.photo, new Callback() {
                @Override
                public void onSuccess() {
                    // Log.e("MAIN", "onSuccess: SUCCESS CSM");
                    holder.gifLoading.setVisibility(View.GONE);
                    holder.photo.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    //Log.e("MAIN", "onError: "+e.getMessage());
                    holder.gifLoading.setVisibility(View.VISIBLE);
                    holder.photo.setVisibility(View.GONE);
                }
            });


            final String urlOfPhoto = String.valueOf(tumblrItems.get(position).getUrl_image());
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ================================== INTERSTICIAL GOOGLE ================================================ //

                        if (interstitialAd_google.isLoaded() && akino == Constant.INTERSTICIAL_FRECUENCY) {
                            interstitialAd_google.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {
                                    interstitialAd_google.loadAd(new AdRequest.Builder().build());

                                    Intent intent = new Intent(mContext, WallpaperActivity.class);
                                    intent.putExtra(WallpaperActivity.key_wall, urlOfPhoto);
                                    intent.putExtra(WallpaperActivity.key_name, tumblrItems.get(position).getName());
                                    intent.putExtra(WallpaperActivity.key_id, position);
                                    intent.putParcelableArrayListExtra(WallpaperActivity.key_list_tumblr, tumblrItems);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                        Pair[] pairs = new Pair[1];
                                        pairs[0] = new Pair<View, String>(holder.photo, "img");

                                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);

                                        mContext.startActivity(intent, options.toBundle());
                                    } else {
                                        mContext.startActivity(intent);
                                    }
                                }

                                @Override
                                public void onAdFailedToLoad(int i) {
                                    super.onAdFailedToLoad(i);
                                    interstitialAd_google.loadAd(new AdRequest.Builder().build());
                                }


                            });

akino = 0;
                            interstitialAd_google.show();
                        } else {
akino++;
                            Intent intent = new Intent(mContext, WallpaperActivity.class);
                            intent.putExtra(WallpaperActivity.key_wall, urlOfPhoto);
                            intent.putExtra(WallpaperActivity.key_name, tumblrItems.get(position).getName());
                            intent.putExtra(WallpaperActivity.key_id, position);
                            intent.putParcelableArrayListExtra(WallpaperActivity.key_list_tumblr, tumblrItems);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                Pair[] pairs = new Pair[1];
                                pairs[0] = new Pair<View, String>(holder.photo, "img");

                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);

                                mContext.startActivity(intent, options.toBundle());
                            } else {
                                mContext.startActivity(intent);
                            }

                        }

                    // ========================== INTERSTICIAL FACEBOOK ==================================== //
/*
                    else{
                        if (interstitialAd_facebook.isAdLoaded()) {
                            interstitialAd_facebook.setAdListener(new InterstitialAdListener() {
                                @Override
                                public void onError(Ad ad, AdError adError) {
                                    Log.e("MAIN", "onErrorIntersticial: "+adError);
                                    interstitialAd_facebook.loadAd();
                                }

                                @Override
                                public void onInterstitialDisplayed(Ad ad) {

                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
                                    interstitialAd_facebook.loadAd();

                                    Intent intent = new Intent(mContext, WallpaperActivity.class);
                                    intent.putExtra(WallpaperActivity.key_wall, urlOfPhoto);
                                    intent.putExtra(WallpaperActivity.key_name, tumblrItems.get(position).getName());
                                    intent.putExtra(WallpaperActivity.key_id, position);
                                    intent.putParcelableArrayListExtra(WallpaperActivity.key_list_tumblr, tumblrItems);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                        Pair[] pairs = new Pair[1];
                                        pairs[0] = new Pair<View, String>(holder.photo, "img");

                                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);

                                        mContext.startActivity(intent, options.toBundle());
                                    } else {
                                        mContext.startActivity(intent);
                                    }

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {
                                    Log.e("MAIN", "onAdLoaded: Loaded? = "+interstitialAd_facebook.isAdLoaded());
                                    interstitialAd_facebook.show();
                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
interstitialAd_facebook.loadAd();

                        } else {

                            Intent intent = new Intent(mContext, WallpaperActivity.class);
                            intent.putExtra(WallpaperActivity.key_wall, urlOfPhoto);
                            intent.putExtra(WallpaperActivity.key_name, tumblrItems.get(position).getName());
                            intent.putExtra(WallpaperActivity.key_id, position);
                            intent.putParcelableArrayListExtra(WallpaperActivity.key_list_tumblr, tumblrItems);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                Pair[] pairs = new Pair[1];
                                pairs[0] = new Pair<View, String>(holder.photo, "img");

                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);

                                mContext.startActivity(intent, options.toBundle());
                            } else {
                                mContext.startActivity(intent);
                            }

                        }
                    }*/
                }
            });
        }else{


            holder.normal_view.setVisibility(View.GONE);



            // ============================== ADS FACEBOOOK ================================ //
            if(MainActivity.nativeAds.isAdLoaded()) {
                holder.banner_view.setVisibility(View.VISIBLE);

                String title = MainActivity.nativeAds.getAdHeadline();
                String button_no = MainActivity.nativeAds.getAdCallToAction();
                holder.title_ad.setText(title);
                holder.button_install.setText(button_no);
                //Log.e("MAIN", "onBindViewHolder:  ACTION = "+button_no );

                if (views.size() > 0) {
                    if (!views.contains(holder.title_ad) && !views.contains(holder.mediaView) && !views.contains(holder.iconView) && !views.contains(holder.button_install)) {
                        views.add(holder.title_ad);
                        views.add(holder.mediaView);
                        views.add(holder.iconView);
                        views.add(holder.button_install);
                    }
                } else {
                    views.add(holder.title_ad);
                    views.add(holder.mediaView);
                    views.add(holder.iconView);
                    views.add(holder.button_install);
                }

                MainActivity.nativeAds.registerViewForInteraction(holder.banner_view, holder.mediaView, holder.iconView, views);
            }
        }


    }


    @Override
    public int getItemCount() {
        return tumblrItems.size();
    }


    class HolderWallpaper extends RecyclerView.ViewHolder{

        private ImageView photo;
        private GifImageView gifLoading;
private RelativeLayout normal_view;


        // ============= NATIVE FACEBOOK ================= //

        private AdIconView iconView;
        private MediaView mediaView;
        private TextView title_ad;
        private RelativeLayout banner_view;
        private Button button_install;


        public HolderWallpaper(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.ad_icon_view);
            button_install = itemView.findViewById(R.id.button_install);
mediaView = itemView.findViewById(R.id.media_fb_ad);
title_ad = itemView.findViewById(R.id.title_ad);

            photo = itemView.findViewById(R.id.photo);
            gifLoading = itemView.findViewById(R.id.gifloading);


            banner_view = itemView.findViewById(R.id.ad_container);

            normal_view = itemView.findViewById(R.id.normal_container);
        }
    }
}
