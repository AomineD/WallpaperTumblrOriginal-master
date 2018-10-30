package com.gay.xmen.activities;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gay.xmen.Adapters.PagerView;
import com.gay.xmen.R;
import com.gay.xmen.api.DownloadArchiveResponse;
import com.gay.xmen.api.Downloader;
import com.gay.xmen.config.FavoritesItem;
import com.gay.xmen.fragments.SetWallpaperFragment;
import com.gay.xmen.models.TumblrItem;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


import pl.droidsonroids.gif.GifImageView;

public class WallpaperActivity extends AppCompatActivity implements DownloadArchiveResponse {

    // =========== Key to bundle =========== //
    public static final String key_name = "kksda";
    public static final String key_wall = "sjdas";
    public static final String key_id = "isdasd";
    public static final String key_type = "jasdkasd";
    public static final String key_list_tumblr = "sdkskdawe";
    private boolean isWallpaper = true;

    // ===================================== //

    private String url;
    private String name_wall;
    private int idname;
    private long id;
    private boolean favorites;
    private List<FavoritesItem> favoritesItems;
    private ArrayList<TumblrItem> tumblrItemArrayList;
    private PagerView papager;
    private Menu main_men;

    // ====================== VIEWS ======================= //

    private ImageView phototo;
    private Toolbar toolbarsito;
    private Button actionButton;
    private Button setWallpaper;
    private Button setLocker;
private FragmentTransaction fragmentTransaction;
private SetWallpaperFragment fragment;
private RelativeLayout relativeLayoutok;
private ViewPager vipager;
private TextView textToOk;

    // ====================== GIF ==================== //
    private GifImageView fav_animation;
    private GifImageView right_gif;
    private GifImageView left_gif;
    private long segundos = 2;
private boolean boleana = false;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        SharedPreferences preferences = getPreferences(MODE_PRIVATE);




        Log.e("MAIN", "onActivityResult: "+requestCode+" DEFAULT: "+UCrop.REQUEST_CROP);
if(requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
    Uri uri = UCrop.getOutput(data);
    Log.e("MAIN", "onActivityResult: Uri = "+uri);
    final WallpaperManager wp = WallpaperManager.getInstance(this);



    if(preferences.getInt(key_type, 0) == 0) {


       // Picasso.get().load(uri).fit().into(phototo);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

            ActivateRelative();
            textToOk.setText(R.string.wallpaper_are_ready);

            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("mimeType", "image/*");
            startActivity(Intent.createChooser(intent, "Set as: "));
        }else{
            Uri real = null;


            File fo = null;
            if (uri != null) {
                fo = new File(uri.getPath());
            }

            try {
                //  Log.e("MAIN", "onActivityResult: Autority "+getApplicationContext().getPackageName()+".provider");
                real = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".provider", fo);
            }
            catch (RuntimeException e){
                Log.e("MAIN", "RUNTIME: "+e.getMessage());
            }


            ActivateRelative();
            textToOk.setText(R.string.wallpaper_are_ready);

            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(real, "image/*");
            intent.putExtra("mimeType", "image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Set as: "));
        }

    }else if(preferences.getInt(key_type, 0) == 1){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

            ActivateRelative();
            textToOk.setText(R.string.lockscreen_are_ready);

            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("mimeType", "image/*");
            startActivity(Intent.createChooser(intent, "Choose loockscreen selector"));
        }else{
            Uri real = null;


            File fo = null;
            if (uri != null) {
                fo = new File(uri.getPath());
            }

            try {
                //  Log.e("MAIN", "onActivityResult: Autority "+getApplicationContext().getPackageName()+".provider");
                real = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".provider", fo);
            }
            catch (RuntimeException e){
                Log.e("MAIN", "RUNTIME: "+e.getMessage());
            }


            ActivateRelative();
            textToOk.setText(R.string.lockscreen_are_ready);

            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(real, "image/*");
            intent.putExtra("mimeType", "image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Choose loockscreen selector"));
        }
        //startActivity(intent);

    } else if(preferences.getInt(key_type, 0) == 2){
if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
    ActivateRelative();
    textToOk.setText(R.string.sent_whatsapp);

    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    intent.setDataAndType(uri, "image/*");
    intent.putExtra("mimeType", "image/*");
    intent.setPackage("com.whatsapp");
    Log.e("MAIN", "onActivityResult: "+uri);
    // startActivity(Intent.createChooser(intent, "Choose loockscreen"));
    startActivity(intent);

}else{
    ActivateRelative();
    textToOk.setText(R.string.sent_whatsapp);
    Uri real = null;


    File fo = null;
    if (uri != null) {
        fo = new File(uri.getPath());
    }

    try {
    real = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".provider", fo);
    }
catch (RuntimeException e){
        Log.e("MAIN", "RUNTIME: "+e.getMessage());
    }

    Log.e("MAIN", "onActivityResult: "+uri);

    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    intent.setDataAndType(real, "image/*");
    intent.putExtra("mimeType", "image/*");
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.setPackage("com.whatsapp");

           startActivity(intent);

}
    }



}else if(resultCode == UCrop.RESULT_ERROR){
    final Throwable cropError = UCrop.getError(data);


    Log.e("MAIN", "onActivityResult: error "+cropError.getMessage());
}

       // Log.e("MAIN", "Result: "+resultCode+ " OK: "+RESULT_OK+" kke = "+kke);

if(resultCode == RESULT_CANCELED){
//    Log.e("MAIN", "PACKAGE NAME: "+data.getSelector().getPackage());
}

    }


    private void ActivateRelative(){
        relativeLayoutok.setVisibility(View.VISIBLE);

        MediaPlayer mp = MediaPlayer.create(this, R.raw.complete);

        mp.start();

    }


// =============================================== //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
//SugarContext.init(this);
        phototo = findViewById(R.id.wallpaper);
        toolbarsito = findViewById(R.id.tolbar);
relativeLayoutok = findViewById(R.id.relativeok);
textToOk = findViewById(R.id.instructions);




vipager = findViewById(R.id.vipager);
// =============== GIFS =================== //

fav_animation = findViewById(R.id.fav_gif);
right_gif = findViewById(R.id.right_gif);
left_gif = findViewById(R.id.left_gif);

// ======================================== //

        Bundle bondol = getIntent().getExtras();

        if(bondol != null){
            idname = bondol.getInt(key_id);
            url = bondol.getString(key_wall);
            name_wall = bondol.getString(key_name);
            tumblrItemArrayList = bondol.getParcelableArrayList(key_list_tumblr);
        }

        Picasso.get().load(Uri.parse(url)).fit().into(phototo);

SetupToolbar();


SetupButtons();
SetupViewPager();


    }


 // ======================================================================== ViewPager Configuration ========================================================================= //
 // ========================================================================================================================================================================== //

    private void SetupViewPager() {
        boleana = false;
         papager = new PagerView(getSupportFragmentManager(), tumblrItemArrayList);


        vipager.setAdapter(papager);
vipager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(vipager.getCurrentItem() == 0) {
            papager.setDefaultpost(position);
            if(tumblrItemArrayList.get(position).isAD()){
                actionButton.setVisibility(View.GONE);
            }else{
                actionButton.setVisibility(View.VISIBLE);
            }
        }else{
            papager.setDefaultpost(position + 1);

            if(tumblrItemArrayList.get(position).isAD()){
                actionButton.setVisibility(View.GONE);
            }else{
                actionButton.setVisibility(View.VISIBLE);
            }
        }


        Uri newUri = tumblrItemArrayList.get(position).getUrl_image();
        Picasso.get().load(newUri).fit().into(phototo);
url = String.valueOf(tumblrItemArrayList.get(position).getUrl_image());
CheckFav();

        Animation right = AnimationUtils.loadAnimation(WallpaperActivity.this, R.anim.zoomout);
        Animation left = AnimationUtils.loadAnimation(WallpaperActivity.this, R.anim.zoomout);


if(right_gif.getAnimation() == null || right_gif.getAnimation() != right && !boleana) {
  //  Log.e("MAIN", "onPageScrolled: ANIMANDO" );
    right_gif.setAnimation(right);

    left_gif.setAnimation(left);
    boleana = true;
}

       // Log.e("MAIN", "onPageScrolled: Position = "+position+ ", offset = "+positionOffset);




    }

    @Override
    public void onPageSelected(int position) {
      //vipager.setCurrentItem(position);
        if(vipager.getCurrentItem() == 0) {
            papager.setDefaultpost(position);
        }else{
            papager.setDefaultpost(position + 1);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if(state == 2) {

            Log.e("MAIN", "onPageScrollStateChanged: "+main_men.getItem(0).getTitle());

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Animation right = AnimationUtils.loadAnimation(WallpaperActivity.this, R.anim.zoomin);
                            Animation left = AnimationUtils.loadAnimation(WallpaperActivity.this, R.anim.zoomin);

                            if (right_gif.getAnimation() == null || right_gif.getAnimation() != right && boleana) {
                                //Log.e("MAIN", "onPageScrolled: ANIMANDO" );
                                right_gif.startAnimation(right);

                                left_gif.startAnimation(left);
                                boleana = false;
                            }

                        }
                    });
                }
            }, 2500);
        }

        //Log.e("MAIN", "onPageScrollStateChanged: status = "+state);

    }
});

 vipager.setCurrentItem(idname);

if(vipager.getCurrentItem() == 0) {
    papager.setDefaultpost(vipager.getCurrentItem());
}else{
    papager.setDefaultpost(vipager.getCurrentItem() + 1);
}



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Animation right = AnimationUtils.loadAnimation(WallpaperActivity.this, R.anim.zoomin);
                        Animation left = AnimationUtils.loadAnimation(WallpaperActivity.this, R.anim.zoomin);

                        if (right_gif.getAnimation() == null || right_gif.getAnimation() != right && boleana) {
                            //Log.e("MAIN", "onPageScrolled: ANIMANDO" );
                            right_gif.startAnimation(right);

                            left_gif.startAnimation(left);
                            boleana = false;
                        }

                    }
                });
            }
        }, 2500);

final float MIN_SCALE = 0.5f;

vipager.setPageTransformer(false, new ViewPager.PageTransformer() {
    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0f);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            page.setAlpha(1f);
            page.setTranslationX(0f);
            page.setScaleX(1f);
            page.setScaleY(1f);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            page.setAlpha(1 - position);

            // Counteract the default slide transition
            page.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0f);
        }
    }
});


    }



    // ======================================================================================================================== //
    // ================================================ SETUP DE LOS BOTONES ================================================== //
    // ======================================================================================================================== //

    private void SetupButtons() {
        actionButton = findViewById(R.id.setWallpaper);
setWallpaper = findViewById(R.id.likewallpaper);
setLocker = findViewById(R.id.likelocker);


relativeLayoutok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        relativeLayoutok.setVisibility(View.GONE);
    }
});


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(setWallpaper.getVisibility() == View.GONE) {

                    setWallpaper.setVisibility(View.VISIBLE);


                    Animation animation_size = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);


                    //setWallpaper.startAnimation(animation_size);
                    setWallpaper.setAnimation(animation_size);

                    // ========= segundo boton =========== //
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Animation animation_size = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
                                    setLocker.setVisibility(View.VISIBLE);
                                    setLocker.startAnimation(animation_size);
                                }
                            });
                        }
                    }, 500);

                }else{
                   Gonebuttons();

                }

            }
        });


        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWallpaper = true;
                DownloadFileMethodWithResponse();

            }
        });

        setLocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWallpaper = false;
                isLocker();
                DownloadFileMethodWithResponse();
            }
        });



        //fragmentTransaction.replace(R.id.frame_ly, );


    }

    // ============================================================================================================================================ //
    // ===================================================== OCULTAR BOTONES   ==================================================================== //
    // ============================================================================================================================================ //

    private void Gonebuttons(){
        Animation animation_size = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);


        setWallpaper.startAnimation(animation_size);

        // ========= segundo boton =========== //
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Animation animation_size = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
                        setLocker.startAnimation(animation_size);
                    }
                });
            }
        }, 700);




        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setLocker.setVisibility(View.GONE);
                        setWallpaper.setVisibility(View.GONE);
                    }
                });
            }
        }, 2500);
    }

    // ========================================================================================================================================================= //
    // ========================================================== INICIAR FRAGMENT PARA SETEAR WALLPAPER ======================================================= //
    // ========================================================================================================================================================= //

    private void GoOn(Uri uri){

        Gonebuttons();
        //uri = Uri.parse(url);
        setLocker.setVisibility(View.GONE);
        setWallpaper.setVisibility(View.GONE);

        FragmentManager manager = getSupportFragmentManager();

        fragment = new SetWallpaperFragment();

        Bundle bondol = new Bundle();

        bondol.putString(SetWallpaperFragment.key_bondol, url);
        bondol.putString(SetWallpaperFragment.key_path, String.valueOf(uri));

        fragment.setArguments(bondol);

        fragmentTransaction = manager.beginTransaction();

      //  fragmentTransaction.addToBackStack("back");

       // fragmentTransaction.back
        fragmentTransaction.setCustomAnimations(R.anim.lefttoright, R.anim.righttoleft);




        fragmentTransaction.replace(R.id.frame_ly, fragment);

        fragmentTransaction.commit();
        Log.e("MAIN", "onClick: "+fragment.toString() );

    }


    // ======================================================================================================================== //
    // ======================================================================================================================== //
    // ======================================================================================================================== //

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

MenuInflater menuInflater = getMenuInflater();

menuInflater.inflate(R.menu.wallpapermenu, menu);

        try {
            favoritesItems = FavoritesItem.listAll(FavoritesItem.class);
            //Log.e("MAIN", "onCreateOptionsMenu: SIZE "+menu.size() );
            for(int i=0; i < favoritesItems.size(); i++){
                if(favoritesItems.get(i).getUrl().equals(url)){
                    favorites = true;
                    id = favoritesItems.get(i).getId();
menu.getItem(0).setIcon(R.drawable.favorite_btn_on);
               //     Log.e("MAIN", "onCreateOptionsMenu: INTO "+favoritesItems.get(i).getUrl());
                    break;
                }
             //   Log.e("MAIN", "onCreateOptionsMenu: OUT "+favoritesItems.get(i).getUrl());

            }

        }catch (SQLiteException e){
            Log.e("MAIN", "Error SQLite: "+e.getMessage());
        }

        main_men = menu;

return true;

    }

    // ====================================================== CHEQUEAR FAVORITOS ============================================================ //

    private void CheckFav(){
        if(main_men != null) {
            main_men.getItem(0).setIcon(R.drawable.favorite_btn);
            favorites = false;
            try {
                favoritesItems = FavoritesItem.listAll(FavoritesItem.class);
                //Log.e("MAIN", "onCreateOptionsMenu: SIZE "+menu.size() );
                for (int i = 0; i < favoritesItems.size(); i++) {
                    if (favoritesItems.get(i).getUrl().equals(url)) {
                        favorites = true;
                        id = favoritesItems.get(i).getId();
                        main_men.getItem(0).setIcon(R.drawable.favorite_btn_on);
                        favorites = true;
                        //     Log.e("MAIN", "onCreateOptionsMenu: INTO "+favoritesItems.get(i).getUrl());
                        break;
                    }
                    //   Log.e("MAIN", "onCreateOptionsMenu: OUT "+favoritesItems.get(i).getUrl());

                }

            } catch (SQLiteException e) {
                Log.e("MAIN", "Error SQLite: " + e.getMessage());
            }
        }
    }

    // =============================================== DEFAULT METHODS ==================================================== //

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!tumblrItemArrayList.get(vipager.getCurrentItem()).isAD()) {
            switch (item.getItemId()) {
                case R.id.download:
                    DownloadFileMethod();
                    break;
                case R.id.favorite:
                    if (favorites) {

                        FavoritesItem fav = FavoritesItem.findById(FavoritesItem.class, id);
                        fav.delete();
                        favorites = false;
                        item.setIcon(R.drawable.favorite_btn);


//fav_animation.setAnimation();


                    } else {
                        item.setIcon(R.drawable.favorite_btn_on);
                        favorites = true;
                        FavoritesItem fav = new FavoritesItem();
                        url = String.valueOf(tumblrItemArrayList.get(vipager.getCurrentItem()).getUrl_image());
                        fav.setUrl(url);
                        fav.setName(name_wall);
                        fav.save();
                        id = fav.getId();
                        fav_animation.setVisibility(View.VISIBLE);
                        Noactive();
                    }
                    break;


            }



        }
        return true;
    }

    private void Noactive() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      fav_animation.setVisibility(View.GONE);
                      Log.e("MAIN", "run: NO VISIBLE");
                  }
              });
            }
        }, segundos*1000);


    }
    // ======================================================================================================================== //
// ================================================== Descargas y permisos ======================================================= //
// ======================================================================================================================== //

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 96){
            DownloadFileMethod();
        }else if(requestCode == 99){
            DownloadFileMethodWithResponse();
        }

    }

    private void DownloadFileMethod() {



        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 96);
            }



        }else {
            ProgressDialog Dialogo = new ProgressDialog(this);
            final Downloader downloader =
                    new Downloader(this, Dialogo);


            Dialogo.setMessage("Downloading");
            Dialogo.setIndeterminate(true);
            Dialogo.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            Dialogo.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloader.cancel(true);
                }
            });

url = String.valueOf(tumblrItemArrayList.get(vipager.getCurrentItem()).getUrl_image());
            downloader.execute(url, name_wall + "_" + idname);

        }
    }

    private void DownloadFileMethodWithResponse() {



        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
            }



        }else {
            ProgressDialog Dialogo = new ProgressDialog(this);
            final Downloader downloader =
                    new Downloader(this, Dialogo, this);


            Dialogo.setMessage("Downloading");
            Dialogo.setIndeterminate(true);
            Dialogo.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            Dialogo.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloader.cancel(true);
                }
            });

            url = String.valueOf(tumblrItemArrayList.get(vipager.getCurrentItem()).getUrl_image());
            downloader.execute(url, name_wall + "_" + idname);

        }
    }



    // ======================================================================================================================== //
    // ======================================================================================================================== //
    // ======================================================================================================================== //

    private void SetupToolbar() {
        toolbarsito.setTitleTextColor(getResources().getColor(R.color.white));
setSupportActionBar(toolbarsito);

        Objects.requireNonNull(getSupportActionBar()).setTitle(name_wall);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //SugarContext.terminate();
    }

    @Override
    public void Donwloaded(Uri path) {
        Gonebuttons();
if(isWallpaper) {
    GoOn(path);
}else {
   SetCourt();
}
    }

private Uri final_data;
    private void SetCourt(){
        SetupFinalData();

          //getSupportFragmentManager().beginTransaction().remove(SetWallpaperFragment.this).commit();


            Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name) + "/" + "jaj.png");

            //  Log.e("MAIN", "onClick: URI "+uri.toString());
            UCrop.of(final_data, uri).withAspectRatio(9, 16).withMaxResultSize(2280, 1800).start(this);

        }

    private void SetupFinalData(){

      /*  PagerView pagerr = (PagerView)vipager.getAdapter();



        ImageView img = null;

        if (pagerr != null) {

            img = pagerr.getFragment().image;
        }

        Log.e("MAIN", "SetupFinalData: "+(img!=null)+" y "+(pagerr!=null));
*/

        BitmapDrawable drawable = (BitmapDrawable) phototo.getDrawable();


        Bitmap batmap = drawable.getBitmap();

        //Log.e("MAIN", "CropImage: bitmap = "+(batmap!=null));
        if (batmap != null) {
            final_data = SetWallpaperFragment.GetUriFromImage(batmap, this);
        }
    }




    @Override
    public void Failed(String errno) {

    }


    private void isLocker(){
        try {
            SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();

            editor.putInt(WallpaperActivity.key_type, 1);

            editor.commit();


        }catch (NullPointerException e){
            Log.e("MAIN", "IsWallpaper: "+e.getMessage());
        }
    }
}
