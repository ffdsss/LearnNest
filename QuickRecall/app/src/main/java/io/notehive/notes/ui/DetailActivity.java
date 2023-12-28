package io.notehive.notes.ui;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import io.notehive.notes.R;
import io.notehive.notes.data.dao.CategoryInfoDao;
import io.notehive.notes.data.dao.DaoManager;
import io.notehive.notes.data.dao.ScheduleInfoDao;
import io.notehive.notes.data.entity.CategoryInfo;
import io.notehive.notes.data.entity.ScheduleInfo;
import io.notehive.notes.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {


    private ActivityDetailBinding detailBinding;

    private String id;

    private ScheduleInfoDao scheduleInfoDao;

    private CategoryInfoDao categoryInfoDao;

    private ScheduleInfo scheduleInfo;

    private String title;
    private String category;
    private String remark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(detailBinding.getRoot());
        id = getIntent().getStringExtra("id");
        scheduleInfoDao = DaoManager.getInstance().getNewSession().getScheduleInfoDao();
        categoryInfoDao = DaoManager.getInstance().getNewSession().getCategoryInfoDao();
        scheduleInfo = scheduleInfoDao.queryBuilder().where(ScheduleInfoDao.Properties.Id.eq(Long.valueOf(id))).build().unique();

        title = scheduleInfo.getTitle();
        category = scheduleInfo.getCategory();
        remark = scheduleInfo.getRemark();

        initKeyBoard();

        detailBinding.etTitle.setContentText(title);
        detailBinding.tvStartDate.setText(scheduleInfo.getStartDate());
        detailBinding.tvStartTime.setText(scheduleInfo.getStartTime());
        detailBinding.tvEndDate.setText(scheduleInfo.getEndDate());
        detailBinding.tvEndTime.setText(scheduleInfo.getEndTime());

        detailBinding.etRemark.setContentText(remark);

        List<AdapterItem> list = getAdapterItems();
        detailBinding.spinnerCategory.setItems(list);
        Log.d("Name@category1", category + "   " + getCategorySize(category));
        detailBinding.spinnerCategory.setSelectedItem(list.get(getCategorySize(category)));

//        newCategory = list.get(getCategorySize(category)).toString();
//        detailBinding.spinnerCategory.setOnItemSelectedListener((view, position, id, item) -> {
//            hideKeyboard();
//            newCategory =  list.get(position).toString();
//        });

        detailBinding.btnSave.setOnClickListener(v -> {
            hideKeyboard();
            String newTitle = detailBinding.etTitle.getContentText();
            String newRemark = detailBinding.etRemark.getContentText();
            String newCategory = detailBinding.spinnerCategory.getSelectedItem().toString();

            if (newTitle.equals(title) && newRemark.equals(remark) && newCategory.equals(category)) {
                XToastUtils.info(getString(R.string.no_change));
            } else {
                updateSchedule(newTitle, newRemark, newCategory);
                XToastUtils.success(getString(R.string.success_save));
                finish();
            }
        });


        detailBinding.btnDel.setOnClickListener(v -> {
            ScheduleInfo unique = scheduleInfoDao.queryBuilder().where(ScheduleInfoDao.Properties.Id.eq(id)).build().unique();
            scheduleInfoDao.delete(unique);
            finish();
        });

    }


    private void updateSchedule(String title, String remark, String category) {
        ScheduleInfo unique = scheduleInfoDao.queryBuilder().where(ScheduleInfoDao.Properties.Id.eq(id)).build().unique();
        unique.setTitle(title);
        unique.setRemark(remark);
        unique.setCategory(category);
        scheduleInfoDao.update(unique);
    }


    private void initKeyBoard() {
        detailBinding.constraintLayoutDetail.setOnClickListener(v -> hideKeyboard());
        detailBinding.tvStartDate.setOnClickListener(v -> hideKeyboard());
        detailBinding.tvStartTime.setOnClickListener(v -> hideKeyboard());
        detailBinding.tvEndDate.setOnClickListener(v -> hideKeyboard());
        detailBinding.tvEndTime.setOnClickListener(v -> hideKeyboard());
    }


    private int getCategorySize(String category) {

        List<CategoryInfo> list = categoryInfoDao.queryBuilder().build().list();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCategory().equals(category)) {
                return i;
            }
        }
        return 0;
    }


    private List<AdapterItem> getAdapterItems() {
        List<AdapterItem> list = new ArrayList<>();
        List<CategoryInfo> CategoryInfoList = categoryInfoDao.queryBuilder().build().list();
        for (CategoryInfo categoryInfo : CategoryInfoList) {
            list.add(new AdapterItem(categoryInfo.getCategory()));
        }
        return list;
    }


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}