package com.example.lineup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.lineup.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PositionInfo extends AppCompatActivity {

    WebView webView;
    WebSettings webSettings;
    int soccer;
    int baseball;
    int basketball;
    int vollyball;
    int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_info);

        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);

        getIntentResult();

        onResultReceived();

    }

    public void getIntentResult() {

        res = getIntent().getIntExtra("result", 0);
    }


    public void onResultReceived() {
        switch (res) {
            case 1:
                //축구
                webView.loadUrl("https://ko.wikipedia.org/wiki/%EC%B6%95%EA%B5%AC%EC%9D%98_%ED%8F%AC%EC%A7%80%EC%85%98");
                soccer = 0;
                break;
            case 2:
                //야구
                webView.loadUrl("https://ko.wikipedia.org/wiki/%EC%95%BC%EA%B5%AC%EC%9D%98_%ED%8F%AC%EC%A7%80%EC%85%98");
                baseball = 0;
                break;
            case 3:
                //농구
                webView.loadUrl("https://ko.wikipedia.org/wiki/%EB%86%8D%EA%B5%AC%EC%9D%98_%ED%8F%AC%EC%A7%80%EC%85%98");
                basketball = 0;
                break;
            case 4:
                //배구
                webView.loadUrl("https://ko.wikipedia.org/wiki/%EB%B0%B0%EA%B5%AC");
                vollyball = 0;
                break;
            default:
                Toast.makeText(this, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show();

        }
    }
}
