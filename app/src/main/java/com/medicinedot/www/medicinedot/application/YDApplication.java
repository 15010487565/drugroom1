package com.medicinedot.www.medicinedot.application;

import android.support.multidex.MultiDex;
import android.util.Log;

import com.medicinedot.www.medicinedot.rong.RongReceiveMessageListener;
import com.medicinedot.www.medicinedot.rong.SendMessageListener;
import com.yonyou.sns.im.core.YYIMChat;

import io.rong.imkit.RongIM;
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
        try {
            /**
             * 初始化融云
             */
            RongIM.init(this);
            MultiDex.install(this);
            RongIM.setOnReceiveMessageListener(new RongReceiveMessageListener());
            RongIM.getInstance().setSendMessageListener(new SendMessageListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
