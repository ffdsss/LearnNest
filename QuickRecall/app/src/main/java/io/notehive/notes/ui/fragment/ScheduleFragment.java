package io.notehive.notes.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import io.notehive.notes.R;
import io.notehive.notes.data.dao.DaoManager;
import io.notehive.notes.data.dao.ScheduleInfoDao;
import io.notehive.notes.data.entity.ScheduleInfo;
import io.notehive.notes.databinding.FragmentScheduleBinding;
import io.notehive.notes.ui.DetailActivity;
import io.notehive.notes.ui.ScheduleAdapter;

public class ScheduleFragment extends Fragment {


    private FragmentScheduleBinding scheduleBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        scheduleBinding = FragmentScheduleBinding.inflate(getLayoutInflater(), container, false);
        return scheduleBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        Context context = getContext();
        ScheduleInfoDao scheduleInfoDao = DaoManager.getInstance().getNewSession().getScheduleInfoDao();

        scheduleBinding.rvSchedule.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        scheduleBinding.rvSchedule.addItemDecoration(new ScheduleDecoration());
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(context);
        List<ScheduleInfo> scheduleList = scheduleInfoDao.queryBuilder().build().list();
        scheduleAdapter.setData(scheduleList).setOnItemClickListener(pos -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("id", String.valueOf(scheduleList.get(pos).getId()));
            startActivity(intent);
        });
        scheduleBinding.rvSchedule.setAdapter(scheduleAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        scheduleBinding = null;
    }


    class ScheduleDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, getResources().getDimensionPixelOffset(R.dimen.rv_rect_4), getResources().getDimensionPixelOffset(R.dimen.rv_rect_1), 0);
        }

    }
}