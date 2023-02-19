package com.example.myapplication;

import static com.example.myapplication.ScreenTimeCalculator.calculateScreenTime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScreenTimeactivity extends AppCompatActivity {
    private TextView screenTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_timeactivity);
        screenTimeTextView = findViewById(R.id.screen_time_text_view);
        Intent serviceIntent = new Intent(this, ScreenTimeService.class);
        startService(serviceIntent);
        updateScreenTimeTextView();


    }
    private void updateScreenTimeTextView() {
        DatabaseHelper db = new DatabaseHelper(this);

        long totalScreenTime = db.getTotalScreenTime();
        long screenTime = calculateScreenTime(this);
        long screenTimeHours = TimeUnit.MILLISECONDS.toHours(screenTime);
        screenTimeTextView.setText( + screenTimeHours + " hours");
        if(screenTimeHours>= 16){
            new SweetAlertDialog(ScreenTimeactivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Message")
                    .setContentText("Looks like your child has used enough phone for the day . Do you want to block some of the addictive apps?")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent i = new Intent(ScreenTimeactivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    })
                    .show();
        }else {
            screenTimeTextView.setText( "Today: "+ screenTimeHours + " "+  "hrs");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkUsageAccessPermission();
    }
    private void checkUsageAccessPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (mode != AppOpsManager.MODE_ALLOWED) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ScreenTimeactivity.this, ChildHome.class));
        finish();

    }



}