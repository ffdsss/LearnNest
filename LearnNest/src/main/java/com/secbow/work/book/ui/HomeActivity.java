package com.secbow.work.book.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.navigation.NavigationView;
import com.secbow.work.book.BaseActivity;
import com.secbow.work.book.R;
import com.secbow.work.book.databinding.ActivityHomeBinding;
import com.secbow.work.book.vo.HomeVo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding homeBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());


        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(getResources().getColor(R.color.top));
        float topLeftRadius = 0f;
        float topRightRadius = 0f;
        float bottomRightRadius = 15f;
        float bottomLeftRadius = 15f;
        gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        homeBinding.includeHome.consTop.setBackground(gradientDrawable);

        initMenu();

        List<HomeVo> homeVos = new ArrayList<>();
        homeVos.add(new HomeVo(R.drawable.html, getString(R.string.home_item_1), 1));
        homeVos.add(new HomeVo(R.drawable.java, getString(R.string.home_item_2), 2));
        homeVos.add(new HomeVo(R.drawable.cplus, getString(R.string.home_item_3), 3));
        homeVos.add(new HomeVo(R.drawable.linux, getString(R.string.home_item_4), 4));
        homeVos.add(new HomeVo(R.drawable.swift, getString(R.string.home_item_5), 5));
        homeVos.add(new HomeVo(R.drawable.javas, getString(R.string.home_item_6), 6));
        homeVos.add(new HomeVo(R.drawable.ruby, getString(R.string.home_item_7), 7));
        homeVos.add(new HomeVo(R.drawable.go, getString(R.string.home_item_8), 8));
        homeVos.add(new HomeVo(R.drawable.php, getString(R.string.home_item_9), 9));
        homeVos.add(new HomeVo(R.drawable.rust, getString(R.string.home_item_10), 10));
        homeVos.add(new HomeVo(R.drawable.scala, getString(R.string.home_item_11), 11));
        homeVos.add(new HomeVo(R.drawable.python, getString(R.string.home_item_12), 12));


        homeBinding.includeHome.rv1.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        homeBinding.includeHome.rv1.addItemDecoration(new HomeDecoration());
        HomeItemAdapter itemAdapter = new HomeItemAdapter(HomeActivity.this);
        itemAdapter.setData(homeVos).setOnItemClickListener(pos -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.putExtra("type", homeVos.get(pos).getType());
            startActivity(intent);
        });
        homeBinding.includeHome.rv1.setAdapter(itemAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    private void initMenu() {
        setDrawerDistance();
        homeBinding.navHome.setItemIconTintList(null);
        homeBinding.navHome.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.side_update:
                    openGP();
                    break;
                case R.id.side_share:
                    String playStoreLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
                    shareContent(playStoreLink);
                    break;
                case R.id.side_about:
                    startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                    break;
                case R.id.side_rate:
                    openGPM();
                    break;
            }
            homeBinding.dlHome.closeDrawers();
            return true;
        });

    }

    @SuppressLint("QueryPermissionsNeeded")
    private void shareContent(String link) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.side_share));
        shareIntent.putExtra(Intent.EXTRA_TEXT, link);

        Intent chooserIntent = Intent.createChooser(shareIntent, getString(R.string.share_to));
        if (chooserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private void openGP() {
        Uri uri = Uri.parse("market://details");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void openGPM() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Modify the manual sliding position of the side scrolling bar
     */
    private void setDrawerDistance() {
        try {
            Field leftDraggierField = homeBinding.dlHome.getClass().getDeclaredField("mLeftDragger");
            leftDraggierField.setAccessible(true);
            ViewDragHelper vdh = (ViewDragHelper) leftDraggierField.get(homeBinding.dlHome);
            Field edgeSizeField = vdh.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(vdh);
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                getDisplay().getRealSize(point);
            } else {
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                wm.getDefaultDisplay().getRealSize(point);
            }
            edgeSizeField.setInt(vdh, Math.max(edgeSize, point.x));
            Field leftCallbackField = homeBinding.dlHome.getClass().getDeclaredField("mLeftCallback");
            leftCallbackField.setAccessible(true);
            ViewDragHelper.Callback vdhCallback = (ViewDragHelper.Callback) leftCallbackField.get(homeBinding.dlHome);
            Field peekRunnableField = vdhCallback.getClass().getDeclaredField("mPeekRunnable");
            peekRunnableField.setAccessible(true);
            Runnable nullRunnable = () -> {
            };
            peekRunnableField.set(vdhCallback, nullRunnable);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    class HomeDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int tag = getResources().getDimensionPixelOffset(R.dimen.rv_div_1);
            int tag4 = getResources().getDimensionPixelOffset(R.dimen.rv_div_4);
            outRect.set(tag, tag4, tag, tag);
        }

    }

}

