package com.medicinedot.www.medicinedot.func;

import android.app.Activity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.MeSupplierInfoActivity;

import www.xcd.com.mylibrary.func.BaseFunc;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

/**
 * 头像
 * 
 * @author litfb
 * @date 2014年10月16日
 * @version 1.0
 */
public class AccountHeadFunc extends BaseFunc{

	ImageView imageView;

	public AccountHeadFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.me_account_head;
	}

	@Override
	public int getFuncIcon() {
		return 0;
	}

	@Override
	public int getFuncName() {
		return R.string.head;
	}

	@Override
	public void onclick() {
		((MeSupplierInfoActivity)getActivity()).upHeadImage();
	}
	@Override
	protected void initCustomView(LinearLayout customView) {
		super.initCustomView(customView);
		//创建保存布局参数的对象
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
						, LinearLayout.LayoutParams.WRAP_CONTENT);
		imageView=new ImageView(getActivity());
		int maxhw = (int) getActivity().getResources().getDimension(R.dimen.height_35);
		params.height = maxhw;
		params.width = maxhw;
		imageView.setLayoutParams(params);//设置布局参数
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片自动缩放
		customView.setGravity(Gravity.RIGHT);
		customView.addView(imageView);
		String headimg = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("headimg");
		refreshHead(headimg);
	}
	public void refreshHead(String headurl){
		Glide.with(getActivity())
				.load(headurl)
				.centerCrop()
				.crossFade()
				.transform(new GlideCircleTransform(getActivity()))
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.mipmap.defaulthead)
				.error(R.mipmap.defaulthead)
				.into(imageView);
	}
}
