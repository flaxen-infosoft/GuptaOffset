package com.flaxeninfosoft.guptaoffset.views.superEmployee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivitySuperEmployeeMainBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;
import com.flaxeninfosoft.guptaoffset.views.SplashActivity;

public class SuperEmployeeMainActivity extends AppCompatActivity {

    private ActivitySuperEmployeeMainBinding binding;
    private SuperEmployeeViewModel viewModel;

    private NavController navController;
    private AppBarConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_super_employee_main);

        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.super_employee_main_host_fragment);

        navController = hostFragment.getNavController();

        config = new AppBarConfiguration.Builder(R.id.superEmployeeHomeFragment).build();

        setSupportActionBar(binding.superEmployeeMainToolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, config);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(SuperEmployeeViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_super_employee_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_super_employee_home_profile) {
            navController.navigate(R.id.employeeProfileFragment);
            return true;
        }
        if (item.getItemId() == R.id.menu_super_employee_my_employees) {
            navController.navigate(R.id.superEmployeeAllEmployeesFragment);
            return true;
        }
        if (item.getItemId() == R.id.menu_super_employee_home_logout) {

            viewModel.logout();

            Intent intent = new Intent(SuperEmployeeMainActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}