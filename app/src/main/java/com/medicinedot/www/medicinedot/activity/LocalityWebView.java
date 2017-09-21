package com.medicinedot.www.medicinedot.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.medicinedot.www.medicinedot.R;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class LocalityWebView extends SimpleTopbarActivity {

    private WebView webview;

    @Override
    protected Object getTopbarTitle() {
        return "用户协议";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locality_web_view);
        webview = (WebView) findViewById(R.id.web);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("url");
        Log.e("TAG_","stringExtra="+stringExtra);
        WebSettings setting = webview.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDefaultTextEncodingName("utf-8");
        setting.setAllowFileAccess(true);// 设置允许访问文件数据
        setting.setSupportZoom(true);// 支持放大网页功能
//        setting.setBuiltInZoomControls(true);// 支持缩小网页功能
        setting.setBlockNetworkImage(false);
        //自适应屏幕  
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//允许混合内容，5.0之后的api
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.clearCache(true);
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        webview.requestFocusFromTouch();
        webview.loadUrl(stringExtra);
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

