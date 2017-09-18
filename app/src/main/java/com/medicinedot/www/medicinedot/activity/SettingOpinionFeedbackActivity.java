package com.medicinedot.www.medicinedot.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

public class SettingOpinionFeedbackActivity extends SimpleTopbarActivity {

    private EditText opinion;
    private Button submit;
    @Override
    protected Object getTopbarTitle() {
        return R.string.opinionfeedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingopinionfeedback);
        opinion = (EditText) findViewById(R.id.opinion);
        opinion.setOnFocusChangeListener(this);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.submit:
                String opinionstr = opinion.getText().toString().trim();
                if (opinionstr == null||"".equals(opinionstr)){
                    ToastUtil.showToast("反馈内容不能为空!");
                    return;
                }
                String uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
                createDialogshow();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("uid", uid);
                params.put("content", opinionstr);
                okHttpPost(100, GlobalParam.OPINIONFEEDBACK, params);
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            switch (requestCode){
                case 100:
                    ToastUtil.showToast("提交成功，您的宝贵意见是我们进步的源泉！");
                    finish();
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
