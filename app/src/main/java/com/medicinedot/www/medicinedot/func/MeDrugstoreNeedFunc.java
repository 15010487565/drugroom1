package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.content.Intent;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.action.MeDrugstoreNeedAction;

import www.xcd.com.mylibrary.func.BaseFunc;

/**
 * 会员中心
 *
 * Created by 1 on 2015/11/23.
 */
public class MeDrugstoreNeedFunc extends BaseFunc {

    public MeDrugstoreNeedFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.me_func_vipsupplier;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.me_func_drugstoreneed;
    }

    @Override
    public int getFuncName() {
        return R.string.me_func_drugstoreneed;
    }

    @Override
    public void onclick() {
        getActivity().startActivity(new Intent(getActivity(), MeDrugstoreNeedAction.class));

    }
}
