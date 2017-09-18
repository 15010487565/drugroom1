package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.ChatSupplierActivity;

import www.xcd.com.mylibrary.func.BaseFunc;


/**
 * 设置
 *
 * @author litfb
 * @date 2014年10月8日
 * @version 1.0
 */
public class MeChatSupplierFunc extends BaseFunc {

	public MeChatSupplierFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.me_func_supplierchat;
	}

	@Override
	public int getFuncIcon() {
		return R.mipmap.me_func_supplierchat;
	}

	@Override
	public int getFuncName() {
		return R.string.me_func_supplierchat;
	}

	@Override
	public void onclick() {
		Intent intent = new Intent(getActivity(), ChatSupplierActivity.class);
		getActivity().startActivity(intent);
	}
//	@Override
//	public View initFuncView(boolean isSeparator, Object... params) {
//		View funcView = super.initFuncView(isSeparator, params);
//		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		params2.topMargin = (int) getActivity().getResources().getDimension(R.dimen.me_vertical_margin);
//		funcView.setLayoutParams(params2);
//		return funcView;
//	}
	@Override
	protected void initCustomView(LinearLayout customView) {
		super.initCustomView(customView);
		//创建保存布局参数的对象
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
						, LinearLayout.LayoutParams.WRAP_CONTENT);
		TextView textview=new TextView(getActivity());
		params.leftMargin = (int) getActivity().getResources().getDimension(R.dimen.margin_10);
		textview.setLayoutParams(params);//设置布局参数
		textview.setText("99+");
		textview.setPadding(5,5,5,5);
		textview.setBackgroundResource(R.drawable.shape_reddot);
		textview.setTextSize(12);
		textview.setGravity(Gravity.LEFT);
		textview.setTextColor(getActivity().getColor(R.color.white));
		customView.addView(textview);
	}
}
