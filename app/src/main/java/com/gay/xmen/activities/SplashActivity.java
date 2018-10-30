package com.gay.xmen.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.gay.xmen.MainActivity;
import com.gay.xmen.R;
import com.gay.xmen.intro.IntroActivity;

import io.fabric.sdk.android.Fabric;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    public static final long segundos = 7;
public static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        RequestPermiss();

preferences = getPreferences(MODE_PRIVATE);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(preferences.getInt(MainActivity.key_tt, 0) == 0){
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));

                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                }
            }
        }, 1000 * segundos);
    }

    // =============== REQUEST PERMISS ===================== //
    private void RequestPermiss() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Log.e("MAIN", "RequestPermiss: "+ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE));
        }else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 333);
                Log.e("MAIN", "RequestPermiss: REQUESTING ");
            }
        }

    }

    // ========================================== //
}
