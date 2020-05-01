package com.example.mydaemondemo;

import android.content.Intent;
import android.os.Build;

import com.example.mydaemondemo.service.DaemonJobService;
import com.example.mydaemondemo.service.LocalService;
import com.example.mydaemondemo.service.RemoteService;

/**
 * Created by shurui on 2020/5/1.
 */
public class MyDaemonApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //守护进程初始化
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH){
            DaemonJobService.startJob(this);
        } else {
            startService(new Intent(this, LocalService.class));
            startService(new Intent(this, RemoteService.class));
        }
    }
}
