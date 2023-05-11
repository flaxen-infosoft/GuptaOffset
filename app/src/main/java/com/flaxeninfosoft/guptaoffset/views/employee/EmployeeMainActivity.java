package com.flaxeninfosoft.guptaoffset.views.employee;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivityEmployeeMainBinding;
import com.flaxeninfosoft.guptaoffset.services.LocationService;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeMainActivity extends AppCompatActivity {

    private ActivityEmployeeMainBinding binding;
    private EmployeeViewModel viewModel;

    private NavController navController;
    private AppBarConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_main);

        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.employee_main_host_fragment);

        navController = hostFragment.getNavController();

        config = new AppBarConfiguration.Builder(R.id.employeeHomeFragment).build();

//        setSupportActionBar(binding.employeeMainToolbar);
//        NavigationUI.setupActionBarWithNavController(this, navController, config);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(EmployeeViewModel.class);
        viewModel.getToastMessageLiveData().observe(this, this::showToastMessage);

        startLocationService();
    }

    private void startLocationService() {
        if (isLocationPermissionGranted()) {
            Intent intent = new Intent(this, LocationService.class);
            startService(intent);
        } else {
            Toast.makeText(this, "Location and Network permission needed", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeMainActivity.this);
            builder.setTitle("Permission Denied")
                    .setCancelable(false)
                    .setMessage("आपने लोकेशन की परमिशन नही दी है कृप्या सेटिंग में जाकर परमिशन दे")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Ok", (dialog, which) -> {

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(intent);

                    }).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_employee_home_profile) {
            navController.navigate(R.id.employeeProfileFragment);
            return true;
        }

//        if (item.getItemId() == R.id.menu_employee_home_refresh) {
//            viewModel.getCurrentEmployeeHistory().observe(this, this::setEmployeeHistory);
//            return true;
//        }

//        if (item.getItemId() == R.id.menu_employee_home_logout) {
//
//            viewModel.logout();
//            stopLocationService();
//            Intent intent = new Intent(EmployeeMainActivity.this, SplashActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
//            return true;
//        }
        return false;
    }


    private void stopLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }

    private void showToastMessage(String s) {
        Log.d("CRM-LOG", s);
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}