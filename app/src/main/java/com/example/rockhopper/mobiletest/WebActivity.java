package com.example.rockhopper.mobiletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String url = getIntent().getStringExtra("url");
        webView = findViewById(R.id.webview);
        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
