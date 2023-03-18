package com.flaxeninfosoft.guptaoffset.views.superEmployee;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivitySuperEmployeeMainBinding;
import com.flaxeninfosoft.guptaoffset.services.LocationService;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;
import com.flaxeninfosoft.guptaoffset.views.SplashActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SuperEmployeeMainActivity extends AppCompatActivity {

    private ActivitySuperEmployeeMainBinding binding;
    private SuperEmployeeViewModel viewModel;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_super_employee_main);

        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.super_employee_main_host_fragment);

        navController = hostFragment.getNavController();

//        config = new AppBarConfiguration.Builder(R.id.superEmployeeHomeFragment).build();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(SuperEmployeeViewModel.class);

        startLocationService();
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
            stopLocationService();
            Intent intent = new Intent(SuperEmployeeMainActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void startLocationService() {
        if (isLocationPermissionGranted()) {
            Intent intent = new Intent(this, LocationService.class);
            startService(intent);
        } else {
            Toast.makeText(this, "Location and Network permission needed", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isLocationPermissionGranted() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    69
            );
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 69) {
            if (resultCode == RESULT_OK) {

                startLocationService();

            }
        }
    }

    private void stopLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    public void setupActionBar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.super_employee_main_host_fragment);

        navController = hostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);
        toolbar.setTitle(title);
    }
}