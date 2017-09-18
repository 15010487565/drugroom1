package com.medicinedot.www.medicinedot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.RegisterSuppliercodeinfo;
import com.medicinedot.www.medicinedot.bean.RegisterSupplierinfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

public class RegisterSupplierActivity extends SimpleTopbarActivity {

    private EditText forget_phone,authcode_edit,forget_poasswod,confirm_password;
    private TextView authcode_button,agreement_text,login;
    private CheckBox agreement_checkbox;
    private Button register;
    private TimeCount time;
    private int SELECTTYPE = -1;
    private String invitation_code;
    @Override
    protected Object getTopbarTitle() {
        return R.string.register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersupplier);
        SELECTTYPE = getIntent().getIntExtra("SELECTTYPE",-1);
        invitation_code = getIntent().getStringExtra("invitecode");
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
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
        //同意协议
        agreement_checkbox = (CheckBox) findViewById(R.id.agreement_checkbox);
        agreement_checkbox.setChecked(true);
        agreement_text = (TextView) findViewById(R.id.agreement_text);
        agreement_text.setOnClickListener(this);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.agreement_text:
                agreement_checkbox.setChecked(!agreement_checkbox.isChecked());
                break;
            case R.id.login:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
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
            case R.id.register:

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
                if (forgetpoasswod.length()<6){
                    ToastUtil.showToast("密码长度为6-20位");
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
                if (!agreement_checkbox.isChecked()){
                    ToastUtil.showToast("请阅读并同意用户协议");
                    return;
                }

                createDialogshow();
                String deviceId = ClassUtils.getDeviceId(this);
                Log.e("TAG_deviceId","deviceId="+deviceId);
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("phone", forgetphone);
                params.put("pwd", forgetpoasswod);
                params.put("vcode", authcodeedit);
                params.put("vkey", deviceId);//设备唯一值
                if (SELECTTYPE ==1){
                    params.put("utype", "2");//utype : 用户类型 1 供应商 2 药店
                }else{
                    params.put("utype", "1");//utype : 用户类型 1 供应商 2 药店
                }

                if (invitation_code==null||"".equals(invitation_code)){
                    params.put("invitecode", "");
                }else {
                    params.put("invitecode", invitation_code);
                }

                okHttpPost(100, GlobalParam.REGISTER, params);
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
                    RegisterSupplierinfo info = JSON.parseObject(returnData, RegisterSupplierinfo.class);
                    String utype = info.getData().getUtype();
                    if ("2".equals(utype)){
                        Intent intent = new Intent(this, RegisterDrugstoreInfoActivity.class);
                        String uid = info.getData().getUid();
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("uid", uid);
                        String token = info.getData().getToken();
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("token", token);
                        intent.putExtra("uid",uid);
                        startActivity(intent);
                    }else if ("1".equals(utype)){
                        Intent intent = new Intent(this, RegisterSupplierInfoActivity.class);
                        String uid = info.getData().getUid();
                        intent.putExtra("uid",uid);
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("uid", uid);
                        String token = info.getData().getToken();
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("token", token);
                        startActivity(intent);
                    }
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
