package com.pacepal.walk.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.pacepal.walk.databinding.ActivityLaunchBinding;

public class LaunchActivity extends BaseActivity {

    private ActivityLaunchBinding launchBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchBinding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(launchBinding.getRoot());
        initEvent();
        new Handler().postDelayed(this::initAnimator, 200);
        new Handler().postDelayed(this::initTv, 300);
        launchBinding.btnStart.setOnClickListener(v -> {
            startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            finish();
        });
    }
    private void initTv() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(launchBinding.tvApp, View.ALPHA, 0f, 1f);
        int duration = 1500;
        alphaAnimator.setDuration(duration);
        alphaAnimator.start();
    }

    private void initEvent() {
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(launchBinding.imIcon, View.TRANSLATION_Y, 0, 260);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(launchBinding.imIcon, View.ALPHA, 0f, 1f);
        int duration = 1500;
        translateYAnimator.setDuration(duration);
        alphaAnimator.setDuration(duration);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateYAnimator, alphaAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void initAnimator() {
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(launchBinding.btnStart, View.TRANSLATION_Y, 0, -200);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(launchBinding.btnStart, View.ALPHA, 0f, 1f);
        int duration = 1000;
        translateYAnimator.setDuration(duration);
        alphaAnimator.setDuration(duration);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateYAnimator, alphaAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        animatorSet.start();

    }


}