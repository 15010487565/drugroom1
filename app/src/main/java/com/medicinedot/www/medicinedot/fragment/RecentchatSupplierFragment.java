package com.medicinedot.www.medicinedot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.medicinedot.www.medicinedot.R;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;


/**
 * Created by Android on 2017/9/5.
 */
public class RecentchatSupplierFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected Object getTopbarTitle() {
        return R.string.recentchat;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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