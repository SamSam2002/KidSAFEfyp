package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageView emergency;

    private ImageView location;

    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);

        mediaPlayer.setLooping(true);
        emergency = findViewById(R.id.emer);
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play the alarm sound
                mediaPlayer.start();

                // Create a SweetAlertDialog to display the stop button
                SweetAlertDialog alertDialog = new SweetAlertDialog(AlarmActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Emergency Alert")
                        .setContentText("Alarm triggered!")
                        .setCancelText("Cancel")
                        .setConfirmText("Stop")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                stopAlarm();
                                sweetAlertDialog.dismiss();
                            }
                        });

                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        location = findViewById(R.id.loc);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AlarmActivity.this, LocationTracker.class);
                startActivity(intent);
                finish();
            }
        });
        login = findViewById(R.id.parent_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AlarmActivity.this, ChildLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void stopAlarm() {
        mediaPlayer.stop();
        mediaPlayer.prepareAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
