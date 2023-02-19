package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class ScreenTimeService extends Service {
    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long screenTime = ScreenTimeCalculator.calculateScreenTime(getApplicationContext());
                storeScreenTimeInDatabase(screenTime);
            }
        }, 0, 5000); // 5 seconds

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void storeScreenTimeInDatabase(long screenTime) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.addScreenTime(screenTime);
    }
}
