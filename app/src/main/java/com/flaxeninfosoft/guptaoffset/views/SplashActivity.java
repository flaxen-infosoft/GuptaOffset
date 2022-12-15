package com.flaxeninfosoft.guptaoffset.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;
import com.flaxeninfosoft.guptaoffset.views.admin.AdminMainActivity;
import com.flaxeninfosoft.guptaoffset.views.employee.EmployeeMainActivity;

public class SplashActivity extends AppCompatActivity {

    private MainRepository repo;
    private static int splash_time_out=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_splash);

        repo = MainRepository.getInstance(getApplicationContext());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLoggedIn = SharedPrefs.getInstance(getApplicationContext()).isLoggedIn();

                if (isLoggedIn) {
                    repo.login(SharedPrefs.getInstance(getApplicationContext()).getCredentials(), new ApiResponseListener<Employee, String>() {
                        @Override
                        public void onSuccess(Employee response) {
                            startHomeActivityByEmployee(response);
                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            startLoginActivity();
                        }
                    });
                } else {
                    startLoginActivity();
                }
            }
        },splash_time_out);


    }

    private void startHomeActivityByEmployee(Employee employee) {

        Intent intent;

        switch (employee.getDesignation()) {
            case "employee":
                intent = new Intent(this, EmployeeMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;

            case "admin":
                intent = new Intent(this, AdminMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;

            default:
                SharedPrefs.getInstance(getApplication().getApplicationContext()).clearCredentials();
                Toast.makeText(this, "Department is not supported", Toast.LENGTH_SHORT).show();
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