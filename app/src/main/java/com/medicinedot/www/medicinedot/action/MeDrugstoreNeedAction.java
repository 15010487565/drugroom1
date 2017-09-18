package com.medicinedot.www.medicinedot.action;

import android.os.Bundle;
import android.widget.EditText;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.entity.GlobalParam;
import com.medicinedot.www.medicinedot.func.MeDrugsroteneedTextTopBtnFunc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static www.xcd.com.mylibrary.utils.XCDSharePreference.getInstantiation;

public class MeDrugstoreNeedAction extends SimpleTopbarActivity {

    private EditText drugstoreneed_content;
    private String drugstoreneedcontent;
    private static Class<?> rightFuncArray[] = {MeDrugsroteneedTextTopBtnFunc.class};

    @Override
    protected Object getTopbarTitle() {
        return R.string.me_func_drugstoreneed;
    }

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medrugstoreneedaction);
        String content = XCDSharePreference.getInstantiation(this).getSharedPreferences("content");
        drugstoreneed_content = (EditText) findViewById(R.id.drugstoreneed_content);
        drugstoreneed_content.setText(content);
    }

    public void getDrugsroteneed(){
        drugstoreneedcontent = drugstoreneed_content.getText().toString().trim();
        if (drugstoreneedcontent==null||"".equals(drugstoreneedcontent)){
            ToastUtil.showToast("药品需求不能为空!");
            return;
        }
        String uid = getInstantiation(this).getSharedPreferences("uid");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("content", drugstoreneedcontent);
        okHttpImgPost(100, GlobalParam.PERFECT, params);
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200){
            switch (requestCode){
                case 100:
                    getInstantiation(this).setSharedPreferences("content", drugstoreneedcontent);
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
