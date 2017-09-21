package com.medicinedot.www.medicinedot.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.medicinedot.www.medicinedot.entity.Config;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;


/**
 * 成功后回调也闪烁，将包名wxapi修改包名为wxap，暂时不执行回调页
 */
public class WXPayEntryActivity extends SimpleTopbarActivity implements IWXAPIEventHandler {
	
	private static final String TAG = "TAG_";

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Config.APP_ID);
        api.handleIntent(getIntent(), this);
    }
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {

		if(resp.errCode==0){
			ToastUtil.showToast("支付成功!");
		}else if(resp.errCode==-1){
			ToastUtil.showToast("支付失败!");
		}else if(resp.errCode==-2){
			ToastUtil.showToast("取消支付!");
		}
		Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
		finish();
	}

	@Override
	public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

	}

	@Override
	public void onCancelResult() {

	}

	@Override
	public void onErrorResult(int errorCode, IOException errorExcep) {

	}

	@Override
	public void onParseErrorResult(int errorCode) {

	}

	@Override
	public void onFinishResult() {

	}
}