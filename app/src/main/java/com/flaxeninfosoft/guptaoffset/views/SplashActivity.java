package com.flaxeninfosoft.guptaoffset.views;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.Connection;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;
import com.flaxeninfosoft.guptaoffset.viewModels.LoginViewModel;
import com.flaxeninfosoft.guptaoffset.views.admin.AdminMainActivity;
import com.flaxeninfosoft.guptaoffset.views.employee.EmployeeMainActivity;
import com.flaxeninfosoft.guptaoffset.views.employee.fragments.EmployeeHomeFragment;
import com.flaxeninfosoft.guptaoffset.views.superEmployee.SuperEmployeeMainActivity;

public class SplashActivity extends AppCompatActivity {

    private MainRepository repo;
    private LoginViewModel viewModel;
//--------------------------------------Notification-----------------------------
    private static final String CHANNEL_ID="Only For New Message";
    private static final int NOTIFICATION_ID=100;
//--------------------------------------Notification-----------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//--------------------------------------Notification Code-----------------------------

        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.splash_logo,null);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        Bitmap bitmap=bitmapDrawable.getBitmap();

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        Intent intent=new Intent(getApplicationContext(),SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notification=new Notification.Builder(this)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.splash_logo)
                    .setContentText("Application Running")
                    .setSubText("New Message")
                    .setContentIntent(pendingIntent)
                    .setChannelId(CHANNEL_ID)
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"GuptaJI Channel",NotificationManager.IMPORTANCE_HIGH)
            );
        }
        else{
            notification=new Notification.Builder(this)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.splash_logo)
                    .setContentText("New Message")
                    .setSubText("Message From Employees")
                    .setContentIntent(pendingIntent)
                    .build();
        }
        notificationManager.notify(NOTIFICATION_ID,notification);

//--------------------------------------Notification Code-----------------------------


        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginViewModel.class);
        repo = MainRepository.getInstance(getApplicationContext());

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            checkGps(manager);
        } else {
            continueFlow();
        }
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

    private void checkGps(LocationManager manager) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enable Gps.")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    continueFlow();
                })
                .setNegativeButton("No", (dialog, id) -> checkGps(manager));
        final AlertDialog alert = builder.create();
        alert.show();
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
                startLocationService();
                intent = new Intent(this, EmployeeMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;

            case Constants.DESIGNATION_SUPER_EMPLOYEE:
                startLocationService();
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

    private void startLocationService() {

    }

    private void startLoginActivity() {
        SharedPrefs.getInstance(getApplication().getApplicationContext()).clearCredentials();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}