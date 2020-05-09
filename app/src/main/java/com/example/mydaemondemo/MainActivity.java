package com.example.mydaemondemo;

import android.os.Bundle;
import android.view.View;

import com.example.mydaemondemo.util.IntentWrapperUtils;
import com.shihoo.daemon.DaemonEnv;
import com.shihoo.daemon.IntentWrapper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //是否 任务完成, 不再需要服务运行? 最好使用SharePreference，注意要在同一进程中访问该属性
    public static boolean isCanStartWorkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                DaemonEnv.sendStartWorkBroadcast(this);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isCanStartWorkService = true;
                        DaemonEnv.startServiceSafely(MainActivity.this, MainWorkService.class);
                    }
                }, 1000);
                break;
            case R.id.btn_white:
                IntentWrapperUtils.whiteListMatters(this, "轨迹跟踪服务的持续运行");
                break;
            case R.id.btn_stop:

                DaemonEnv.sendStopWorkBroadcast(this);
                isCanStartWorkService = false;
                break;
        }
    }

    //防止华为机型未加入白名单时按返回键回到桌面再锁屏后几秒钟进程被杀
    public void onBackPressed() {
        IntentWrapper.onBackPressed(this);
    }


}
