package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.view.View;

import com.medicinedot.www.medicinedot.activity.SingleUpInfoActivity;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.func.BaseTopTextViewFunc;


/**
 * Created by Android on 2017/5/15.
 */
public class SingleUpDataSaveTextTopBtnFunc extends BaseTopTextViewFunc {


    public SingleUpDataSaveTextTopBtnFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.me_func_drugstoreneedcompile;
    }
    /** 功能文本 */
    protected String getFuncText() {
        return "确定";
    }

    protected int getFuncTextRes() {
        return R.string.ok;
    }

    @Override
    public void onclick(View v) {
        ((SingleUpInfoActivity)getActivity()).getSave();
    }
}
