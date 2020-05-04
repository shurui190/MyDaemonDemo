package com.example.mydaemondemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mydaemondemo.activity.OnePxActivity;

import java.lang.ref.WeakReference;

/**1像素管理类
 *
 * Created by shurui on 2020/4/10.
 */

public class    ScreenManager {
    private static final String TAG = "ScreenManager";
    private Context mContext;
    private static ScreenManager mSreenManager;
    // 使用弱引用，防止内存泄漏
    private WeakReference<Activity> mActivityRef;

    private ScreenManager(Context mContext){
        this.mContext = mContext;
    }

    // 单例模式
    public static ScreenManager getScreenManagerInstance(Context context){
        if(mSreenManager == null){
            mSreenManager = new ScreenManager(context);
        }
        return mSreenManager;
    }

    // 获得OnePxActivity的引用
    public void setSingleActivity(Activity mActivity){
        mActivityRef = new WeakReference<>(mActivity);
    }

    // 启动OnePxActivity
    public void startActivity(){
        if(Contants.DEBUG)
            Log.d(TAG,"准备启动OnePxActivity...");
        Intent intent = new Intent(mContext, OnePxActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    // 结束OnePxActivity
    public void finishActivity(){
        if(Contants.DEBUG)
            Log.d(TAG,"准备结束OnePxActivity...");
        if(mActivityRef != null){
            Activity mActivity = mActivityRef.get();
            if(mActivity != null){
                mActivity.finish();
            }
        }
    }
}
