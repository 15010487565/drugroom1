package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.MeSupplierInfoActivity;

import www.xcd.com.mylibrary.func.BaseFunc;
import www.xcd.com.mylibrary.utils.XCDSharePreference;


public class AccountRegionFunc extends BaseFunc {

    TextView textview;
    public AccountRegionFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.me_account_region;
    }

    @Override
    public int getFuncIcon() {
        return 0;
    }

    @Override
    public int getFuncName() {
        return R.string.me_account_region;
    }

    @Override
    public void onclick() {
        ((MeSupplierInfoActivity)getActivity()).selectRegion();
    }

    @Override
    public View initFuncView(boolean isSeparator, Object... params) {
        View funcView = super.initFuncView(true, params);
        return funcView;
    }

    @Override
    protected void initCustomView(LinearLayout customView) {
        super.initCustomView(customView);
        //创建保存布局参数的对象
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT);
        textview = new TextView(getActivity());
        params.leftMargin = (int) getActivity().getResources().getDimension(R.dimen.height_25);
        textview.setLayoutParams(params);//设置布局参数
        textview.setTextSize(18);
        textview.setGravity(Gravity.RIGHT);
        textview.setTextColor(getActivity().getResources().getColor(R.color.black_66));
        textview.setLines(1);
        customView.addView(textview);
        String region = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("region");
        refreshRigion(region);
    }
    public void refreshRigion(String refresh){
        textview.setText(refresh);
    }
    public String getTextString(){
        return textview.getText().toString().trim();
    }
}
