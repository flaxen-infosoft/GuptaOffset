package com.flaxeninfosoft.guptaoffset.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class Utilities {

    public static float getBatteryLevel(Context context) {
        Intent batteryStatus = context.registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int batteryLevel = -1;
        int batteryScale = 1;
        if (batteryStatus != null) {
            batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, batteryLevel);
            batteryScale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, batteryScale);
        }
        return batteryLevel / (float) batteryScale * 100;
    }
}
