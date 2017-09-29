package com.medicinedot.www.medicinedot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;

import static www.xcd.com.mylibrary.utils.XCDSharePreference.getInstantiation;


public class SettingBindingmobileActivity extends SimpleTopbarActivity {

    public final static int INTENTMOBILE = 0;
    private TextView settingbindinghead_phone;
    private Button updatamobile;
    private ImageView settingbindinghead_head;
    @Override
    protected Object getTopbarTitle() {
        return R.string.bindingmobile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingbindingmobile);
        initView();
    }
    private String phone;
    private void initView(){
        phone = getIntent().getStringExtra("phone");
        //头像
        settingbindinghead_head = (ImageView) findViewById(R.id.settingbindinghead_head);
        //已绑定的手机号
        settingbindinghead_phone = (TextView) findViewById(R.id.settingbindinghead_phone);
        settingbindinghead_phone.setText(phone);
        updatamobile = (Button) findViewById(R.id.updatamobile);
        updatamobile.setOnClickListener(this);
        //加载圆形头像
        String headimg = getInstantiation(this).getSharedPreferences("headimg");
        try {
            Glide.with(this)
                    .load(GlobalParam.IP+headimg)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.upload_image_side)
                    .error(R.mipmap.upload_image_side)
                    .into(settingbindinghead_head);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.updatamobile:
                Intent intent = new Intent(this, SettingOldBindingmobileActivity.class);
                intent.putExtra("phone",phone);
                startActivityForResult(intent,INTENTMOBILE);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case INTENTMOBILE:
                    String mobile = data.getStringExtra("MOBILE");
                    settingbindinghead_phone.setText(mobile);
                    break;

            }
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200){
            switch (requestCode){
                case 100:

                    break;
            }
        }
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
