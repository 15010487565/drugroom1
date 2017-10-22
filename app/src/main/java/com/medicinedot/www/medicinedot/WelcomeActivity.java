package com.medicinedot.www.medicinedot;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.medicinedot.www.medicinedot.activity.LoginActivity;
import com.medicinedot.www.medicinedot.activity.WelcomeViewPagerActivity;

import www.xcd.com.mylibrary.utils.XCDSharePreference;

/**
 * Created by Android on 2017/8/7.
 */

public class WelcomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /**全屏设置，隐藏窗口所有装饰**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 2000);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    class splashhandler implements Runnable {

        public void run() {
            try {
                String fristversionName= XCDSharePreference.getInstantiation(WelcomeActivity.this).getSharedPreferences("FRISTVERSIONNAME");
                String versionName = getVersionName();
                if (versionName.equals(fristversionName)){
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }else {

                    startActivity(new Intent(WelcomeActivity.this, WelcomeViewPagerActivity.class));
                }
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }
}

