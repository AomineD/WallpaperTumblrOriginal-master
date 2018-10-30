package com.gay.xmen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.gay.xmen.R;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    public static final String key_ur = "sjasdjasdas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.web_id);

        Bundle bondol = getIntent().getExtras();
        String url = "";
        if(bondol != null){
            url = bondol.getString(key_ur);
        }

        webView.loadUrl(url);



    }
}
