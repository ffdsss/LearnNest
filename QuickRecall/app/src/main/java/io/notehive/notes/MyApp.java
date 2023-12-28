package io.notehive.notes;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;

import io.notehive.notes.data.dao.CategoryInfoDao;
import io.notehive.notes.data.dao.DaoManager;
import io.notehive.notes.data.entity.CategoryInfo;
import io.notehive.notes.ui.DetailActivity;

public class MyApp extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    private CategoryInfoDao categoryInfoDao;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DaoManager.getInstance();
        categoryInfoDao = DaoManager.getInstance().getNewSession().getCategoryInfoDao();
        String[] array = ResUtils.getStringArray(context, R.array.category_mode_entry);
        for (String s : array) {
            if (!isCategoryExist(s)) {
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.setCategory(s);
                categoryInfoDao.insert(categoryInfo);
            }
        }

    }

    private boolean isCategoryExist(String category) {
        CategoryInfo unique = categoryInfoDao.queryBuilder().where(CategoryInfoDao.Properties.Category.eq(category)).build().unique();
        return unique != null;
    }


}