package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.SingleUpInfoActivity;

import www.xcd.com.mylibrary.func.BaseFunc;
import www.xcd.com.mylibrary.utils.XCDSharePreference;


public class AccountNameFunc extends BaseFunc {

    public AccountNameFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.me_account_name;
    }

    @Override
    public int getFuncIcon() {
        return 0;
    }

    @Override
    public int getFuncName() {
        return R.string.name;
    }

    @Override
    public void onclick() {
        Intent intent = new Intent(getActivity(),SingleUpInfoActivity.class);
        intent.putExtra("title","姓名");
        String namefunc = "";
        if (textview!=null&&(namefunc= textview.getText().toString().trim())!=null){
            intent.putExtra("hintcontent",namefunc);
        }else {
            intent.putExtra("hintcontent","请输入您的姓名");
        }
        getActivity().startActivityForResult(intent,1);
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
        String name = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("name");
        resetRightName(name);
        textview.setTextSize(18);
        textview.setGravity(Gravity.RIGHT);
        textview.setTextColor(getActivity().getResources().getColor(R.color.black_66));
        customView.addView(textview);
    }
    public void resetRightName(String name){
        textview.setText(name);
    }
}
