package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.myapplication.model.Password;
import com.example.myapplication.utils.Utils;
import com.shuhart.stepview.StepView;

import java.util.List;

public class PatternLockAct extends AppCompatActivity {
    StepView stepview;
    LinearLayout normalLayout;
    TextView status_password;
    RelativeLayout relativeLayout;
    Password utilsPassword;
    String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);
        Log.d("PatternLockAct", "onCreate() called");

        initLayout();
        initIconeApp();

        initPatternListener();
    }

    private void initIconeApp() {
        if(getIntent().getStringExtra("broadcast_reciever" ) !=null){
            ImageView icon = findViewById(R.id.app_icon);
            String current_app = new Utils(this ).getLastApp();
            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = getPackageManager().getApplicationInfo(current_app, 0);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
            icon.setImageDrawable(applicationInfo.loadIcon(getPackageManager()));
        }

    }

    private void initPatternListener() {
        PatternLockView patternLockView = findViewById(R.id.pattern_view);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String pwd = PatternLockUtils.patternToString(patternLockView, pattern);
                if(pwd.length()<4){
                    status_password.setText(utilsPassword.SCHEMA_FAILED);
                    patternLockView.clearPattern();
                    return;
                }
                if(utilsPassword.getPassword()==null){
                   if(utilsPassword.isFirstStep()) {
                      userPassword=pwd;
                      utilsPassword.setFirstStep(false);
                      status_password.setText(utilsPassword.STATUS_NEXT_STEP);
                      stepview.go(1,true);
                   }else{
                       if(userPassword.equals(pwd)){
                           utilsPassword.setPassword(userPassword);
                           status_password.setText(utilsPassword.STATUS_PASSWORD_CORRECT);
                           stepview.done(true);
                           startAct();
                       }else{
                           status_password.setText(utilsPassword.STATUS_PASSWORD_INCORRECT);
                       }

                   }
                }
                else{
                    if(utilsPassword.isCorrect(pwd)){
                        status_password.setText(utilsPassword.STATUS_PASSWORD_CORRECT);
                        startAct();

                    }else{
                        status_password.setText(utilsPassword.STATUS_PASSWORD_INCORRECT);
                    }
                    patternLockView.clearPattern();

                }

            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void startAct() {
        if(getIntent().getStringExtra("broadcast_reciever" ) ==null){
            startActivity(new Intent(this, BlockActivity.class));
        }

        finish();
    }

    private void initLayout() {
        stepview = findViewById(R.id.step_view);
        normalLayout=findViewById(R.id.normal_layout);
        relativeLayout = findViewById(R.id.root);
        status_password=findViewById(R.id.status_password);
        utilsPassword = new Password(this);
        status_password.setText(utilsPassword.STATUS_FIRST_STEP);

        if(utilsPassword.getPassword() == null){
            normalLayout.setVisibility(View.GONE);
            stepview.setVisibility(View.VISIBLE);
            stepview.setStepsNumber(2);
            stepview.go(0,true);
        }else{
            normalLayout.setVisibility(View.VISIBLE);
            stepview.setVisibility(View.GONE);
            int backColor = ResourcesCompat.getColor(getResources(),R.color.black, null);
            relativeLayout.setBackgroundColor(backColor);
            status_password.setTextColor(Color.WHITE);

        }
    }

    @Override
    public void onBackPressed() {
        if(utilsPassword.getPassword()==null && !utilsPassword.isFirstStep()){
            stepview.go(0,true);
            utilsPassword.setFirstStep(true);
            status_password.setText(utilsPassword.STATUS_FIRST_STEP);
        }else {
            startCurrentHomePackage();
            finish();
            super.onBackPressed();
        }
    }

    private void startCurrentHomePackage() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY );
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName,activityInfo.name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);

        new Utils(this).clearLastApp();
    }

}