package io.notehive.notes.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import io.notehive.notes.R;
import io.notehive.notes.databinding.ActivityFragmentBinding;

public class FragmentActivity extends BaseActivity {

    private ActivityFragmentBinding fragmentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentBinding = ActivityFragmentBinding.inflate(getLayoutInflater());
        setContentView(fragmentBinding.getRoot());
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onStart() {
        super.onStart();
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView3);

        fragmentBinding.includeHome.homeLayout.setOnClickListener(v -> navController.navigate(R.id.menu_home));
        fragmentBinding.includeSchedule.scheduleLayout.setOnClickListener(v -> navController.navigate(R.id.menu_schedule));
        fragmentBinding.includeAccount.accountLayout.setOnClickListener(v -> navController.navigate(R.id.menu_account));
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            controller.popBackStack();
            fragmentBinding.includeHome.homeLayout.setProgress(0f);
            fragmentBinding.includeSchedule.scheduleLayout.setProgress(0f);
            fragmentBinding.includeAccount.accountLayout.setProgress(0f);
            switch (destination.getId()) {
                case R.id.menu_home:
                    fragmentBinding.includeHome.homeLayout.transitionToEnd();
                    break;
                case R.id.menu_account:
                    fragmentBinding.includeAccount.accountLayout.transitionToEnd();
                    break;
                default:
                    fragmentBinding.includeSchedule.scheduleLayout.transitionToEnd();
                    break;

            }
        });
    }


}