package com.example.myapplication;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SaveMyAppsService extends Service {

    // Set the package names of the apps you want to show a password activity for
    private static final String[] TARGET_PACKAGE_NAMES = {"com.fast.lock"};

    private String currentPackageName;
    private boolean isRunning;
    private Handler handler;
    private Runnable runnable;
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        currentPackageName = getPackageName();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkRunningApps();
                if (isRunning) {
                    handler.postDelayed(this, 100);
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        handler.post(runnable);

        Notification notification = new NotificationCompat.Builder(this, "my_channel_id")
                .setContentTitle("SaveMyAppsService")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "my_channel_id",
                    "My Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
        startForeground(NOTIFICATION_ID, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkRunningApps() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 1000; // 1 second ago
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime);
        if (usageStatsList != null && usageStatsList.size() > 0) {
            UsageStats recentUsageStats = null;
            for (UsageStats usageStats : usageStatsList) {
                if (recentUsageStats == null || usageStats.getLastTimeUsed() > recentUsageStats.getLastTimeUsed()) {
                    recentUsageStats = usageStats;
                }
            }
            String packageName = recentUsageStats.getPackageName();
            Log.d("SaveMyAppsService", "Package Name: " + packageName);
            for (String targetPackageName : TARGET_PACKAGE_NAMES) {
                if (packageName.contains(targetPackageName)) {
                    showPasswordActivity();
                    return;
                }
            }
        }
    }


    private void showPasswordActivity() {
        // Launch your password activity here
        Log.d("SaveMyAppsService", "Launching password activity");
        // Launch your password activity here
        Intent passwordIntent = new Intent(this, PatternLockAct.class);
        passwordIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Log.d("SaveMyAppsService", "About to start password activity");

        Log.d("SaveMyAppsService", "Password activity launched");
    }
}
