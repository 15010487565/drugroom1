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


public class AccountGenderFunc extends BaseFunc {

    public AccountGenderFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.me_account_gender;
    }

    @Override
    public int getFuncIcon() {
        return 0;
    }

    @Override
    public int getFuncName() {
        return R.string.gender;
    }

    @Override
    public void onclick() {
        ((MeSupplierInfoActivity) getActivity()).getGenderInfo();
    }

    @Override
    public View initFuncView(boolean isSeparator, Object... params) {
        View funcView = super.initFuncView(true, params);
        return funcView;
    }
    TextView textview;
    @Override
    protected void initCustomView(LinearLayout customView) {
        super.initCustomView(customView);
        //创建保存布局参数的对象
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT);
        textview = new TextView(getActivity());
        textview.setLayoutParams(params);//设置布局参数
        String sex = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("sex");
        if ("1".equals(sex)){
            resetRightGender("男");
        }else if ("2".equals(sex)){
            resetRightGender("女");
        }else {
            resetRightGender("男");
        }
        textview.setTextSize(18);
        textview.setGravity(Gravity.RIGHT);
        textview.setTextColor(getActivity().getResources().getColor(R.color.black_66));
        customView.addView(textview);
    }
    public void resetRightGender(String Gender){
        textview.setText(Gender);
    }
    public String getresetRightGender() {
        return textview.getText().toString();
    }
}
