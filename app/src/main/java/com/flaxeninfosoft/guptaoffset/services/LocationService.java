package com.flaxeninfosoft.guptaoffset.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

public class LocationService extends Service {

    private EmployeeViewModel viewModel;

    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    private final static int INTERVAL = 20000;
    Handler m_handler = new Handler();

    Runnable m_handlerTask = new Runnable() {
        @Override
        public void run() {
            m_handler.postDelayed(m_handlerTask, INTERVAL);

            if (ActivityCompat.checkSelfPermission(LocationService.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(LocationService.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                        .addOnCompleteListener(task -> {});
            }
        }
    };



    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(EmployeeViewModel.class);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(TimeUnit.MICROSECONDS.toMillis(0));
        locationRequest.setFastestInterval(TimeUnit.SECONDS.toMillis(2));
        locationRequest.setMaxWaitTime(TimeUnit.SECONDS.toMillis(30));

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location location = new Location();
                location.setLatitude(locationResult.getLastLocation().getLatitude());
                location.setLongitude(locationResult.getLastLocation().getLongitude());

                viewModel.addCurrentEmployeeLocation(location);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       super.onStartCommand(intent, flags, startId);

        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        Log.i("LocationUpdate", "Service started");

        m_handlerTask.run();
        startMyOwnForeground();

        return START_STICKY;
    }

    private void startMyOwnForeground() {
        Drawable drawable= ResourcesCompat.getDrawable(getResources(), R.drawable.splash_logo,null);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        Bitmap bitmap=bitmapDrawable.getBitmap();

        String NOTIFICATION_CHANNEL_ID = "com.flaxeninfosoft";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.splash_logo)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        locationProviderClient.removeLocationUpdates(locationCallback).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                m_handler.removeCallbacks(m_handlerTask);
                stopForeground(Service.STOP_FOREGROUND_REMOVE);
                Toast.makeText(LocationService.this, "Service stopped", Toast.LENGTH_SHORT).show();
                Log.i("LocationUpdate", "Service stopped");
            } else {
                Toast.makeText(LocationService.this, "Failed to stop service", Toast.LENGTH_SHORT).show();
                Log.i("LocationUpdate", "Service failed to stop");
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}