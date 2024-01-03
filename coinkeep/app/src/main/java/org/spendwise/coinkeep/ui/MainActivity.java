package org.spendwise.coinkeep.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.spendwise.coinkeep.R;
import org.spendwise.coinkeep.databinding.ActivityMainBinding;
import org.spendwise.coinkeep.ui.fragment.AccountFragment;
import org.spendwise.coinkeep.ui.fragment.DetailsFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org.spendwise.coinkeep.databinding.ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.viewpager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return new AccountFragment();
                } else {
                    return new DetailsFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });


        int[] tabIcons = {
                R.drawable.account,
                R.drawable.details};

        int[] tabTitles = {
                R.string.accounting,
                R.string.details
        };

       new TabLayoutMediator(mainBinding.tabLayout, mainBinding.viewpager2, (tab, position) -> {
           tab.setCustomView(R.layout.layout_tab_item);



           ImageView tabIcon = tab.getCustomView().findViewById(R.id.tab_im_icon);
           TextView tabText = tab.getCustomView().findViewById(R.id.tab_tv_text);

           if (position == 0) {
               tabIcon.setImageResource(R.drawable.account2_red);
           } else {
               tabIcon.setImageResource(R.drawable.details2);
           }

           //tabIcon.setImageResource(tabIcons[position]);
           tabText.setText(tabTitles[position]);

           if (position == 0) {
               tab.isSelected();
           }

       }).attach();


       mainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               setTabState(tab, true);
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {
               setTabState(tab, false);
           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
    }


    private void setTabState(TabLayout.Tab tab, boolean isSelected) {
        View customView = tab.getCustomView();
        if (customView != null) {
            ImageView tabIcon = customView.findViewById(R.id.tab_im_icon);
            int position = tab.getPosition();
            if (isSelected) {
                if (position == 0) {
                    tabIcon.setImageResource(R.drawable.account2_red);
                } else {
                    tabIcon.setImageResource(R.drawable.details2_red);
                }
            } else {
                if (position == 0) {
                    tabIcon.setImageResource(R.drawable.account2);
                } else {
                    tabIcon.setImageResource(R.drawable.details2);
                }
            }
        }
    }

}