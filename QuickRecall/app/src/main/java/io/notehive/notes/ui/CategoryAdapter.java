package io.notehive.notes.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.List;
import io.notehive.notes.R;
import io.notehive.notes.data.entity.CategoryInfo;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.LinearViewHolder> {


    private List<CategoryInfo> itemVoList;
    private final Context context;

    private OnItemClickListener clickListener;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {


        holder.buttonView.setText(itemVoList.get(position).getCategory());


        holder.buttonView.setOnClickListener(v -> new XUISimplePopup(context, new String[]{"delete"}).create(dip2px(context, 0), (adapter, item, position1) -> {
            if (clickListener != null) {
                clickListener.onClick(position);
            }
        }).showDown(holder.buttonView));


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

        private final ButtonView buttonView;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonView = itemView.findViewById(R.id.btn_category_del);
        }
    }


    public CategoryAdapter setData(List<CategoryInfo> itemVoList) {
        this.itemVoList = itemVoList;
        return this;
    }

    public CategoryAdapter setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }


    public interface OnItemClickListener {
        void onClick(int pos);
    }
}
