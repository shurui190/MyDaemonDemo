package com.example.mydaemondemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.mydaemondemo.IMyAidlInterface;

import androidx.annotation.Nullable;

/**
 * Created by shurui on 2020/5/1.
 */
public class RemoteService extends Service {
    private final static int NOTIFICATION_ID = 1002;
    private static final String TAG = "RemoteService";
    private ServiceConnection serviceConnection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Notification notification = new Notification();

            startForeground(NOTIFICATION_ID, notification);
            // start InnerService
            startService(new Intent(this, InnerService.class));

        } catch (Throwable e) {
            e.printStackTrace();
        }

        serviceConnection = new RemoteServiceConnection();
        startService(new Intent(this, LocalService.class));
        bindService(new Intent(this, LocalService.class), serviceConnection, BIND_AUTO_CREATE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    class RemoteServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务连接后回调
            Log.d(TAG, "bind LocalService");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "main process local service died，make it alive");
            //连接中断后回调
            startService(new Intent(RemoteService.this, LocalService.class));
            bindService(new Intent(RemoteService.this, LocalService.class), serviceConnection,
                    BIND_AUTO_CREATE);
        }
    }


    public static class InnerService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }


        @Override
        public void onCreate() {
            super.onCreate();
            try {
                startForeground(NOTIFICATION_ID, new Notification());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            stopSelf();
        }

        @Override
        public void onDestroy() {
            stopForeground(true);
            super.onDestroy();
        }
    }


    static class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }
}
