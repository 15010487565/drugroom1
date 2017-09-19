package com.medicinedot.www.medicinedot.activity;

import android.os.Bundle;

import com.medicinedot.www.medicinedot.R;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

/**
 * Created by Android on 2017/8/21.
 */

public class RongChatActivity  extends SimpleTopbarActivity {

    @Override
    protected Object getTopbarTitle() {
        return "消息";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        String title = getIntent().getData().getQueryParameter("title");
        resetTopbarTitle(title);
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
