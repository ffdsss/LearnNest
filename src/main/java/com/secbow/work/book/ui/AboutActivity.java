package com.secbow.work.book.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.webkit.WebSettings;

import androidx.annotation.Nullable;

import com.secbow.work.book.BaseActivity;
import com.secbow.work.book.R;
import com.secbow.work.book.databinding.ActivityAboutBinding;

public class AboutActivity extends BaseActivity {

    private ActivityAboutBinding aboutBinding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(aboutBinding.getRoot());

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(getResources().getColor(R.color.top));
        float topLeftRadius = 0f;
        float topRightRadius = 0f;
        float bottomRightRadius = 15f;
        float bottomLeftRadius = 15f;
        gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        aboutBinding.consTop.setBackground(gradientDrawable);

        aboutBinding.btnBack.setOnClickListener(v -> finish());

        WebSettings webSettings = aboutBinding.webAbout.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);

        String h5Path = "file:///android_asset/j1.html";

        aboutBinding.webAbout.loadUrl(h5Path);
    }
}
