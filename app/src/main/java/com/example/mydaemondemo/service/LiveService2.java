package com.example.mydaemondemo.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.mydaemondemo.R;
import com.example.mydaemondemo.util.MyLifecycleHandler;

import androidx.annotation.Nullable;


/**
 * Created by shurui on 2020/4/10.
 * 播放无声音乐的服务类
 */

public class LiveService2 extends Service {
    private final static String TAG = "LiveService2";
    private MediaPlayer mMediaPlayer;

    private static int count ;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        count++;
        Log.i(TAG,"----- count"+count);
        Log.d(TAG, TAG + "---->onCreate,启动服务");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.no_notice);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(()->{
         startPlayMusic();
        }).start();
        return START_STICKY;
    }

    private void startPlayMusic() {
        if (mMediaPlayer != null) {
            Log.d(TAG, "启动后台播放音乐");
            mMediaPlayer.start();
        }
    }

    private void stopPlayMusic() {
        if (mMediaPlayer != null) {
            Log.d(TAG, "关闭后台播放音乐");
            mMediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        count--;
        stopPlayMusic();
        Log.d(TAG, TAG + "---->onCreate,停止服务");
        // 重启
        Intent intent = new Intent(getApplicationContext(), LiveService2.class);
        startService(intent);

    }
    public static boolean checkRunning(){
        Log.i(TAG,"-----check count "+count);
        if(count>0){
            return true;
        }
        else {
            return false;
        }
    }



}
