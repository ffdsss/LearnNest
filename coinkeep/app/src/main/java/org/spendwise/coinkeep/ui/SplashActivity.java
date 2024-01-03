package org.spendwise.coinkeep.ui;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

import org.spendwise.coinkeep.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initIcon();
        initName();

        new Handler().postDelayed(() -> {startActivity(new Intent(SplashActivity.this, MainActivity.class)); finish();}, 1600);


    }

    private void initIcon() {
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(binding.imIcon, View.TRANSLATION_Y, 0, 300);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(binding.imIcon, View.ALPHA, 0f, 1f);
        int duration = 1500;
        translateYAnimator.setDuration(duration);
        alphaAnimator.setDuration(duration);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateYAnimator, alphaAnimator);
        animatorSet.start();
    }


    private void initName() {
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(binding.tvAppName, View.TRANSLATION_Y, 0, -270);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 0f, 1f);
        int duration = 1500;
        translateYAnimator.setDuration(duration);
        alphaAnimator.setDuration(duration);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateYAnimator, alphaAnimator);
        animatorSet.start();
    }


}