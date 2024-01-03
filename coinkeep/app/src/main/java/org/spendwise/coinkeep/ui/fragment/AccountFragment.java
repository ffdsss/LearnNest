package org.spendwise.coinkeep.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;


import org.spendwise.coinkeep.R;
import org.spendwise.coinkeep.data.dao.AccountInfoDao;
import org.spendwise.coinkeep.data.dao.DaoManager;
import org.spendwise.coinkeep.data.entity.AccountInfo;
import org.spendwise.coinkeep.databinding.FragmentAccountBinding;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AccountFragment extends Fragment {


    public AccountFragment() {
        // Required empty public constructor
    }


    private FragmentAccountBinding accountBinding;
    private Context context;

    private String startDate;
    private String inCome;
    private String category;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accountBinding = FragmentAccountBinding.inflate(getLayoutInflater(), container, false);
        return accountBinding.getRoot();

    }

    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        AccountInfoDao accountInfoDao = DaoManager.getInstance().getNewSession().getAccountInfoDao();
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(accountBinding.edNum, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(10);
        accountBinding.edNum.setFilters(filters);
        accountBinding.edNum.setCursorVisible(false);

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDate = calendar.get(Calendar.DAY_OF_MONTH);
        startDate = getFormatDate(new Date());
        accountBinding.btnDate.setText(startDate);
        accountBinding.btnDate.setOnClickListener(v -> new DatePickerDialog(context, (view14, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            startDate = getFormatDate(selectedDate.getTime());
            accountBinding.btnDate.setText(startDate);
        }, mYear, mMonth, mDate).show());


        accountBinding.rg1.check(R.id.rb_income);
        inCome = getString(R.string.income);
        accountBinding.rg1.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_income:
                    inCome = getString(R.string.income);
                    break;
                default:
                    inCome = getString(R.string.expend);
                    break;
            }
        });
        accountBinding.rg2.check(R.id.rb_food);
        category = getString(R.string.food);
        accountBinding.rg2.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_food:
                    category = getString(R.string.food);
                    break;
                case R.id.rb_traffic:
                    category = getString(R.string.traffic);
                    break;
                case R.id.rb_game:
                    category = getString(R.string.game);
                    break;
                case R.id.rb_clothing:
                    category = getString(R.string.clothing);
                    break;
                case R.id.rb_travel:
                    category = getString(R.string.travel);
                    break;
                case R.id.rb_digital:
                    category = getString(R.string.digital);
                    break;
                default:
                    category = getString(R.string.educate);
                    break;
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0");
        accountBinding.edNum.setText(stringBuilder.toString());

        accountBinding.btnNumDel.setOnClickListener(v -> {
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            if (stringBuilder.toString().equals("")) {
                stringBuilder.append("0");
            }
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNumAc.setOnClickListener(v -> {
            stringBuilder.setLength(0);
            stringBuilder.append("0");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNum100.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("00");
            accountBinding.edNum.setText(stringBuilder.toString());
        });


        accountBinding.btnNum1000.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("000");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNumZero.setOnClickListener(v -> {
            if (!stringBuilder.toString().equals("0")) {
                stringBuilder.append("0");
                accountBinding.edNum.setText(stringBuilder.toString());
            }
        });

        accountBinding.btnNum1.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("1");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNum2.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("2");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNum3.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("3");
            accountBinding.edNum.setText(stringBuilder.toString());
        });


        accountBinding.btnNum4.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("4");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNum5.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("5");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNum6.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("6");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNum7.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("7");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNum8.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("8");
            accountBinding.edNum.setText(stringBuilder.toString());
        });


        accountBinding.btnNum9.setOnClickListener(v -> {
            if (stringBuilder.toString().equals("0")) {
                stringBuilder.setLength(0);
            }
            stringBuilder.append("9");
            accountBinding.edNum.setText(stringBuilder.toString());
        });

        accountBinding.btnNumDot.setOnClickListener(v -> {
            if (stringBuilder.length() > 0) {
                if (stringBuilder.indexOf(".") == -1) {
                    stringBuilder.append(".");
                }
                accountBinding.edNum.setText(stringBuilder.toString());
            }

        });

        accountBinding.btnNumOk.setOnClickListener(v -> {
            boolean s = stringBuilder.charAt(stringBuilder.length() - 1) == '.';
            boolean d = !stringBuilder.toString().equals("0");
            if (!s && d) {
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setMoney(stringBuilder.toString());
                accountInfo.setDate(startDate);
                accountInfo.setTime(getFormatTime(new Date()));
                accountInfo.setCategory(category);
                accountInfo.setRemark("");
                accountInfo.setIsIncome(inCome);
                accountInfoDao.insert(accountInfo);
                stringBuilder.setLength(0);
                stringBuilder.append("0");
                accountBinding.edNum.setText(stringBuilder.toString());
            }
        });


    }

    private String getFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String getFormatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        accountBinding = null;
    }
}