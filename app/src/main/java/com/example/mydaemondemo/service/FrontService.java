package com.example.mydaemondemo.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.example.mydaemondemo.MainActivity;
import com.example.mydaemondemo.R;

import androidx.annotation.Nullable;

/**
 * Created by shurui on 2020/3/11.
 * 用于开启前台服务 提高优先级
 */

public class FrontService extends Service {

    private NotificationManager notificationManager;
    private String notificationId = "fwchannelId";
    private String notificationName = "fwchannelName";
    private int importance = NotificationManager.IMPORTANCE_HIGH;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //创建NotificationChannel
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, importance);
            notificationManager.createNotificationChannel(channel);
            startForeground(1, getNotification());
        }
    }


    private Notification getNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("前台服务,以提高优先级")
                .setContentText("我正在运行");
        builder.setContentIntent(pendingIntent);
        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }


}
