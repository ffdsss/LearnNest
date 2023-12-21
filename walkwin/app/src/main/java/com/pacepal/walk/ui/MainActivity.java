package com.pacepal.walk.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.pacepal.walk.R;
import com.pacepal.walk.data.dao.DaoManager;
import com.pacepal.walk.data.dao.EventInfoDao;
import com.pacepal.walk.data.dao.StepInfoDao;
import com.pacepal.walk.data.vo.EventInfo;
import com.pacepal.walk.data.vo.StepInfo;
import com.pacepal.walk.databinding.ActivityMainBinding;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding mainBinding;
    private boolean isGo = false;
    private SensorManager sensorManager;

    private int count;

    private String clickDate;
    private int clickEventNum;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        initCalendarView();

        mainBinding.btnGo.setOnClickListener(v -> startStep());
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        initTotalStepListener();

        mainBinding.tvEvents.setOnClickListener(v -> {
            if (clickEventNum > 0) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                intent.putExtra("click_date", clickDate);
                startActivity(intent);
            } else {
                XToastUtils.info(getString(R.string.no_event));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String calendarDate = String.format("%d/%d/%d",  mainBinding.calendarView.getCurYear(),  mainBinding.calendarView.getCurMonth(),  mainBinding.calendarView.getCurDay());
        mainBinding.tvDate.setText(calendarDate);
        initTotalAndEvent(calendarDate);
    }

    private String countKm(int steps) {
        double km = steps * 0.7;
        DecimalFormat df = new DecimalFormat("#.## km");
        return df.format(km / 1000);
    }

    private String getFormatDate(Date date, boolean isTime) {
        SimpleDateFormat dateFormat;
        if (!isTime) {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        } else {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        }
        return dateFormat.format(date);

    }


    private void initTotalAndEvent(String currentDate) {
        clickDate = currentDate;
        Log.d("Name@currentDate", currentDate + " ");
        StepInfo currentStepInfo = checkDate(currentDate);
        if (currentStepInfo != null) {
            mainBinding.tvKm.setText(countKm(currentStepInfo.getSteps()));
            mainBinding.tvSteps.setText(String.valueOf(currentStepInfo.getSteps()));
        } else {
            mainBinding.tvKm.setText(countKm(0));
            mainBinding.tvSteps.setText(String.valueOf(0));
        }
        EventInfoDao eventInfoDao = DaoManager.getInstance().getDaoSession().getEventInfoDao();
        String likeDate = "%" + currentDate + "%";
        List<EventInfo> list = eventInfoDao.queryBuilder().where(EventInfoDao.Properties.Date.like(likeDate)).list();
        clickEventNum = list.size();
        mainBinding.tvEvents.setText(String.valueOf(list.size()));
    }

    private void updateTotal(int stepCount) {
        String currentDate = getFormatDate(new Date(), false);

        StepInfoDao stepInfoDao = DaoManager.getInstance().getNewSession().getStepInfoDao();
        StepInfo currentStepInfo = checkDate(currentDate);

        // 上一日的步数
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.DAY_OF_YEAR, -1);
        String lastDay = getFormatDate(calendar.getTime(), false);
        int currentSteps = stepCount - getDateSteps(lastDay);

        if (currentStepInfo != null) {// update
            currentStepInfo.setSteps(currentSteps);
            stepInfoDao.update(currentStepInfo);
        } else { //add
            StepInfo stepInfo = new StepInfo();
            stepInfo.setDate(currentDate);
            if (currentSteps < 0) {
                stepInfo.setSteps(stepCount);
            } else {
                stepInfo.setSteps(currentSteps);
            }
            stepInfoDao.insert(stepInfo);
        }
        mainBinding.tvKm.setText(countKm(currentSteps));
        mainBinding.tvSteps.setText(String.valueOf(currentSteps));

    }


    private int getDateSteps(String date) {
        StepInfoDao stepInfoDao = DaoManager.getInstance().getNewSession().getStepInfoDao();
        StepInfo unique = stepInfoDao.queryBuilder().where(StepInfoDao.Properties.Date.eq(date)).build().unique();
        if (unique != null) {
            return unique.getSteps();
        } else {
            return 0;
        }
    }

    private StepInfo checkDate(String date) {
        StepInfoDao stepInfoDao = DaoManager.getInstance().getNewSession().getStepInfoDao();
        return stepInfoDao.queryBuilder().where(StepInfoDao.Properties.Date.eq(date)).build().unique();
    }

    private void initTotalStepListener() {
        Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        SensorEventListener stepListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                    int stepCount = (int) event.values[0];
                    updateTotal(stepCount);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepListener, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initCurrentStepListener() {
        Sensor stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        SensorEventListener stepListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                    count = count + (int) event.values[0];
                    mainBinding.tvCurrent.setText(String.valueOf(count));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepListener, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void startStep() {
        if (isGo) {
            new MaterialDialog.Builder(MainActivity.this)
                    .content(getString(R.string.input_event))
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .inputRange(3, 30)
                    .input(getString(R.string.enter_event), "", false, (dialog, input) -> {

                    })
                    .positiveText(getString(R.string.confirm))
                    .negativeText(getString(R.string.cancel_m))
                    .cancelable(false)
                    .onPositive((dialog, which) -> {
                        EventInfo eventInfo = new EventInfo();
                        eventInfo.setEvent(dialog.getInputEditText().getText().toString());
                        eventInfo.setSteps(count);
                        eventInfo.setDate(getFormatDate(new Date(), true));
                        insertEvent(eventInfo);
                        count = 0;
                        isGo = false;
                        mainBinding.btnGo.setText(getString(R.string.go));
                        mainBinding.tvCurrent.setText(String.valueOf(count));
                        initTotalAndEvent(getFormatDate(new Date(), false));
                    }).show();
        } else {
            if (XXPermissions.isGranted(MainActivity.this, Permission.ACTIVITY_RECOGNITION)) {
                isGo = true;
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long secondsRemaining = millisUntilFinished / 1000;
                        mainBinding.btnGo.setText(String.valueOf(secondsRemaining + 1));

                    }

                    @Override
                    public void onFinish() {
                        mainBinding.btnGo.setText(getString(R.string.stop));
                        initCurrentStepListener();
                    }
                }.start();
            } else {
                initPermission();
            }
        }
    }

    private void insertEvent(EventInfo eventInfo) {
        EventInfoDao eventInfoDao = DaoManager.getInstance().getNewSession().getEventInfoDao();
        eventInfoDao.insert(eventInfo);
    }


    private void initCalendarView() {
        mainBinding.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                String calendarDate = String.format("%d/%d/%d", calendar.getYear(), calendar.getMonth(), calendar.getDay());
                mainBinding.tvDate.setText(calendarDate);
                initTotalAndEvent(calendarDate);
                if (isClick) {
                    if (mainBinding.calendarLayout.isExpand()) {
                        mainBinding.calendarLayout.shrink();
                    }
                }
            }
        });

    }

    private void initPermission() {

        XXPermissions.with(MainActivity.this)
                .permission(Permission.ACTIVITY_RECOGNITION).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        if (!allGranted) {
                            Toast.makeText(MainActivity.this, getString(R.string.per_fail), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(MainActivity.this, getString(R.string.per_succ), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        if (doNotAskAgain) {
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.per_fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}