package com.medicinedot.www.medicinedot.application;

import android.util.Log;

import com.yonyou.sns.im.core.YYIMChat;

import www.xcd.com.mylibrary.base.application.BaseApplication;

/**
 * Created by Android on 2017/9/5.
 */

public class YDApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            //初始化头像jar
            YYIMChat.getInstance().init(getApplicationContext());
            YYIMChat.getInstance().configLogger(Log.VERBOSE, true, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
