package com.medicinedot.www.medicinedot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.RegisterSuppliercodeinfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;


public class SettingNewBindingmobileActivity extends SimpleTopbarActivity {

    private EditText forget_phone,authcode_edit;
    private TextView authcode_button;
    private Button ok;
    private TimeCount time;
    @Override
    protected Object getTopbarTitle() {
        return "输入新手机号";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingnewbindingmobile);
        initView();
    }

    private void initView(){
        forget_phone = (EditText) findViewById(R.id.forget_phone);
        forget_phone.setOnFocusChangeListener(this);
        authcode_edit = (EditText) findViewById(R.id.authcode_edit);
        authcode_edit.setOnFocusChangeListener(this);
        authcode_button = (TextView) findViewById(R.id.authcode_button);
        authcode_button.setOnClickListener(this);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
    }
    private String code;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        String forgetphone  = forget_phone.getText().toString().trim();
        if ("".equals(forgetphone)||forgetphone==null){
            ToastUtil.showToast("手机号不能为空");
            return;
        }
        boolean mobileNO = ClassUtils.isMobileNO(forgetphone);
        if (!mobileNO){
            ToastUtil.showToast("请输入正确的手机号");
            return;
        }
        String deviceIdcode = ClassUtils.getDeviceId(this);
        switch (v.getId()){
            case R.id.authcode_button:
                createDialogshow();
                Map<String, Object> paramscode = new HashMap<String, Object>();
                paramscode.put("vkey", deviceIdcode);
                paramscode.put("phone", forgetphone);
                okHttpGet(101, GlobalParam.GETVERIFICATIONCODE, paramscode);
                time.start();
                break;
            case R.id.ok:

                String authcodeedit = authcode_edit.getText().toString().trim();
                if ("".equals(authcodeedit)||authcodeedit==null){
                    ToastUtil.showToast("验证码不能为空");
                    return;
                }
                if (!code.equals(authcodeedit)){
                    ToastUtil.showToast("您输入的验证码有误！");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("MOBILE",forgetphone);
                this.setResult(Activity.RESULT_OK,intent);
                finish();

               String uid= XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
                createDialogshow();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("phone", forgetphone);
                params.put("uid", uid);
                params.put("vcode", authcodeedit);
                params.put("vkey", deviceIdcode);
                okHttpPost(100, GlobalParam.UPPHONE, params);
                break;
        }
    }
    /**
     * 倒计时内部类
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数：总时长，计时间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            authcode_button.setText("重新验证");
            authcode_button.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished){// 计时过程显示
            authcode_button.setClickable(false);
            authcode_button.setText(millisUntilFinished /1000 + "秒");
        }
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200){
            switch (requestCode){
                case 100:
                    Intent in = new Intent("android.intent.action.LOGIN");
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    finish();
                    break;
                case 101:
                    RegisterSuppliercodeinfo codeinfo = JSON.parseObject(returnData, RegisterSuppliercodeinfo.class);
                    code = codeinfo.getData();
                    break;
            }
        }
        ToastUtil.showToast(returnMsg);
    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }
}
