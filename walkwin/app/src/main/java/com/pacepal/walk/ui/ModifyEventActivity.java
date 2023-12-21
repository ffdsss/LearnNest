package com.pacepal.walk.ui;

import android.os.Bundle;
import android.view.View;

import com.pacepal.walk.R;
import com.pacepal.walk.data.dao.DaoManager;
import com.pacepal.walk.data.dao.EventInfoDao;
import com.pacepal.walk.data.vo.EventInfo;
import com.pacepal.walk.databinding.ActivityModifyEventBinding;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class ModifyEventActivity extends BaseActivity {

    private ActivityModifyEventBinding modifyEventBinding;

    private String eventId;
    private String event;
    private String eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modifyEventBinding = ActivityModifyEventBinding.inflate(getLayoutInflater());
        setContentView(modifyEventBinding.getRoot());
        modifyEventBinding.btnBack.setOnClickListener(v -> finish());

        event = getIntent().getStringExtra("event_event");
        eventId = getIntent().getStringExtra("event_id");
        eventTime = getIntent().getStringExtra("event_time");

        modifyEventBinding.etEvent.setContentText(event);

        modifyEventBinding.btnDelete.setOnClickListener(v -> new MaterialDialog.Builder(ModifyEventActivity.this)
                .content(getString(R.string.delete_event))
                .positiveText(getString(R.string.yes))
                .negativeText(getString(R.string.no))
                .cancelable(false)
                .onPositive((dialog, which) -> {
                    deleteEvent();
                    finish();
                })
                .show());

        modifyEventBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEvent = modifyEventBinding.etEvent.getContentText();
                if (newEvent.equals(event)) {
                    XToastUtils.info(getString(R.string.not_modify));
                } else {
                    updateEvent(newEvent);
                    finish();
                }
            }


        });


    }

    private void deleteEvent() {
        EventInfoDao eventInfoDao = DaoManager.getInstance().getNewSession().getEventInfoDao();
        EventInfo unique = eventInfoDao.queryBuilder().where(
                EventInfoDao.Properties.Id.eq(Long.valueOf(eventId)),
                EventInfoDao.Properties.Date.eq(eventTime)).build().unique();
        eventInfoDao.delete(unique);
    }

    private void updateEvent(String newEvent) {
        EventInfoDao eventInfoDao = DaoManager.getInstance().getNewSession().getEventInfoDao();
        EventInfo unique = eventInfoDao.queryBuilder().where(
                EventInfoDao.Properties.Id.eq(Long.valueOf(eventId)),
                EventInfoDao.Properties.Date.eq(eventTime)
                ).build().unique();
        unique.setEvent(newEvent);
        eventInfoDao.update(unique);
        XToastUtils.success(getString(R.string.success_save));
    }

}