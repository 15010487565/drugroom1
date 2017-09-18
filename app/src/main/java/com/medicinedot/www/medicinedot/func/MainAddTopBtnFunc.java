package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.medicinedot.www.medicinedot.R;

import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;

public class MainAddTopBtnFunc extends BaseTopImageBtnFunc {

	public MainAddTopBtnFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.main_topbar_add;
	}

	@Override
	public int getFuncIcon() {
		return R.mipmap.ic_launcher;
	}

	@Override
	public void onclick(View v) {
		Log.e("TAG_","DIA");
//		getActivity().startActivity(new Intent(getActivity(),SettingActivity.class));
	}

}
