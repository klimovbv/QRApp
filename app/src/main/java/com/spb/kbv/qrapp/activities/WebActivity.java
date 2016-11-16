package com.spb.kbv.qrapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.spb.kbv.qrapp.R;
import com.spb.kbv.qrapp.activities.MainActivity;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = (WebView) findViewById(R.id.web_view);
        assert webView != null;
        webView.loadUrl(getIntent().getStringExtra(MainActivity.WEB_URL));
    }

}
