package com.samikshya.lock.activities.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.samikshya.lock.R;
import com.samikshya.lock.activities.lock.GestureSelfUnlockActivity;
import com.samikshya.lock.activities.pwd.CreatePwdActivity;
import com.samikshya.lock.base.AppConstants;
import com.samikshya.lock.base.BaseActivity;
import com.samikshya.lock.services.BackgroundManager;
import com.samikshya.lock.services.LoadAppListService;
import com.samikshya.lock.services.LockService;
import com.samikshya.lock.utils.AppUtils;
import com.samikshya.lock.utils.LockUtil;
import com.samikshya.lock.utils.SpUtil;
import com.samikshya.lock.utils.ToastUtil;
import com.samikshya.lock.widget.DialogPermission;



public class SplashActivity extends BaseActivity {
    private static final int RESULT_ACTION_USAGE_ACCESS_SETTINGS = 1;
    private static final int RESULT_ACTION_ACCESSIBILITY_SETTINGS = 3;

    private ImageView mImgSplash;
    @Nullable
    private ObjectAnimator animator;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        AppUtils.hideStatusBar(getWindow(), true);
        mImgSplash = findViewById(R.id.img_splash);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void initData() {
//        //startService(new Intent(this, LoadAppListService.class));
//        BackgroundManager.getInstance().init(this).startService(LoadAppListService.class);
//
//        //start lock services if  everything is already  setup
//        if (SpUtil.getInstance().getBoolean(AppConstants.LOCK_STATE, false)) {
//            BackgroundManager.getInstance().init(this).startService(LockService.class);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            animator = ObjectAnimator.ofFloat(mImgSplash, "alpha", 0.5f, 1);
//        }
//        animator.setDuration(1500);
//        animator.start();
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                boolean isFirstLock = SpUtil.getInstance().getBoolean(AppConstants.LOCK_IS_FIRST_LOCK, true);
//                if (isFirstLock) {
//                    showDialog();
//                } else {
//                    Intent intent = new Intent(SplashActivity.this, GestureSelfUnlockActivity.class);
//                    intent.putExtra(AppConstants.LOCK_PACKAGE_NAME, AppConstants.APP_PACKAGE_NAME);
//                    intent.putExtra(AppConstants.LOCK_FROM, AppConstants.LOCK_FROM_LOCK_MAIN_ACITVITY);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                }
//            }
//        });


            //startService(new Intent(this, LoadAppListService.class));
            BackgroundManager.getInstance().init(this).startService(LoadAppListService.class);

            //start lock services if everything is already setup
            if (SpUtil.getInstance().getBoolean(AppConstants.LOCK_STATE, false)) {
                BackgroundManager.getInstance().init(this).startService(LockService.class);
            }

            // Instead of animating the splash image and showing the lock screen, start the lock screen directly
            boolean isFirstLock = SpUtil.getInstance().getBoolean(AppConstants.LOCK_IS_FIRST_LOCK, true);
            if (isFirstLock) {
                showDialog();
            } else {
                Intent intent = new Intent(this, GestureSelfUnlockActivity.class);
                intent.putExtra(AppConstants.LOCK_PACKAGE_NAME, AppConstants.APP_PACKAGE_NAME);
                intent.putExtra(AppConstants.LOCK_FROM, AppConstants.LOCK_FROM_LOCK_MAIN_ACITVITY);
                startActivity(intent);
                finish();
            }
        }



    private void showDialog() {
        // If you do not have access to view usage rights and the phone exists to view usage this interface
        if (!LockUtil.isStatAccessPermissionSet(SplashActivity.this) && LockUtil.isNoOption(SplashActivity.this)) {
            DialogPermission dialog = new DialogPermission(SplashActivity.this);
            dialog.show();
            dialog.setOnClickListener(new DialogPermission.onClickListener() {
                @Override
                public void onClick() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Intent intent = null;
                        intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        startActivityForResult(intent, RESULT_ACTION_USAGE_ACCESS_SETTINGS);
                    }
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                gotoCreatePwdActivity();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ACTION_USAGE_ACCESS_SETTINGS) {
            if (LockUtil.isStatAccessPermissionSet(SplashActivity.this)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                    gotoCreatePwdActivity();
                }
            } else {
                ToastUtil.showToast("Permission denied");
                finish();
            }
        }
        if (requestCode == RESULT_ACTION_ACCESSIBILITY_SETTINGS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                gotoCreatePwdActivity();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public boolean isAccessibilityEnabled() {
        int accessibilityEnabled = 0;
        final String ACCESSIBILITY_SERVICE = "io.github.subhamtyagi.privacyapplock/com.lzx.lock.service.LockAccessibilityService";
        try {
            accessibilityEnabled = Settings.Secure.getInt(this.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            //setting not found so your phone is not supported
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessabilityService = mStringColonSplitter.next();
                    if (accessabilityService.equalsIgnoreCase(ACCESSIBILITY_SERVICE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void gotoCreatePwdActivity() {
        Intent intent2 = new Intent(SplashActivity.this, CreatePwdActivity.class);
        startActivity(intent2);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void initAction() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animator = null;
    }
}
