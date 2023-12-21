package com.pacepal.walk.ui;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pacepal.walk.R;
import com.pacepal.walk.data.dao.DaoManager;
import com.pacepal.walk.data.dao.EventInfoDao;
import com.pacepal.walk.data.vo.EventInfo;
import com.pacepal.walk.databinding.ActivityEventBinding;

import java.util.List;

public class EventActivity extends BaseActivity {


    private ActivityEventBinding eventBinding;
    private String currentDate;
    private EventItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBinding = ActivityEventBinding.inflate(getLayoutInflater());
        setContentView(eventBinding.getRoot());
        eventBinding.btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentDate = getIntent().getStringExtra("click_date");
        eventBinding.rvEvent.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        eventBinding.rvEvent.addItemDecoration(new EventDecoration());
        itemAdapter = new EventItemAdapter(EventActivity.this);
        List<EventInfo> eventInfo = getEventInfo(currentDate);
        itemAdapter.setData(eventInfo).setOnItemClickListener(pos -> {
            Intent intent = new Intent(EventActivity.this, ModifyEventActivity.class);
            intent.putExtra("event_event", eventInfo.get(pos).getEvent());
            intent.putExtra("event_id", String.valueOf(eventInfo.get(pos).getId()));
            intent.putExtra("event_time", eventInfo.get(pos).getDate());
            startActivity(intent);

        });
        eventBinding.rvEvent.setAdapter(itemAdapter);



    }

    private List<EventInfo> getEventInfo(String currentDate) {
        EventInfoDao eventInfoDao = DaoManager.getInstance().getDaoSession().getEventInfoDao();
        String likeDate = "%" + currentDate + "%";
        return eventInfoDao.queryBuilder().where(EventInfoDao.Properties.Date.like(likeDate)).list();
    }

    class EventDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int tag = getResources().getDimensionPixelOffset(R.dimen.dimen_1dp);
            int tag4 = getResources().getDimensionPixelOffset(R.dimen.dimen_3dp);
            outRect.set(tag, tag4, tag, tag);
        }

    }
}