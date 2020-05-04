package com.example.mydaemondemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mydaemondemo.activity.OnePxActivity;


/**
 * Created by shurui on 2020/4/10.
 * 1像素保活，用于监听息屏
 */

public class OnePixelReceiver extends BroadcastReceiver {
    private Context mContext;
    // 锁屏广播接收器
    private OnePixelReceiver.SreenBroadcastReceiver mScreenReceiver;
    // 屏幕状态改变回调接口
    private OnePixelReceiver.SreenStateListener mStateReceiverListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        //屏幕关闭启动1像素Activity
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("OnePx", "屏幕关闭启动1像素Activity");
            Intent it = new Intent(context, OnePxActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
            //屏幕打开 结束1像素
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("OnePx", "屏幕打开 结束1像素");
            context.sendBroadcast(new Intent("finish"));
        }
    }

    public class SreenBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("KeepAppAlive", "SreenLockReceiver-->监听到系统广播：" + action);
            if (mStateReceiverListener == null) {
                return;
            }
            if (Intent.ACTION_SCREEN_ON.equals(action)) {         // 开屏
                mStateReceiverListener.onSreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {  // 锁屏
                mStateReceiverListener.onSreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                mStateReceiverListener.onUserPresent();
            }
        }
    }

    // 监听sreen状态对外回调接口
    public interface SreenStateListener {
        void onSreenOn();

        void onSreenOff();

        void onUserPresent();
    }

}
