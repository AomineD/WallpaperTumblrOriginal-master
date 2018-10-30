package com.gay.xmen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import com.facebook.ads.NativeAdListener;
import com.gay.xmen.activities.WebActivity;
import com.gay.xmen.config.Constant;
import com.gay.xmen.config.PagerAdapter;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.NativeAd;
import com.gay.xmen.fragments.Home;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.orm.SugarContext;

import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private android.support.v7.widget.Toolbar toolbar;

    // ============================== GIF VIEWS ============================== //
    public static final String key_tt = "tytorial";
    private GifImageView gif_img;
    private TextView content;
    private GifImageView tuto_1;
    private GifImageView tuto_2;
    private GifImageView fireworks_gif;

    public static final String keyToBoleaan = "boleaan";
    public static final String keytoint = "keylkey";

    // ======================================================================= //


    private RelativeLayout relativeLayoutTutorial;
    private LinearLayout layout_banner;
    private int valuest = 1;
    private SharedPreferences preferences;
    private MediaPlayer success;
    private MediaPlayer congrats;
    public static NativeAd nativeAds;
    public static InterstitialAd main_interstitial;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetupTransitions();



        SugarContext.init(this);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this);

        success = MediaPlayer.create(this, R.raw.success);
        congrats = MediaPlayer.create(this, R.raw.complete);
        layout_banner = findViewById(R.id.banner_container);

        relativeLayoutTutorial = findViewById(R.id.tutorialrelative);
        tuto_1 = findViewById(R.id.giftuto_1);
        tuto_2 = findViewById(R.id.giftuto_2);
        fireworks_gif = findViewById(R.id.fireworks);

        gif_img = findViewById(R.id.pikaid);
        content = findViewById(R.id.instructions);

        preferences = getPreferences(MODE_PRIVATE);

        if (preferences.getInt(key_tt, 0) == 0) {
            Showtutorial();
        }


        tabLayout = findViewById(R.id.nav_Tab);
        viewPager = findViewById(R.id.vipager);
        toolbar = findViewById(R.id.toolbarMain);
        SetupActionBar();

        ChangeTitle(0);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title1vipager));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title2vipager));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title3vipager));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                ChangeTitle(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);


        ShowDialog();


        AdSettings.setDebugBuild(true);
        SetupAds();
    }



    private void SetupAds() {
nativeAds = new NativeAd(this, getString(R.string.native_ad_fb));
nativeAds.setAdListener(new NativeAdListener() {
    @Override
    public void onMediaDownloaded(Ad ad) {

    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Toast.makeText(MainActivity.this, "Error: "+adError.getErrorMessage(), Toast.LENGTH_LONG).show();
        Log.e("MAINY", "onError: "+adError.getErrorMessage());
    }

    @Override
    public void onAdLoaded(Ad ad) {
        //Toast.makeText(MainActivity.this, "Los nativos cargaron normal", Toast.LENGTH_SHORT).show();
        Home.UpdateNotify();
    }

    @Override
    public void onAdClicked(Ad ad) {

    }

    @Override
    public void onLoggingImpression(Ad ad) {

    }
});
nativeAds.loadAd();

if(Constant.TYPE_ADS == 0){
    AdView banner_facebook = new AdView(this, getString(R.string.Banner_facebook), AdSize.BANNER_HEIGHT_50);

    banner_facebook.loadAd();

    banner_facebook.setAdListener(new AdListener() {
        @Override
        public void onError(Ad ad, AdError adError) {
            Log.e("MAIN", "onErrorBanner: "+adError.getErrorMessage());
        }

        @Override
        public void onAdLoaded(Ad ad) {
            Log.e("MAIN", "onAdLoaded: COMPLETE" );
        }

        @Override
        public void onAdClicked(Ad ad) {

        }

        @Override
        public void onLoggingImpression(Ad ad) {

        }
    });

    layout_banner.addView(banner_facebook);

}else if(Constant.TYPE_ADS == 1){
    com.google.android.gms.ads.AdView banner_google = new com.google.android.gms.ads.AdView(this);

    banner_google.setAdUnitId(getString(R.string.banner_google));
    banner_google.setAdListener(new com.google.android.gms.ads.AdListener(){
        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            Log.e("MAIN", "onAdFailedToLoad: " +i);
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            Log.e("MAIN", "onAdLoaded: COMPLETE");
        }
    });

    banner_google.setAdSize(com.google.android.gms.ads.AdSize.BANNER);

    banner_google.loadAd(new AdRequest.Builder().build());

    layout_banner.addView(banner_google);
}



main_interstitial = new InterstitialAd(this);
main_interstitial.setAdUnitId(getString(R.string.intersticial_id_google));
main_interstitial.setAdListener(new com.google.android.gms.ads.AdListener(){
    @Override
    public void onAdClosed() {
        main_interstitial.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onAdFailedToLoad(int i) {
        main_interstitial.loadAd(new AdRequest.Builder().build());
    }
});

        main_interstitial.loadAd(new AdRequest.Builder().build());

    }


    // ============================================================== Configuración de Toolbar ====================================================================== //
    // ============================================================================================================================================================== //

    private void SetupActionBar() {


        DrawerLayout drawerLayout = findViewById(R.id.drawer);


        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opendrawer, R.string.closedrawer);
        navigationView = findViewById(R.id.navi);

        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void SetupTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new ChangeImageTransform());

        }
    }


    // ====================================================================== Cambiar titulo de toolbar ==================================================================== //
    // ====================================================================================================================================================================== //

    private void ChangeTitle(int position) {

        switch (position) {
            case 0:
                getSupportActionBar().setTitle(R.string.title1vipager);
                break;
            case 1:
                getSupportActionBar().setTitle(R.string.title2vipager);
                break;
            case 2:
                getSupportActionBar().setTitle(R.string.title3vipager);
                break;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shareapp:
                Intent intentShare = new Intent(Intent.ACTION_SEND);


                intentShare.putExtra(Intent.EXTRA_TEXT, "Download WallpaperTumblr to have the best and beatiful wallpapers here: "+getString(R.string.url_google_play)+getPackageName());
intentShare.setType("text/plain");
                startActivity(Intent.createChooser(intentShare, "Choose one..."));

                break;
            case R.id.rateapp:
Intent rate = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_google_play)+getPackageName()));

startActivity(rate);
                break;
            case R.id.policy:
                Intent intent = new Intent(this, WebActivity.class);

                Bundle extras = new Bundle();

                extras.putString(WebActivity.key_ur, getString(R.string.url_policy));

                intent.putExtras(extras);

                startActivity(intent);
                break;
            case R.id.conditions:
                Intent intent_conditions = new Intent(this, WebActivity.class);

                Bundle extras_2 = new Bundle();

                extras_2.putString(WebActivity.key_ur, getString(R.string.url_conditions));

                intent_conditions.putExtras(extras_2);

                startActivity(intent_conditions);

                break;

        }


        return false;
    }

    void Showtutorial() {
       // relativeLayoutTutorial.setVisibility(View.VISIBLE);

       // content.setText(R.string.tuto1);
        //valuest = 2;



    }


    public void Next(View view) {
        switch (valuest) {
            case 2:
                content.setText(R.string.tuto2);
                tuto_1.setVisibility(View.VISIBLE);
                success.start();
                break;
            case 3:
                content.setText(R.string.tuto3);
                tuto_1.setVisibility(View.GONE);
                tuto_2.setVisibility(View.VISIBLE);
                success.start();
                break;
            case 4:
                content.setText(R.string.tuto4);
                tuto_2.setVisibility(View.GONE);
                fireworks_gif.setVisibility(View.VISIBLE);
                congrats.start();
                break;
            case 5:
                relativeLayoutTutorial.setVisibility(View.GONE);
                valuest = 0;
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt(key_tt, 1);
                editor.commit();
                break;
        }

        valuest++;
    }


    private void ShowDialog() {


        if (preferences.getBoolean(keyToBoleaan, true)) {

            if (preferences.getInt(keytoint, 1) >= Constant.Pop_up_frecuency) {
                final SharedPreferences.Editor edit = preferences.edit();

                // =============== MOSTRAR AL FIN ==================== //

                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle(getString(R.string.rate_title));
                alertDialog.setMessage(getString(R.string.rate_message));
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.not_now_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.never_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();

                        edit.putBoolean(keyToBoleaan, false);
                        edit.commit();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.rate_now), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_google_play) + getPackageName()));


                        startActivity(intent);
                        alertDialog.dismiss();
                        edit.putBoolean(keyToBoleaan, false);
                        edit.commit();
                    }
                });

                alertDialog.show();


                // =================================================== //
                edit.putInt(keytoint, 1);
                edit.commit();
            } else {
                SharedPreferences.Editor edit = preferences.edit();

                int loco = preferences.getInt(keytoint, 1);

                loco++;

                edit.putInt(keytoint, loco);
                edit.commit();

            }
        }


    }



}
