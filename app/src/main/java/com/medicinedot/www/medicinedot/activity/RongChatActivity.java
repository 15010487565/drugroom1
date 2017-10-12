package com.medicinedot.www.medicinedot.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.RongYunUserInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;

import static com.medicinedot.www.medicinedot.R.id.chat_number;
import static www.xcd.com.mylibrary.utils.ClassUtils.REQUEST_CODE_ASK_CALL_PHONE;
import static www.xcd.com.mylibrary.utils.XCDSharePreference.context;

/**
 * Created by Android on 2017/8/21.
 */

public class RongChatActivity  extends SimpleTopbarActivity {

    private ImageView titleimage;
    private TextView chatname,chatnumber,chatlocation,chatcontext;
    private LinearLayout allchatinfo,linear_location;
//    private LinearLayout linear_number;
    @Override
    protected Object getTopbarTitle() {
        return "消息";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        String title = getIntent().getData().getQueryParameter("title");
        resetTopbarTitle(title);
        String targetId = getIntent().getData().getQueryParameter("targetId");
        Log.e("TAG_单聊","targetId="+targetId);
        allchatinfo = (LinearLayout) findViewById(R.id.allchatinfo);
//        linear_number = (LinearLayout) findViewById(R.id.linear_number);
//        String utype = XCDSharePreference.getInstantiation(this).getSharedPreferences("utype");
//        if ("2".equals(utype)){
//            linear_number.setVisibility(View.GONE);
//        }else {
//            linear_number.setVisibility(View.VISIBLE);
//        }
        linear_location = (LinearLayout) findViewById(R.id.linear_location);
        titleimage = (ImageView) findViewById(R.id.titleimage);
        chatname = (TextView) findViewById(R.id.chat_name);
        chatnumber = (TextView) findViewById(chat_number);
        chatnumber.setOnClickListener(this);
        chatlocation = (TextView) findViewById(R.id.chat_location);
        chatcontext = (TextView) findViewById(R.id.chat_context);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", targetId);
        okHttpGet(100, GlobalParam.GETUSERINFOFORID, params);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.chat_number:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CODE_ASK_CALL_PHONE);
                } else {
                    ClassUtils.callDirectly(this, chatnumber.getText().toString().trim(), true);
                }
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200) {
            allchatinfo.setVisibility(View.VISIBLE);
            switch (requestCode){
                case 100:
                    RongYunUserInfo result = JSON.parseObject(returnData, RongYunUserInfo.class);
                    RongYunUserInfo.DataBean userdata = result.getData();
                    String utype = userdata.getUtype();
                    String nickname = userdata.getName();
                    chatname.setText(nickname ==null?"":nickname);
                    String phone = userdata.getPhone().trim();
//                    if ("2".equals(utype)){
//                        chatnumber.setText("");
//                        linear_number.setVisibility(View.GONE);
//                    }else {
                        chatnumber.setText(phone);
                        chatnumber.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
                        chatnumber.getPaint().setAntiAlias(true);//抗锯齿
//                        linear_number.setVisibility(View.VISIBLE);
//                    }
                    String region = userdata.getRegion().trim();
                    if (TextUtils.isEmpty(region)){
                        chatlocation.setText("");
                        linear_location.setVisibility(View.GONE);
                    }else {
                        chatlocation.setText(region);
                        linear_location.setVisibility(View.VISIBLE);
                    }
                    String content = userdata.getContent().trim();
                    String homecontext = "";
                    if (TextUtils.isEmpty(content)){
                        if ("1".equals(utype)){
                            homecontext = "个人简介：暂无";
                        }else {
                            homecontext = "药品需求：暂无";
                        }
                    }else {
                        if ("1".equals(utype)){
                            homecontext = "个人简介："+content;
                        }else {
                            homecontext = "药品需求："+content;
                        }
                    }
                    SpannableString styledText = new SpannableString(homecontext);
                    styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_66), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_66_), 5, homecontext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    chatcontext.setText(styledText, TextView.BufferType.SPANNABLE);
                    String image_head = userdata.getHeadimg();
                    try {
                        Glide.with(this)
                                .load(GlobalParam.IP+image_head)
                                .centerCrop()
                                .crossFade()
                                .transform(new GlideCircleTransform(this))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.mipmap.upload_image_side)
                                .error(R.mipmap.upload_image_side)
                                .into(titleimage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }else {
            allchatinfo.setVisibility(View.GONE);
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
