package com.pacepal.walk.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pacepal.walk.R;
import com.pacepal.walk.data.vo.EventInfo;

import java.text.DecimalFormat;
import java.util.List;


public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.LinearViewHolder> {


    private List<EventInfo> itemVoList;
    private Context context;

    private OnItemClickListener clickListener;

    public EventItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {

        if (!TextUtils.isEmpty(itemVoList.get(position).getEvent())) {
            holder.tvEvent.setText(itemVoList.get(position).getEvent());
        }
        holder.tvStep.setText(String.valueOf(itemVoList.get(position).getSteps()));
        holder.tvKm.setText(countKm(itemVoList.get(position).getSteps()));
        holder.tvTime.setText(itemVoList.get(position).getDate());

        holder.constraintLayout.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

        holder.tvEvent.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

        holder.tvStep.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

        holder.tvKm.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

    }

    private String countKm(int steps) {
        double km = steps * 0.7;
        DecimalFormat df = new DecimalFormat("#.## km");
        return df.format(km / 1000);
    }

    @Override
    public int getItemCount() {
        return itemVoList.size();
    }

    static class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEvent, tvStep, tvKm, tvTime;
        private ConstraintLayout constraintLayout;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraint_event);
            tvStep = itemView.findViewById(R.id.item_tv_steps);
            tvEvent = itemView.findViewById(R.id.item_tv_event);
            tvKm = itemView.findViewById(R.id.item_tv_km);
            tvTime = itemView.findViewById(R.id.item_tv_time);
        }
    }


    public EventItemAdapter setData(List<EventInfo> itemVoList) {
        this.itemVoList = itemVoList;
        return this;
    }

    public EventItemAdapter setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }


    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
