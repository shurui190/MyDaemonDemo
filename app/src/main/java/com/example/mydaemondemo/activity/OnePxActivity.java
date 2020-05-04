package com.example.mydaemondemo.activity;

/**
 * Created by shurui on 2020/4/10.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.mydaemondemo.MainActivity;
import com.example.mydaemondemo.util.Contants;
import com.example.mydaemondemo.util.ScreenManager;
import com.example.mydaemondemo.util.SystemUtils;

import androidx.annotation.Nullable;


public class OnePxActivity extends Activity {
    private static final String TAG = "OnePx";
    private BroadcastReceiver endReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate--->启动1像素保活");
        // 获得activity的Window对象，设置其属性
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 1;
        attrParams.width = 1;
        mWindow.setAttributes(attrParams);

        ScreenManager.getScreenManagerInstance(this).setSingleActivity(this);
        //结束该页面的广播
        endReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive--->finish");
                finish();
            }
        };
        registerReceiver(endReceiver, new IntentFilter("finish"));
        checkScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 检查屏幕状态  isScreenOn为true  屏幕“亮”结束该Activity
     */
    private void checkScreen() {
        PowerManager pm = (PowerManager) OnePxActivity.this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (!SystemUtils.isAPPAlive(this, Contants.PACKAGE_NAME)) {
            Intent intentAlive = new Intent(this, MainActivity.class);
            intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentAlive);
            Log.i(TAG, "SinglePixelActivity---->APP被干掉了，我要重启它");
        }
        super.onDestroy();
        unregisterReceiver(endReceiver);
    }

}
