package com.medicinedot.www.medicinedot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.func.SingleUpDataSaveTextTopBtnFunc;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;

public class SingleUpInfoActivity extends SimpleTopbarActivity {

    private EditText updata_edit;
    private String hintcontent;
    private static Class<?> rightFuncArray[] = {SingleUpDataSaveTextTopBtnFunc.class};
    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singupinfo);
        String title = getIntent().getStringExtra("title");
        resetTopbarTitle(title);
        hintcontent = getIntent().getStringExtra("hintcontent");
        updata_edit = (EditText) findViewById(R.id.updata_edit);
        updata_edit.setOnFocusChangeListener(this);
        updata_edit.setHint(hintcontent);
    }
    public void getSave(){
        String trim = updata_edit.getText().toString().trim();
        if (trim == null||"".equals(trim)){
            ToastUtil.showToast("您输入的"+hintcontent+"不能为空");
        }else {
            Intent intent = new Intent();
            intent.putExtra("hintcontent",trim);
            this.setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

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
