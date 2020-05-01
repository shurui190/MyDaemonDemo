package com.example.mydaemondemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 基础Application
 * Created by shurui .
 */
public class BaseApp extends Application {


    private static HandlerThread sWorkerThread = new HandlerThread("app-main");

    static {
        sWorkerThread.start();
    }

    private static Handler sWorker = new Handler(sWorkerThread.getLooper());

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        try {
            Class.forName("android.os.AsyncTask");
        } catch (Throwable ignore) {
        }
        super.onCreate();
    }

    @Override
    public void startActivity(Intent intent) {
        if (null != intent) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        super.startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ApplicationHelper.setUp(this, sWorker, sHandler);
    }
}
