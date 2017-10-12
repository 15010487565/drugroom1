package com.medicinedot.www.medicinedot.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.medicinedot.www.medicinedot.action.MeNVipSupplierAction;
import com.medicinedot.www.medicinedot.entity.Config;

import java.util.Map;

import www.xcd.com.mylibrary.utils.ToastUtil;

/**
 * Created by Android on 2017/6/27.
 */

public class ZFBPay {

    private Handler handler;
    public ZFBPay(Handler handler) {
        this.handler = handler;
    }

    public  void buttonAlipay(final Activity activity, final String orderInfo) {

        Log.e("TAG_", "PRIATE=" + Config.RSA_PRIVATE);
        if ("error".equals( Config.RSA_PRIVATE)){
            ToastUtil.showToast("请登录正式版app支付");
            return;
        }
        // 必须异步调用
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = handler.obtainMessage();
                msg.what = MeNVipSupplierAction.SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
