package com.example.mydaemondemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import com.example.mydaemondemo.receiver.OnePixelReceiver;
import com.example.mydaemondemo.service.DaemonJobService;
import com.example.mydaemondemo.service.FrontService;
import com.example.mydaemondemo.service.LiveService2;
import com.example.mydaemondemo.service.LocalService;
import com.example.mydaemondemo.service.RemoteService;
import com.example.mydaemondemo.util.MyLifecycleHandler;

/**
 * Created by shurui on 2020/5/1.
 */
public class MyDaemonApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //守护进程初始化
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            DaemonJobService.startJob(this);
        } else {
            startService(new Intent(this, LocalService.class));
            startService(new Intent(this, RemoteService.class));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0以上通过startForegroundService启动service
            startForegroundService(new Intent(this, FrontService.class));
        }

        //注册方法，用于监听app是否在前台运行
        registerActivityLifecycleCallbacks(new MyLifecycleHandler());

        //注册广播，用于监听一像素保活
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new OnePixelReceiver(), intentFilter);

        //无声音乐保活
        Intent intent = new Intent(this, LiveService2.class);
        startService(intent);

//        new Thread(() -> {
//            while (true) {
//                try {
//                    if (!MyLifecycleHandler.isApplicationVisible()) {
//                        stopPlayNoNoticeService();
//                    } else {
//                        startPlayNoNoticeService();
//                    }
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }


    private void stopPlayNoNoticeService() {
        Log.i("LiveService2", "-----app处于前台，停止音乐");
        if (LiveService2.checkRunning()) {
            Intent intent = new Intent(this, LiveService2.class);
            stopService(intent);
        }
    }

    private void startPlayNoNoticeService() {
        Log.i("LiveService2", "-----app处于后台，播放音乐");

        Intent intent = new Intent(this, LiveService2.class);
        startService(intent);

    }
}
