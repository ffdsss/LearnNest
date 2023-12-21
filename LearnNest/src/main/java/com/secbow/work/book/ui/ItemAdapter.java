package com.secbow.work.book.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.secbow.work.book.R;
import com.secbow.work.book.vo.ItemVo;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.LinearViewHolder> {


    private List<ItemVo> itemVoList;
    private Context context;

    private OnItemClickListener clickListener;

    public ItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.LinearViewHolder holder, int position) {


        Glide.with(context).load(itemVoList.get(position).getRes()).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });
        holder.tvTitle.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });
        holder.tvDesc.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });


        if (!TextUtils.isEmpty(itemVoList.get(position).getTitle())) {
            holder.tvTitle.setText(itemVoList.get(position).getTitle());
        }
        if (!TextUtils.isEmpty(itemVoList.get(position).getDescribe())) {
            holder.tvDesc.setText(itemVoList.get(position).getDescribe());
        }

    }

    @Override
    public int getItemCount() {
        return itemVoList.size();
    }

    static class LinearViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tvTitle, tvDesc;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
            tvTitle = itemView.findViewById(R.id.item_name);
            tvDesc = itemView.findViewById(R.id.item_desc);

        }
    }


    public ItemAdapter setData(List<ItemVo> itemVoList) {
        this.itemVoList = itemVoList;
        return this;
    }

    public ItemAdapter setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }


    public interface OnItemClickListener{
        void onClick(int pos);
    }

}
