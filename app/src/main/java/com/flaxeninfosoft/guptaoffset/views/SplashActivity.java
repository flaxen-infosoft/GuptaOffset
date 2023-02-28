package com.flaxeninfosoft.guptaoffset.views;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Connection;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;
import com.flaxeninfosoft.guptaoffset.viewModels.LoginViewModel;
import com.flaxeninfosoft.guptaoffset.views.admin.AdminMainActivity;
import com.flaxeninfosoft.guptaoffset.views.employee.EmployeeMainActivity;
import com.flaxeninfosoft.guptaoffset.views.superEmployee.SuperEmployeeMainActivity;

public class SplashActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginViewModel.class);

        checkGps();
    }

    private void continueFlow() {
        if (Connection.isConnectingToInternet(getApplicationContext())) {
            boolean isLoggedIn = SharedPrefs.getInstance(getApplicationContext()).isLoggedIn();
            Log.e("TEST", isLoggedIn + " this is loggedIN");
            if (isLoggedIn) {
                viewModel.loginUser(SharedPrefs.getInstance(getApplicationContext()).getCredentials()).observe(this, this::onLoginRequestComplete);
            } else {
                startLoginActivity();
            }
        } else {
            Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
            startLoginActivity();
        }
    }

    private void checkGps() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enable Gps.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("No", (dialog, id) -> checkGps());
            final AlertDialog alert = builder.create();
            alert.show();
        } else {
            continueFlow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkGps();
    }

    private void onLoginRequestComplete(Boolean isSuccess) {
        if (isSuccess) {
            startHomeActivityByEmployee();
        } else {
            startLoginActivity();
        }
    }

    private void startHomeActivityByEmployee() {

        Intent intent;
        Employee employee = SharedPrefs.getInstance(getApplicationContext()).getCurrentEmployee();

        switch (employee.getDesignation()) {
            case Constants.DESIGNATION_EMPLOYEE:
                intent = new Intent(this, EmployeeMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;

            case Constants.DESIGNATION_SUPER_EMPLOYEE:
                intent = new Intent(this, SuperEmployeeMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case Constants.DESIGNATION_ADMIN:
                intent = new Intent(this, AdminMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            default:
                SharedPrefs.getInstance(getApplication().getApplicationContext()).clearCredentials();
                Toast.makeText(getApplicationContext(), "Department is not supported", Toast.LENGTH_SHORT).show();
                startLoginActivity();
        }
    }

    private void startLoginActivity() {
        SharedPrefs.getInstance(getApplication().getApplicationContext()).clearCredentials();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}