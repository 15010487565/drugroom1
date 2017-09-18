package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.content.Intent;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.MeSettingActivity;

import www.xcd.com.mylibrary.func.BaseFunc;

/**
 * 会员中心
 *
 * Created by 1 on 2015/11/23.
 */
public class MeSettingSupplierFunc extends BaseFunc {

    public MeSettingSupplierFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.setting;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.setting;
    }

    @Override
    public int getFuncName() {
        return R.string.setting;
    }

    @Override
    public void onclick() {
        getActivity().startActivity(new Intent(getActivity(), MeSettingActivity.class));

    }
}
