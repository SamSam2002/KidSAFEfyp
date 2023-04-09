package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.utils.Utils;

public class LockScreenReceiver extends BroadcastReceiver {

    private Context mContext;
    private Utils mUtils;

    public LockScreenReceiver(Context context) {
        mContext = context;
        mUtils = new Utils(mContext);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)
                || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (mUtils.isLock(packageName)) {
                Intent lockIntent = new Intent(context, PatternLockAct.class);
                lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                lockIntent.putExtra("packageName", packageName);
                context.startActivity(lockIntent);
            }
        }
    }


}
