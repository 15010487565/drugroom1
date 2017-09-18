package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.view.View;

import com.medicinedot.www.medicinedot.action.MeNVipSupplierAction;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.func.BaseTopTextViewFunc;


/**
 * Created by Android on 2017/5/15.
 */
public class MeYServiceTextTopBtnFunc extends BaseTopTextViewFunc {


    public MeYServiceTextTopBtnFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.setting;
    }
    /** 功能文本 */
    protected String getFuncText() {
        return "联系客服";
    }

    protected int getFuncTextRes() {
        return R.string.contactservice;
    }

    @Override
    public void onclick(View v) {
        ((MeNVipSupplierAction)getActivity()).getRelationService();
    }
}
