package com.secbow.work.book.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.secbow.work.book.BaseActivity;
import com.secbow.work.book.databinding.ActivityLoadBinding;

public class LoadActivity extends BaseActivity {

    private ActivityLoadBinding loadBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadBinding = ActivityLoadBinding.inflate(getLayoutInflater());
        setContentView(loadBinding.getRoot());

        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(loadBinding.imgLogo, "translationY", 0, 300);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(loadBinding.imgLogo, "alpha", 0f, 1f);
        int duration = 1700;
        translateYAnimator.setDuration(duration);
        alphaAnimator.setDuration(duration);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateYAnimator, alphaAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(LoadActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }, 200);
            }
        });
        animatorSet.start();
    }
}