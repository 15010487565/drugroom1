package com.medicinedot.www.medicinedot.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medicinedot.www.medicinedot.R;
import www.xcd.com.mylibrary.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

public class SettingUpPasswordActivity extends SimpleTopbarActivity {

    private EditText former_poasswod,new_poasswod,oknew_poasswod;
    private Button ok;

    @Override
    protected Object getTopbarTitle() {
        return R.string.updata_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settinguppassword);
        former_poasswod = (EditText) findViewById(R.id.former_poasswod);
        former_poasswod.setOnFocusChangeListener(this);

        new_poasswod = (EditText) findViewById(R.id.new_poasswod);
        new_poasswod.setOnFocusChangeListener(this);

        oknew_poasswod = (EditText) findViewById(R.id.oknew_poasswod);
        oknew_poasswod.setOnFocusChangeListener(this);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ok:
                String formerpoasswod  = former_poasswod.getText().toString().trim();
                if ("".equals(formerpoasswod)||formerpoasswod==null){
                    ToastUtil.showToast("原密码不能为空");
                    return;
                }
                String newpoasswod = new_poasswod.getText().toString().trim();
                if ("".equals(newpoasswod)||newpoasswod==null){
                    ToastUtil.showToast("新密码不能为空");
                    return;
                }
                if (newpoasswod.length()<6){
                    ToastUtil.showToast("新密码长度不能小于6位");
                    return;
                }
                String oknewpoasswod = oknew_poasswod.getText().toString().trim();

                if (!oknewpoasswod.equals(newpoasswod)){
                    ToastUtil.showToast("两次密码不相同");
                    return;
                }
                String uid= XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
                createDialogshow();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("uid", uid);
                params.put("oldpwd", formerpoasswod);
                params.put("newpwd", newpoasswod);
                okHttpPost(100, GlobalParam.UPPASSWORD, params);
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            switch (requestCode){
                case 100:
                    ToastUtil.showToast(returnMsg);
                    finish();
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
