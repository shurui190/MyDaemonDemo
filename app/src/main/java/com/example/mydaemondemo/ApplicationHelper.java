package com.example.mydaemondemo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;

import java.util.List;

/**
 * 线程转换管理器
 * Created by shurui
 */
public class ApplicationHelper {

    private static Context sContext;

    private static Handler sWorker;

    private static Handler sHandler;

    public static void setUp(Context context, Handler worker, Handler handler) {
        sContext = context;
        sWorker = worker;
        sHandler = handler;
    }

    /**
     * 向线程发送数据
     * @param r runnable 对象
     */
    public static void postThread(Runnable r) {
        if (null != sWorker) {
            sWorker.post(r);
        } else {
            throw new RuntimeException("call setUp first");
        }
    }

    public static void postThreadDelayed(Runnable r, long delayMillis) {
        if (null != sWorker) {
            sWorker.postDelayed(r, delayMillis);
        } else {
            throw new RuntimeException("call setUp first");
        }
    }

    /**
     * 向ＵＩ线程发送数据
     * @param r runnable 对象
     */
    public static void postRunInUiThread(Runnable r) {
        if (null != sHandler) {
            sHandler.post(r);
        } else {
            throw new RuntimeException("call setUp first");
        }
    }

    public static void postUiThreadDelayed(Runnable r, long delayMillis) {
        if (null != sHandler) {
            sHandler.postDelayed(r, delayMillis);
        } else {
            throw new RuntimeException("call setUp first");
        }
    }

    public static void removeFromUiThread(Runnable r) {
        sHandler.removeCallbacks(r);
    }

    public static Context getContext() {
        if (sContext == null) {
            throw new RuntimeException("call setUp first");
        }
        return sContext;
    }


    public static String getProcessName(Context context){
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        if (processes == null){
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo info : processes){
            if (info.pid == pid){
                return info.processName;
            }
        }
        return null;
    }
}
