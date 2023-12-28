package io.notehive.notes.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.XToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.notehive.notes.R;
import io.notehive.notes.data.dao.CategoryInfoDao;
import io.notehive.notes.data.dao.DaoManager;
import io.notehive.notes.data.dao.ScheduleInfoDao;
import io.notehive.notes.data.entity.CategoryInfo;
import io.notehive.notes.data.entity.ScheduleInfo;
import io.notehive.notes.databinding.FragmentHomeBinding;
import io.notehive.notes.ui.ScheduleUtil;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding homeBinding;
    private Context context;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String category;
    private int isCalendar;

    private ScheduleInfoDao scheduleInfoDao;
    private CategoryInfoDao categoryInfoDao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        return homeBinding.getRoot();
    }

    @SuppressLint({"DefaultLocale", "PrivateResource"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        scheduleInfoDao = DaoManager.getInstance().getNewSession().getScheduleInfoDao();
        categoryInfoDao = DaoManager.getInstance().getNewSession().getCategoryInfoDao();

        homeBinding.constraintLayoutHome.setOnClickListener(v -> hideKeyboard());


        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDate = calendar.get(Calendar.DAY_OF_MONTH);

        startDate = getFormatDate(new Date());

        homeBinding.tvStartDate.setText(startDate);
        startTime = getNowTime();
        homeBinding.tvStartTime.setText(startTime);
        endDate = getFormatDate(new Date());
        homeBinding.tvEndDate.setText(endDate);
        endTime = getNext2HourTime();
        homeBinding.tvEndTime.setText(endTime);


        homeBinding.tvStartDate.setOnClickListener(v -> {
            hideKeyboard();
            new DatePickerDialog(context, com.xuexiang.xui.R.style.XUITheme_AlertDialog, (view14, year, month, dayOfMonth) -> {
                startDate = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                homeBinding.tvStartDate.setText(startDate);
            }, mYear, mMonth, mDate).show();
        });


        homeBinding.tvStartTime.setOnClickListener(v -> {
            hideKeyboard();
            new TimePickerDialog(context, com.xuexiang.xui.R.style.XUITheme_AlertDialog, (view12, hourOfDay, minute) -> {

                startTime = hourOfDay + ":" + minute;
                homeBinding.tvStartTime.setText(startTime);
            }
                    , calendar.get(Calendar.HOUR_OF_DAY)
                    , calendar.get(Calendar.MINUTE)
                    , true)
                    .show();
        });


        homeBinding.tvEndDate.setOnClickListener(v -> {
            hideKeyboard();
            new DatePickerDialog(context, com.xuexiang.xui.R.style.XUITheme_AlertDialog, (view14, year, month, dayOfMonth) -> {
                hideKeyboard();
                endDate = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                homeBinding.tvEndDate.setText(endDate);
            }, mYear, mMonth, mDate).show();
        });


        homeBinding.tvEndTime.setOnClickListener(v -> {
            hideKeyboard();
            new TimePickerDialog(context, com.xuexiang.xui.R.style.XUITheme_AlertDialog, (view12, hourOfDay, minute) -> {

                endTime = hourOfDay + ":" + minute;
                homeBinding.tvEndTime.setText(endTime);
            }
                    , calendar.get(Calendar.HOUR_OF_DAY)
                    , calendar.get(Calendar.MINUTE)
                    , true)
                    .show();
        });


        List<AdapterItem> list = getAdapterItems();
        homeBinding.spinnerCategory.setItems(list);
        homeBinding.spinnerCategory.setSelectedItem(list.get(1));
        category = list.get(1).toString();
        homeBinding.spinnerCategory.setOnItemSelectedListener((view13, position, id, item) -> {
            hideKeyboard();
            category = list.get(position).toString();
        });


        homeBinding.cbCalendar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            hideKeyboard();
            if (isChecked) {
                isCalendar = 1;
            } else {
                isCalendar = 0;
            }
        });


        homeBinding.btnAdd.setOnClickListener(v -> {
            hideKeyboard();
            String title = homeBinding.etTitle.getContentText();
            String remark = homeBinding.etRemark.getContentText();
            if (isScheduleExist(title, startDate, startTime, endDate, endTime, category, isCalendar)) {
                XToastUtils.warning(R.string.add_fail);
            } else {
                if (isCalendar == 1) {
                    if (XXPermissions.isGranted(context, Permission.WRITE_CALENDAR, Permission.READ_CALENDAR)) {
                        String startDateTimeString = startDate + " " + startTime;
                        String endDateTimeString = endDate + " " + endTime;
                        ScheduleUtil.isExistSchedule(context, title, remark, startDateTimeString, endDateTimeString);
                        addData(title, startDate, startTime, endDate, endTime, category, isCalendar, remark);
                    } else {
                        getPermission();
                    }
                } else {
                    Log.d("Name@category", category + " ");
                    addData(title, startDate, startTime, endDate, endTime, category, isCalendar, remark);
                }
            }

        });
    }


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




    private List<AdapterItem> getAdapterItems() {
        List<AdapterItem> list = new ArrayList<>();
        List<CategoryInfo> CategoryInfoList = categoryInfoDao.queryBuilder().build().list();
        for (CategoryInfo categoryInfo : CategoryInfoList) {
            list.add(new AdapterItem(categoryInfo.getCategory()));
        }
        return list;
    }

    private void addData(String title, String startDate, String startTime, String endDate, String endTime, String category, int isCalendar, String remark) {


        if (TextUtils.isEmpty(title)) {
            XToastUtils.toast(R.string.message_input_title);
            return;
        }

        if (!isEndDateTimeValid(startDate, startTime, endDate, endTime)) {
            XToastUtils.toast(R.string.datetime_valid);
            return;
        }


        if (TextUtils.isEmpty(remark)) {
            remark = "";
        }


        ScheduleInfo categoryInfo = new ScheduleInfo();
        categoryInfo.setTitle(title);
        categoryInfo.setStartDate(startDate);
        categoryInfo.setStartTime(startTime);
        categoryInfo.setEndDate(endDate);
        categoryInfo.setEndTime(endTime);
        categoryInfo.setCategory(category);
        categoryInfo.setIsCalendar(isCalendar);
        categoryInfo.setRemark(remark);
        scheduleInfoDao.insert(categoryInfo);
        XToastUtils.success(R.string.add_success);
    }


    private boolean isCategoryExist(String category) {
        CategoryInfo unique = categoryInfoDao.queryBuilder().where(CategoryInfoDao.Properties.Category.eq(category)).build().unique();
        return unique != null;
    }


    private boolean isScheduleExist(String title, String startDate, String startTime, String endDate, String endTime, String category, int isCalendar) {
        ScheduleInfo unique = scheduleInfoDao.queryBuilder().where(
                ScheduleInfoDao.Properties.Title.eq(title),
                ScheduleInfoDao.Properties.StartDate.eq(startDate),
                ScheduleInfoDao.Properties.StartTime.eq(startTime),
                ScheduleInfoDao.Properties.EndDate.eq(endDate),
                ScheduleInfoDao.Properties.EndTime.eq(endTime),
                ScheduleInfoDao.Properties.Category.eq(category),
                ScheduleInfoDao.Properties.IsCalendar.eq(isCalendar)).build().unique();
        return unique != null;
    }

    private boolean isEndDateTimeValid(String startDateString, String startTimeString, String endDateString, String endTimeString) {

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        String startDateTimeString = startDateString + " " + startTimeString;
        String endDateTimeString = endDateString + " " + endTimeString;

        try {
            Date startDateTime = dateTimeFormat.parse(startDateTimeString);
            Date endDateTime = dateTimeFormat.parse(endDateTimeString);
            return !endDateTime.before(startDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }


    private String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }

    private String getNext2HourTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }

    private void hideKeyboard() {
        View view = getView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeBinding = null;
    }
}