package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class ActivityWeb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = findViewById(R.id.WebView);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        if(url == null || url.equals("")) {
            Toast.makeText(this, "URL is invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        webView.loadUrl(url);
    }
}