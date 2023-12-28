package io.notehive.notes.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import io.notehive.notes.R;
import io.notehive.notes.data.entity.ScheduleInfo;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.LinearViewHolder> {


    private List<ScheduleInfo> itemVoList;
    private final Context context;

    private OnItemClickListener clickListener;

    public ScheduleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.schedule_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {

        if (!TextUtils.isEmpty(itemVoList.get(position).getTitle())) {
            holder.tvTitle.setText(itemVoList.get(position).getTitle());
        }

        if (!TextUtils.isEmpty(itemVoList.get(position).getStartDate())) {
            holder.tvDate.setText(itemVoList.get(position).getStartDate());
        }

        holder.constraintLayout.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });


        holder.tvTitle.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

        holder.tvDate.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemVoList.size();
    }

    static class LinearViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvDate;
        private final ConstraintLayout constraintLayout;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraint_event);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }


    public ScheduleAdapter setData(List<ScheduleInfo> itemVoList) {
        this.itemVoList = itemVoList;
        return this;
    }

    public ScheduleAdapter setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }


    public interface OnItemClickListener {
        void onClick(int pos);
    }
}
