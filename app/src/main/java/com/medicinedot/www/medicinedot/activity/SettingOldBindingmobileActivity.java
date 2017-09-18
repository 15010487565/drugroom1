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
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static com.medicinedot.www.medicinedot.activity.SettingBindingmobileActivity.INTENTMOBILE;


public class SettingOldBindingmobileActivity extends SimpleTopbarActivity {

    private EditText authcode_edit;
    private TextView oldmobile,authcode_button;
    private Button next;
    private TimeCount time;
    @Override
    protected Object getTopbarTitle() {
        return "填写验证码";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingoldbindingmobile);
        initView();
    }

    private void initView(){
        String phone = getIntent().getStringExtra("phone");
        oldmobile = (TextView) findViewById(R.id.oldmobile);
        oldmobile.setText("请输入"+phone+"收到的短信验证码");
        authcode_edit = (EditText) findViewById(R.id.authcode_edit);
        authcode_edit.setOnFocusChangeListener(this);
        authcode_button = (TextView) findViewById(R.id.authcode_button);
        authcode_button.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
    }
    private String code;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.authcode_button:
                String phone = XCDSharePreference.getInstantiation(this).getSharedPreferences("phone");
                createDialogshow();
                String deviceIdcode = ClassUtils.getDeviceId(this);
                Map<String, Object> paramscode = new HashMap<String, Object>();
                paramscode.put("vkey", deviceIdcode);
                paramscode.put("phone", phone);
                okHttpGet(100, GlobalParam.GETVERIFICATIONCODE, paramscode);
                time.start();
                break;
            case R.id.next:

                String authcodeedit = authcode_edit.getText().toString().trim();
                if ("".equals(authcodeedit)||authcodeedit==null){
                    ToastUtil.showToast("验证码不能为空");
                    return;
                }
                if (!code.equals(authcodeedit)){
                    ToastUtil.showToast("您输入的验证码有误！");
                    return;
                }
                startActivityForResult(new Intent(this, SettingNewBindingmobileActivity.class),INTENTMOBILE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case INTENTMOBILE:
                    String mobile = data.getStringExtra("MOBILE");
                    Intent intent = new Intent();
                    intent.putExtra("MOBILE",mobile);
                    this.setResult(Activity.RESULT_OK,intent);
                    finish();
                    break;

            }
        }
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200){
            switch (requestCode){
                case 100:
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
