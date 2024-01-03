package org.spendwise.coinkeep.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import org.spendwise.coinkeep.R;
import org.spendwise.coinkeep.data.entity.AccountInfo;


import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.LinearViewHolder> {


    private List<AccountInfo> itemVoList;
    private final Context context;

    private OnItemClickListener clickListener;

    public DetailsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {


        if (!TextUtils.isEmpty(itemVoList.get(position).getCategory())) {
            holder.tvCategory.setText(itemVoList.get(position).getCategory());
        }

        if (!TextUtils.isEmpty(itemVoList.get(position).getMoney())) {
            if (itemVoList.get(position).getIsIncome().equals(context.getString(R.string.income))) {
                holder.tvMoney.setText(String.format("+ %s", itemVoList.get(position).getMoney()));
            } else {
                holder.tvMoney.setText(String.format("- %s", itemVoList.get(position).getMoney()));
            }
        }

        if (!TextUtils.isEmpty(itemVoList.get(position).getTime())) {
            holder.tvTime.setText(itemVoList.get(position).getTime());
        }

        final String category = itemVoList.get(position).getCategory();

        if (category.equals(context.getString(R.string.food))) {
            Glide.with(context).load(R.drawable.food64).into(holder.imIcon);
        } else if (category.equals(context.getString(R.string.travel))) {
            Glide.with(context).load(R.drawable.travel64).into(holder.imIcon);
        } else if (category.equals(context.getString(R.string.traffic))) {
            Glide.with(context).load(R.drawable.traffic64).into(holder.imIcon);
        } else if (category.equals(context.getString(R.string.game))) {
            Glide.with(context).load(R.drawable.game64).into(holder.imIcon);
        } else if (category.equals(context.getString(R.string.clothing))) {
            Glide.with(context).load(R.drawable.clothing64).into(holder.imIcon);
        } else if (category.equals(context.getString(R.string.digital))) {
            Glide.with(context).load(R.drawable.digital64).into(holder.imIcon);
        } else if (category.equals(context.getString(R.string.educate))) {
            Glide.with(context).load(R.drawable.educate64).into(holder.imIcon);
        }


        holder.constraintLayout.setOnClickListener(v -> new XUISimplePopup(context, new String[]{"delete"}).create(dip2px(context, 0), (adapter, item, position1) -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        }).setPositionOffsetYWhenBottom(-100).showDown(holder.tvTime));


        holder.tvMoney.setOnClickListener(v -> new XUISimplePopup(context, new String[]{"delete"}).create(dip2px(context, 0), (adapter, item, position1) -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        }).setPositionOffsetYWhenBottom(-100).showDown(holder.tvTime));

        holder.tvTime.setOnClickListener(v -> new XUISimplePopup(context, new String[]{"delete"}).create(dip2px(context, 0), (adapter, item, position1) -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        }).setPositionOffsetYWhenBottom(-100).showDown(holder.tvTime));

        holder.imIcon.setOnClickListener(v -> new XUISimplePopup(context, new String[]{"delete"}).create(dip2px(context, 0), (adapter, item, position1) -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        }).setPositionOffsetYWhenBottom(-100).showDown(holder.tvTime));

        holder.tvCategory.setOnClickListener(v -> new XUISimplePopup(context, new String[]{"delete"}).create(dip2px(context, 0), (adapter, item, position1) -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        }).setPositionOffsetYWhenBottom(-100).showDown(holder.tvTime));

    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return itemVoList.size();
    }

    static class LinearViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvMoney;
        private final TextView tvCategory;
        private final TextView tvTime;
        private final ImageView imIcon;
        private final ConstraintLayout constraintLayout;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraint_event);
            tvMoney = itemView.findViewById(R.id.tv_money);
            tvCategory = itemView.findViewById(R.id.tv_m_category);
            tvTime = itemView.findViewById(R.id.tv_m_time);
            imIcon = itemView.findViewById(R.id.im_m_icon);
        }
    }


    public DetailsAdapter setData(List<AccountInfo> itemVoList) {
        this.itemVoList = itemVoList;
        return this;
    }

    public DetailsAdapter setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }


    public interface OnItemClickListener {
        void onClick(int pos);
    }
}
