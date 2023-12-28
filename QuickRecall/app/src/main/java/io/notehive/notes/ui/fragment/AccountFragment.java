package io.notehive.notes.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;

import java.util.List;

import io.notehive.notes.R;
import io.notehive.notes.data.dao.CategoryInfoDao;
import io.notehive.notes.data.dao.DaoManager;
import io.notehive.notes.data.dao.ScheduleInfoDao;
import io.notehive.notes.data.entity.CategoryInfo;
import io.notehive.notes.data.entity.ScheduleInfo;
import io.notehive.notes.databinding.FragmentAccountBinding;
import io.notehive.notes.ui.CategoryAdapter;
import io.notehive.notes.ui.ScheduleUtil;

public class AccountFragment extends Fragment {



    private FragmentAccountBinding accountBinding;
    private CategoryInfoDao categoryInfoDao;
    private CategoryAdapter categoryAdapter;
    private ScheduleInfoDao scheduleInfoDao;

    private Context context;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        accountBinding = FragmentAccountBinding.inflate(getLayoutInflater(), container, false);
        return accountBinding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        categoryInfoDao = DaoManager.getInstance().getNewSession().getCategoryInfoDao();
        scheduleInfoDao = DaoManager.getInstance().getNewSession().getScheduleInfoDao();
        categoryAdapter = new CategoryAdapter(getContext());
        accountBinding.rvCategory.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        accountBinding.rvCategory.addItemDecoration(new CategoryDecoration());
        categoryAdapter.setData(getCategoryInfoList());
        accountBinding.rvCategory.setAdapter(categoryAdapter);

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(10);
        accountBinding.etCategory.setFilters(filters);

        accountBinding.btnAdd.setOnClickListener(v -> {
            String newCategory = accountBinding.etCategory.getText().toString();
            if (!TextUtils.isEmpty(newCategory)) {
                if (!isCategoryExist(newCategory)) {
                    if (getCategoryInfoList().size() > 9) {
                        XToastUtils.warning(R.string.category_max);
                    } else {
                        CategoryInfo categoryInfo = new CategoryInfo();
                        categoryInfo.setCategory(newCategory);
                        categoryInfoDao.insert(categoryInfo);
                        categoryAdapter.setData(getCategoryInfoList());
                        categoryAdapter.notifyDataSetChanged();
                    }

                }
            }
        });

        categoryAdapter.setOnItemClickListener(pos -> {
            categoryInfoDao.delete(getCategoryInfoList().get(pos));
            categoryAdapter.setData(getCategoryInfoList());
            categoryAdapter.notifyDataSetChanged();
        });



        accountBinding.tvClearData.setOnClickListener(v -> DialogLoader.getInstance().showConfirmDialog(
                getContext(),
                getString(R.string.delete_all),
                getString(R.string.yes),
                (dialog, which) -> {
                    dialog.dismiss();
                    deleteAll();
                },
                getString(R.string.no),
                (dialog, which) -> dialog.dismiss()
        ));


        accountBinding.tvAboutUs.setOnClickListener(v -> CookieBar.builder(getActivity())
                .setTitle(R.string.features)
                .setMessage(R.string.about_message)
                .setLayoutGravity(Gravity.BOTTOM)
                .setAction(R.string.ok, v1 -> {

                })
                .show());
    }


    private void deleteAll() {


        if (XXPermissions.isGranted(context, Permission.WRITE_CALENDAR, Permission.READ_CALENDAR)) {
            MiniLoadingDialog mMiniLoadingDialog = WidgetUtils.getMiniLoadingDialog(context);
            mMiniLoadingDialog.updateMessage(R.string.clearing);
            mMiniLoadingDialog.show();
            List<ScheduleInfo> scheduleInfoList = scheduleInfoDao.queryBuilder().build().list();
            for (ScheduleInfo scheduleInfo : scheduleInfoList) {
                ScheduleUtil.deleteSchedule(context, scheduleInfo.getTitle());
            }
            scheduleInfoDao.deleteAll();

            Handler handler = new Handler(Looper.getMainLooper());

            handler.postDelayed(() -> {
                if (mMiniLoadingDialog.isShowing()) {
                    mMiniLoadingDialog.dismiss();
                    mMiniLoadingDialog.recycle();
                }
            }, 1500);
        } else {
            getPermission();
        }

    }

    private List<CategoryInfo> getCategoryInfoList() {
        return categoryInfoDao.queryBuilder().build().list();
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        categoryAdapter.setData(getCategoryInfoList());
//    }

    private void getPermission() {

        XXPermissions.with(context)
                .permission(Permission.WRITE_CALENDAR, Permission.READ_CALENDAR).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        if (!allGranted) {
                            XToastUtils.warning(R.string.permission_failed);
                            return;
                        }
                        XToastUtils.success(R.string.permission_success);
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        if (doNotAskAgain) {
                            XXPermissions.startPermissionActivity(context, permissions);
                        } else {
                            XToastUtils.warning(R.string.permission_failed);
                        }
                    }
                });

    }


    private boolean isCategoryExist(String category) {
        CategoryInfo unique = categoryInfoDao.queryBuilder().where(CategoryInfoDao.Properties.Category.eq(category)).build().unique();
        return unique != null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        accountBinding = null;
    }


    class CategoryDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(getResources().getDimensionPixelOffset(R.dimen.rv_rect_3), getResources().getDimensionPixelOffset(R.dimen.rv_rect_2), getResources().getDimensionPixelOffset(R.dimen.rv_rect_3), 0);
        }

    }
}