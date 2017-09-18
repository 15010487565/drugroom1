package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.action.MeNVipSupplierAction;
import com.medicinedot.www.medicinedot.action.MeYVipSupplierAction;

import www.xcd.com.mylibrary.func.BaseFunc;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

/**
 * 会员中心
 *
 * Created by 1 on 2015/11/23.
 */
public class MeVipSupplierFunc extends BaseFunc {

    private TextView textview;

    public MeVipSupplierFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.me_func_vipsupplier;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.me_func_vipsupplier;
    }

    @Override
    public int getFuncName() {
        return R.string.me_func_vipsupplier;
    }

    @Override
    public void onclick() {
        String is_member = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("is_member");
        Log.e("TAG_","is_member="+is_member);
        if ("1".equals(is_member)){
            getActivity().startActivity(new Intent(getActivity(), MeYVipSupplierAction.class));
        }else {
            getActivity().startActivity(new Intent(getActivity(), MeNVipSupplierAction.class));
        }
    }

    @Override
    protected void initCustomView(LinearLayout customView) {
        super.initCustomView(customView);
        //创建保存布局参数的对象
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT);
        textview=new TextView(getActivity());
        textview.setLayoutParams(params);//设置布局参数
        textview.setTextSize(16);
        textview.setGravity(Gravity.RIGHT);
        textview.setTextColor(getActivity().getColor(R.color.top_bar_background));
        customView.addView(textview);
    }
    public void getMeVIPTime(String time){
        textview.setText(time);
    }
}
