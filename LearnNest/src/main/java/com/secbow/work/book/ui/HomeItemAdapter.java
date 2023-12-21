package com.secbow.work.book.ui;

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
import com.secbow.work.book.R;
import com.secbow.work.book.vo.HomeVo;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.LinearViewHolder> {


    private List<HomeVo> itemVoList;
    private Context context;

    private HomeItemAdapter.OnItemClickListener clickListener;

    public HomeItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeItemAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeItemAdapter.LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemAdapter.LinearViewHolder holder, int position) {


        Glide.with(context).load(itemVoList.get(position).getDraw()).into(holder.imageView);

        if (!TextUtils.isEmpty(itemVoList.get(position).getName())) {
            holder.tvName.setText(itemVoList.get(position).getName());
        }

        holder.constraintLayout.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

        holder.tvName.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        });

        holder.imageView.setOnClickListener(v -> {
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

        private ImageView imageView;
        private TextView tvName;
        private ConstraintLayout constraintLayout;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_item_img);
            tvName = itemView.findViewById(R.id.home_item_name);
            constraintLayout = itemView.findViewById(R.id.home_cons);
        }
    }


    public HomeItemAdapter setData(List<HomeVo> itemVoList) {
        this.itemVoList = itemVoList;
        return this;
    }

    public HomeItemAdapter setOnItemClickListener(HomeItemAdapter.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }


    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
