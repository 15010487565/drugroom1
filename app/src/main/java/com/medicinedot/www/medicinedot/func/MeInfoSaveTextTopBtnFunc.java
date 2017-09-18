package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.view.View;

import com.medicinedot.www.medicinedot.activity.MeSupplierInfoActivity;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.func.BaseTopTextViewFunc;


/**
 * Created by Android on 2017/5/15.
 */
public class MeInfoSaveTextTopBtnFunc extends BaseTopTextViewFunc {


    public MeInfoSaveTextTopBtnFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.setting;
    }

    /**
     * 功能文本
     */
    protected String getFuncText() {
        return "保存";
    }

    protected int getFuncTextRes() {
        return R.string.save;
    }

    @Override
    public void onclick(View v) {
        ((MeSupplierInfoActivity) getActivity()).getSaveInfo();
    }
}
