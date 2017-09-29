package com.medicinedot.www.medicinedot.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.RegisterSuppliercodeinfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.ToastUtil;

public class ForgetPasswordActivity extends SimpleTopbarActivity {

    private EditText forget_phone,authcode_edit,forget_poasswod,confirm_password;
    private TextView authcode_button;
    private Button ok;
    private TimeCount time;
    @Override
    protected Object getTopbarTitle() {
        return R.string.forgetpassword;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        forget_phone = (EditText) findViewById(R.id.forget_phone);
        forget_phone.setOnFocusChangeListener(this);
        authcode_edit = (EditText) findViewById(R.id.authcode_edit);
        authcode_edit.setOnFocusChangeListener(this);
        forget_poasswod = (EditText) findViewById(R.id.forget_poasswod);
        forget_poasswod.setOnFocusChangeListener(this);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        confirm_password.setOnFocusChangeListener(this);
        authcode_button = (TextView) findViewById(R.id.authcode_button);
        authcode_button.setOnClickListener(this);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.authcode_button:
                String forgetphone1  = forget_phone.getText().toString().trim();
                if ("".equals(forgetphone1)||forgetphone1==null){
                    ToastUtil.showToast("手机号不能为空");
                    return;
                }
                boolean mobileNO1 = ClassUtils.isMobileNO(forgetphone1);
                if (!mobileNO1){
                    ToastUtil.showToast("请输入正确的手机号");
                    return;
                }
                time.start();
                createDialogshow();
                String deviceIdcode = ClassUtils.getDeviceId(this);
                Map<String, Object> paramscode = new HashMap<String, Object>();
                paramscode.put("vkey", deviceIdcode);
                paramscode.put("phone", forgetphone1);
                okHttpGet(101, GlobalParam.GETVERIFICATIONCODE, paramscode);
                break;
            case R.id.ok:
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
                String authcodeedit = authcode_edit.getText().toString().trim();
                if ("".equals(authcodeedit)||authcodeedit==null){
                    ToastUtil.showToast("验证码不能为空");
                    return;
                }
                String forgetpoasswod = forget_poasswod.getText().toString().trim();
                if ("".equals(forgetpoasswod)||forgetpoasswod==null){
                    ToastUtil.showToast("密码不能为空");
                    return;
                }
                String confirmpassword = confirm_password.getText().toString().trim();
                if ("".equals(confirmpassword)||confirmpassword==null){
                    ToastUtil.showToast("确认密码不能为空");
                    return;
                }
                if (!forgetpoasswod.equals(confirmpassword)){
                    ToastUtil.showToast("两次密码不相同");
                    return;
                }
                String deviceId = ClassUtils.getDeviceId(this);
                createDialogshow();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("phone", forgetphone);
                params.put("pwd", forgetpoasswod);
                params.put("vkey", deviceId);
                params.put("vcode", authcodeedit);
                okHttpPost(100, GlobalParam.FORGETPASSWORD, params);
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
                    ToastUtil.showToast(returnMsg);
                    finish();
                    break;
                case 101:
                    RegisterSuppliercodeinfo codeinfo = JSON.parseObject(returnData, RegisterSuppliercodeinfo.class);
                    String code = codeinfo.getData();
                    break;
            }
        }else {
            ToastUtil.showToast(returnMsg);
        }
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
