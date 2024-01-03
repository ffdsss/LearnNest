package org.spendwise.coinkeep.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import org.spendwise.coinkeep.R;
import org.spendwise.coinkeep.data.dao.AccountInfoDao;
import org.spendwise.coinkeep.data.dao.DaoManager;
import org.spendwise.coinkeep.data.entity.AccountInfo;
import org.spendwise.coinkeep.databinding.FragmentDetailsBinding;
import org.spendwise.coinkeep.ui.DetailsAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DetailsFragment extends Fragment {


    private FragmentDetailsBinding detailsBinding;
    private Context context;
    private String nowDate;
    private double income;
    private double expend;
    private AccountInfoDao accountInfoDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailsBinding = FragmentDetailsBinding.inflate(getLayoutInflater(), container, false);
        return detailsBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        accountInfoDao = DaoManager.getInstance().getNewSession().getAccountInfoDao();

    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
    @Override
    public void onResume() {
        super.onResume();

        income = 0;
        expend = 0;


        detailsBinding.rvDetails.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        detailsBinding.rvDetails.addItemDecoration(new DetailsDecoration());
        DetailsAdapter detailsAdapter = new DetailsAdapter(context);
        nowDate = getFormatDate(new Date());
        List<AccountInfo> accountInfoList = getAccountInfoList();

        for (AccountInfo accountInfo : accountInfoList) {
            if (accountInfo.getIsIncome().equals(context.getString(R.string.income))) {
                income = income + Double.parseDouble(accountInfo.getMoney());
            } else {
                expend = expend + Double.parseDouble(accountInfo.getMoney());
            }
        }



        detailsBinding.tvShowIncome.setText(String.format("income: %s  expend: %s", income, expend));


        detailsAdapter.setData(accountInfoList).setOnItemClickListener(pos -> {
            accountInfoDao.delete(getAccountInfoList().get(pos));
            List<AccountInfo> newAccountInfoList = getAccountInfoList();
            income = 0;
            expend = 0;
            for (AccountInfo accountInfo : newAccountInfoList) {
                if (accountInfo.getIsIncome().equals(context.getString(R.string.income))) {
                    income = income + Double.parseDouble(accountInfo.getMoney());
                } else {
                    expend = expend + Double.parseDouble(accountInfo.getMoney());
                }
            }
            detailsBinding.tvShowIncome.setText(String.format("income: %s  expend: %s", income, expend));
            detailsAdapter.setData(newAccountInfoList);
            detailsAdapter.notifyDataSetChanged();

        });
        detailsBinding.rvDetails.setAdapter(detailsAdapter);

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDate = calendar.get(Calendar.DAY_OF_MONTH);
        detailsBinding.btnDate.setText(nowDate);
        detailsBinding.btnDate.setOnClickListener(v -> new DatePickerDialog(context, (view14, year, month, dayOfMonth) -> {
            income = 0;
            expend = 0;
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            nowDate = getFormatDate(selectedDate.getTime());
            detailsBinding.btnDate.setText(nowDate);
            List<AccountInfo> newAccountInfoList = getAccountInfoList();
            for (AccountInfo accountInfo : newAccountInfoList) {
                if (accountInfo.getIsIncome().equals(context.getString(R.string.income))) {
                    income = income + Double.parseDouble(accountInfo.getMoney());
                } else {
                    expend = expend + Double.parseDouble(accountInfo.getMoney());
                }
            }
            detailsBinding.tvShowIncome.setText(String.format("income: %s  expend: %s", income, expend));
            detailsAdapter.setData(newAccountInfoList);
            detailsAdapter.notifyDataSetChanged();

        }, mYear, mMonth, mDate).show());
    }


    private List<AccountInfo> getAccountInfoList() {
        return accountInfoDao.queryBuilder().where(AccountInfoDao.Properties.Date.eq(nowDate)).build().list();
    }


    private String getFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        detailsBinding = null;
    }

    class DetailsDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, getResources().getDimensionPixelOffset(R.dimen.rv_2), 0, 0);
        }
    }

}