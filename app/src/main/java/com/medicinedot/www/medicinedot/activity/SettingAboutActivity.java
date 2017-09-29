package com.medicinedot.www.medicinedot.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.SettingAboutInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

public class SettingAboutActivity extends SimpleTopbarActivity {

    private ImageView head;
    private TextView versions,about,phone;

    @Override
    protected Object getTopbarTitle() {
        return R.string.aboutme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingabout);
        head = (ImageView) findViewById(R.id.head);
        versions = (TextView) findViewById(R.id.versions);
        about = (TextView) findViewById(R.id.about);
        phone = (TextView) findViewById(R.id.phone);
        phone.setOnClickListener(this);
        String uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
        createDialogshow();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(100, GlobalParam.ABOUNT, params);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.phone:
                String mobile = phone.getText().toString().trim();
                if (mobile ==null&&"".equals(mobile)){
                    ToastUtil.showToast("未查询到有可用联系方式!");
                    return;
                }
                ClassUtils.call(this,mobile,true);
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            switch (requestCode){
                case 100:
                    try {
                        SettingAboutInfo info = JSON.parseObject(returnData,SettingAboutInfo.class);
                        List<SettingAboutInfo.DataBean> data = info.getData();
                        if (data!=null&&data.size()>0){
                            for (int i = 0; i < data.size(); i++) {
                                SettingAboutInfo.DataBean dataBean = data.get(i);
                                versions.setText(getVersionName());
                                about.setText(dataBean.getContent());
                                phone.setText(dataBean.getPhone());
                                try {
                                    Glide.with(this)
                                            .load(GlobalParam.IP+dataBean.getImage())
                                            .centerCrop()
                                            .crossFade()
                                            .transform(new GlideCircleTransform(this))
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .placeholder(R.mipmap.defaulthead)
                                            .error(R.mipmap.defaulthead)
                                            .into(head);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }else {
            ToastUtil.showToast(returnMsg);
        }
    }
    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
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
