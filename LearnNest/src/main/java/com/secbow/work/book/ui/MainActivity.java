package com.secbow.work.book.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.secbow.work.book.BaseActivity;
import com.secbow.work.book.R;
import com.secbow.work.book.databinding.ActivityMainBinding;
import com.secbow.work.book.vo.ItemVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mainBinding;
    private List<ItemVo> itemVoList;

    private ItemAdapter itemAdapter;

    private int type;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(getResources().getColor(R.color.top));
        float topLeftRadius = 0f;
        float topRightRadius = 0f;
        float bottomRightRadius = 15f;
        float bottomLeftRadius = 15f;
        gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        mainBinding.consTop.setBackground(gradientDrawable);
        type = getIntent().getIntExtra("type", 1);
        initData(type);

        mainBinding.edSearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        mainBinding.btnBack.setOnClickListener(v -> finish());

        mainBinding.rv1.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mainBinding.rv1.addItemDecoration(new MyDecoration());
        itemAdapter = new ItemAdapter(MainActivity.this);
        itemAdapter.setData(itemVoList).setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(MainActivity.this, ContActivity.class);
                intent.putExtra("loadUrl", itemVoList.get(pos).getUrl());
                startActivity(intent);
            }
        });
        mainBinding.rv1.setAdapter(itemAdapter);
        mainBinding.edSearch.setSingleLine(true);
        mainBinding.edSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mainBinding.edSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String inputText = textView.getText().toString();
                if (!TextUtils.isEmpty(inputText)) {
                    List<ItemVo> newItemVoList = new ArrayList<>();
                    for (ItemVo itemVo: itemVoList) {
                        if (itemVo.getTitle().toUpperCase(Locale.ROOT).contains(inputText.toUpperCase(Locale.ROOT))) {
                            newItemVoList.add(itemVo);
                        }
                    }
                    itemAdapter.setData(newItemVoList);
                    itemAdapter.notifyDataSetChanged();
                }
                hideKeyboard(mainBinding.edSearch);
                return true;
            }
            return false;
        });

        mainBinding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputText = s.toString();
                if (TextUtils.isEmpty(inputText)) {
                    itemAdapter.setData(itemVoList);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initData(int type) {
        itemVoList = new ArrayList<>();
        switch (type) {
            case 1:
                itemVoList.add(new ItemVo(R.drawable.h5_1, getString(R.string.h5_1), getString(R.string.h5_1_t), getString(R.string.h5_1_d)));
                itemVoList.add(new ItemVo(R.drawable.h5_2, getString(R.string.h5_2), getString(R.string.h5_2_t), getString(R.string.h5_2_d)));
                itemVoList.add(new ItemVo(R.drawable.h5_3, getString(R.string.h5_3), getString(R.string.h5_3_t), getString(R.string.h5_3_d)));
                itemVoList.add(new ItemVo(R.drawable.h5_4, getString(R.string.h5_4), getString(R.string.h5_4_t), getString(R.string.h5_4_d)));
                itemVoList.add(new ItemVo(R.drawable.h5_5, getString(R.string.h5_5), getString(R.string.h5_5_t), getString(R.string.h5_5_d)));
                itemVoList.add(new ItemVo(R.drawable.h5_6, getString(R.string.h5_6), getString(R.string.h5_6_t), getString(R.string.h5_6_d)));
                itemVoList.add(new ItemVo(R.drawable.h5_7, getString(R.string.h5_7), getString(R.string.h5_7_t), getString(R.string.h5_7_d)));
                itemVoList.add(new ItemVo(R.drawable.h5_8, getString(R.string.h5_8), getString(R.string.h5_8_t), getString(R.string.h5_8_d)));
                break;
            case 2:
                itemVoList.add(new ItemVo(R.drawable.j1, getString(R.string.j_1), getString(R.string.j_1_t), getString(R.string.j_1_d)));
                itemVoList.add(new ItemVo(R.drawable.j2, getString(R.string.j_2), getString(R.string.j_2_t), getString(R.string.j_2_d)));
                itemVoList.add(new ItemVo(R.drawable.j3, getString(R.string.j_3), getString(R.string.j_3_t), getString(R.string.j_3_d)));
                itemVoList.add(new ItemVo(R.drawable.j4, getString(R.string.j_4), getString(R.string.j_4_t), getString(R.string.j_4_d)));
                itemVoList.add(new ItemVo(R.drawable.j5, getString(R.string.j_5), getString(R.string.j_5_t), getString(R.string.j_5_d)));
                itemVoList.add(new ItemVo(R.drawable.j6, getString(R.string.j_6), getString(R.string.j_6_t), getString(R.string.j_6_d)));
                break;
            case 3:
                itemVoList.add(new ItemVo(R.drawable.c1, getString(R.string.c_1), getString(R.string.c_1_t), getString(R.string.c_1_d)));
                itemVoList.add(new ItemVo(R.drawable.c2, getString(R.string.c_2), getString(R.string.c_2_t), getString(R.string.c_2_d)));
                itemVoList.add(new ItemVo(R.drawable.c3, getString(R.string.c_3), getString(R.string.c_3_t), getString(R.string.c_3_d)));
                itemVoList.add(new ItemVo(R.drawable.c4, getString(R.string.c_4), getString(R.string.c_4_t), getString(R.string.c_4_d)));
                itemVoList.add(new ItemVo(R.drawable.c5, getString(R.string.c_5), getString(R.string.c_5_t), getString(R.string.c_5_d)));
                itemVoList.add(new ItemVo(R.drawable.c6, getString(R.string.c_6), getString(R.string.c_6_t), getString(R.string.c_6_d)));
                itemVoList.add(new ItemVo(R.drawable.c7, getString(R.string.c_7), getString(R.string.c_7_t), getString(R.string.c_7_d)));
                itemVoList.add(new ItemVo(R.drawable.c8, getString(R.string.c_8), getString(R.string.c_8_t), getString(R.string.c_8_d)));
                itemVoList.add(new ItemVo(R.drawable.c9, getString(R.string.c_9), getString(R.string.c_9_t), getString(R.string.c_9_d)));
                break;
            case 4:
                itemVoList.add(new ItemVo(R.drawable.l1, getString(R.string.l_1), getString(R.string.l_1_t), getString(R.string.l_1_d)));
                itemVoList.add(new ItemVo(R.drawable.l2, getString(R.string.l_2), getString(R.string.l_2_t), getString(R.string.l_2_d)));
                itemVoList.add(new ItemVo(R.drawable.l3, getString(R.string.l_3), getString(R.string.l_3_t), getString(R.string.l_3_d)));
                itemVoList.add(new ItemVo(R.drawable.l4, getString(R.string.l_4), getString(R.string.l_4_t), getString(R.string.l_4_d)));
                break;
            case 5:
                itemVoList.add(new ItemVo(R.drawable.s1, getString(R.string.s_1), getString(R.string.s_1_t), getString(R.string.s_1_d)));
                itemVoList.add(new ItemVo(R.drawable.s2, getString(R.string.s_2), getString(R.string.s_2_t), getString(R.string.s_2_d)));
                itemVoList.add(new ItemVo(R.drawable.s3, getString(R.string.s_3), getString(R.string.s_3_t), getString(R.string.s_3_d)));
                itemVoList.add(new ItemVo(R.drawable.s4, getString(R.string.s_4), getString(R.string.s_4_t), getString(R.string.s_4_d)));
                break;
            case 6:
                itemVoList.add(new ItemVo(R.drawable.js1, getString(R.string.js_1), getString(R.string.js_1_t), getString(R.string.js_1_d)));
                itemVoList.add(new ItemVo(R.drawable.js2, getString(R.string.js_2), getString(R.string.js_2_t), getString(R.string.js_2_d)));
                itemVoList.add(new ItemVo(R.drawable.js3, getString(R.string.js_3), getString(R.string.js_3_t), getString(R.string.js_3_d)));
                itemVoList.add(new ItemVo(R.drawable.js4, getString(R.string.js_4), getString(R.string.js_4_t), getString(R.string.js_4_d)));
                itemVoList.add(new ItemVo(R.drawable.js5, getString(R.string.js_5), getString(R.string.js_5_t), getString(R.string.js_5_d)));
                itemVoList.add(new ItemVo(R.drawable.js6, getString(R.string.js_6), getString(R.string.js_6_t), getString(R.string.js_6_d)));
                itemVoList.add(new ItemVo(R.drawable.js7, getString(R.string.js_7), getString(R.string.js_7_t), getString(R.string.js_7_d)));
                itemVoList.add(new ItemVo(R.drawable.js8, getString(R.string.js_8), getString(R.string.js_8_t), getString(R.string.js_8_d)));
                break;
            case 7:
                itemVoList.add(new ItemVo(R.drawable.r1, getString(R.string.r_1), getString(R.string.r_1_t), getString(R.string.r_1_d)));
                itemVoList.add(new ItemVo(R.drawable.r2, getString(R.string.r_2), getString(R.string.r_2_t), getString(R.string.r_2_d)));
                itemVoList.add(new ItemVo(R.drawable.r3, getString(R.string.r_3), getString(R.string.r_3_t), getString(R.string.r_3_d)));
                break;
            case 8:
                itemVoList.add(new ItemVo(R.drawable.g1, getString(R.string.g_1), getString(R.string.g_1_t), getString(R.string.g_1_d)));
                itemVoList.add(new ItemVo(R.drawable.g2, getString(R.string.g_2), getString(R.string.g_2_t), getString(R.string.g_2_d)));
                itemVoList.add(new ItemVo(R.drawable.g3, getString(R.string.g_3), getString(R.string.g_3_t), getString(R.string.g_3_d)));
                itemVoList.add(new ItemVo(R.drawable.g4, getString(R.string.g_4), getString(R.string.g_4_t), getString(R.string.g_4_d)));
                break;
            case 9:
                itemVoList.add(new ItemVo(R.drawable.p1, getString(R.string.p_1), getString(R.string.p_1_t), getString(R.string.p_1_d)));
                itemVoList.add(new ItemVo(R.drawable.p2, getString(R.string.p_2), getString(R.string.p_2_t), getString(R.string.p_2_d)));
                itemVoList.add(new ItemVo(R.drawable.p3, getString(R.string.p_3), getString(R.string.p_3_t), getString(R.string.p_3_d)));
                itemVoList.add(new ItemVo(R.drawable.p4, getString(R.string.p_4), getString(R.string.p_4_t), getString(R.string.p_4_d)));
                itemVoList.add(new ItemVo(R.drawable.p5, getString(R.string.p_5), getString(R.string.p_5_t), getString(R.string.p_5_d)));
                itemVoList.add(new ItemVo(R.drawable.p6, getString(R.string.p_6), getString(R.string.p_6_t), getString(R.string.p_6_d)));
                break;
            case 10:
                itemVoList.add(new ItemVo(R.drawable.ru1, getString(R.string.ru_1), getString(R.string.ru_1_t), getString(R.string.ru_1_d)));
                itemVoList.add(new ItemVo(R.drawable.ru2, getString(R.string.ru_2), getString(R.string.ru_2_t), getString(R.string.ru_2_d)));
                break;
            case 11:
                itemVoList.add(new ItemVo(R.drawable.sa1, getString(R.string.sa_1), getString(R.string.sa_1_t), getString(R.string.sa_1_d)));
                break;
            default:
                itemVoList.add(new ItemVo(R.drawable.py1, getString(R.string.py_1), getString(R.string.py_1_t), getString(R.string.py_1_d)));
                itemVoList.add(new ItemVo(R.drawable.py2, getString(R.string.py_2), getString(R.string.py_2_t), getString(R.string.py_2_d)));
                itemVoList.add(new ItemVo(R.drawable.py0, getString(R.string.py_3), getString(R.string.py_3_t), getString(R.string.py_3_d)));
                itemVoList.add(new ItemVo(R.drawable.py4, getString(R.string.py_4), getString(R.string.py_4_t), getString(R.string.py_4_d)));
                itemVoList.add(new ItemVo(R.drawable.py5, getString(R.string.py_5), getString(R.string.py_5_t), getString(R.string.py_5_d)));
                itemVoList.add(new ItemVo(R.drawable.py6, getString(R.string.py_6), getString(R.string.py_6_t), getString(R.string.py_6_d)));
                break;
        }



    }


    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int tag2 = getResources().getDimensionPixelOffset(R.dimen.rv_div_4);
            int tag3 = getResources().getDimensionPixelOffset(R.dimen.rv_div_3);
            outRect.set(tag2, tag2, tag2, tag3);
        }
    }
}