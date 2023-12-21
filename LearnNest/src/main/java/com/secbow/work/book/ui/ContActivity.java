package com.secbow.work.book.ui;



import android.annotation.SuppressLint;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.secbow.work.book.BaseActivity;
import com.secbow.work.book.R;
import com.secbow.work.book.databinding.ActivityContBinding;


public class ContActivity extends BaseActivity {

    private ActivityContBinding contBinding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont);
        contBinding = ActivityContBinding.inflate(getLayoutInflater());
        setContentView(contBinding.getRoot());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(getResources().getColor(R.color.top));
        float topLeftRadius = 0f;
        float topRightRadius = 0f;
        float bottomRightRadius = 15f;
        float bottomLeftRadius = 15f;
        gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        contBinding.consTop.setBackground(gradientDrawable);

        contBinding.btnBack.setOnClickListener(v -> {
            contBinding.webView.goBack();
            finish();
        });

        String loadUrl = getIntent().getStringExtra("loadUrl");

        contBinding.webView.getSettings().setJavaScriptEnabled(true);
        contBinding.webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        contBinding.webView.setWebViewClient(new MWebViewClient());
        contBinding.webView.setWebChromeClient(new MWebChromeClient());

        if (!"".equals(loadUrl)) {
            contBinding.webView.loadUrl(loadUrl);
        }

    }


    class MWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (!TextUtils.isEmpty(title)) {
                contBinding.tvTitle.setText(title);
            }
            super.onReceivedTitle(view, title);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && contBinding.webView.canGoBack()){
            contBinding.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}