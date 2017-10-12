package com.medicinedot.www.medicinedot.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.UpResumeInfo;
import com.medicinedot.www.medicinedot.func.MeDruginfoSaveTextTopBtnFunc;
import com.medicinedot.www.medicinedot.threelevelganged.ArrayWheelAdapter;
import com.medicinedot.www.medicinedot.threelevelganged.BaseThreeActivity;
import com.medicinedot.www.medicinedot.threelevelganged.OnWheelChangedListener;
import com.medicinedot.www.medicinedot.threelevelganged.WheelView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static www.xcd.com.mylibrary.utils.XCDSharePreference.getInstantiation;

public class MeDrugstoreInfoActivity extends BaseThreeActivity implements View.OnClickListener, OnWheelChangedListener {

    private LinearLayout head_linear;
    private TextView briefintroduction_text;
    private LinearLayout address_select;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private TextView btn_confirm, btn_off;
    private ImageView head;
    private TextView address, detailedness_address;
    private static Class<?> rightFuncArray[] = {MeDruginfoSaveTextTopBtnFunc.class};

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected Object getTopbarTitle() {
        return "药店信息";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medrugstoreinfo);
    }

    @Override
    protected void addViewToLeftFunctionZone() {
        super.addViewToLeftFunctionZone();
        address_select = (LinearLayout) findViewById(R.id.address_select);
        //
        btn_confirm = (TextView) findViewById(R.id.btn_confirm);
        btn_off = (TextView) findViewById(R.id.btn_off);
        btn_confirm.setOnClickListener(this);
        btn_off.setOnClickListener(this);

        briefintroduction_text = (TextView) findViewById(R.id.briefintroduction_text);
        briefintroduction_text.setOnClickListener(this);
        String name = getInstantiation(this).getSharedPreferences("name");
        briefintroduction_text.setText(name);
        head_linear = (LinearLayout) findViewById(R.id.head_linear);
        head_linear.setOnClickListener(this);
        head = (ImageView) findViewById(R.id.head);
        address = (TextView) findViewById(R.id.address);
        String strregion = getInstantiation(this).getSharedPreferences("region");
        address.setText(strregion);
        //禁止药店切换城市
//        address.setOnClickListener(this);
        detailedness_address = (TextView) findViewById(R.id.detailedness_address);
        detailedness_address.setOnClickListener(this);
        String straddress = getInstantiation(this).getSharedPreferences("address");
        detailedness_address.setText(straddress);
        String headimg = getInstantiation(this).getSharedPreferences("headimg");
        uploadHead(GlobalParam.IP+headimg);
        setUpViews();
        setUpListener();
        setUpData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.head_linear://个人简介
                getChoiceDialog().show();
                break;
            case R.id.address:
                selectRegion();
                break;
            case R.id.detailedness_address:
                String addresshint = detailedness_address.getText().toString().trim();
                intent = new Intent(this, MultUpInfoActivity.class);
                intent.putExtra("title", "详细地址");
                if (addresshint ==null||"".equals(addresshint)){
                    intent.putExtra("hintaddress",true);
                    intent.putExtra("hint",true);
                    intent.putExtra("hintcontent", "请输入详细地址");
                }else {
                    intent.putExtra("hintaddress",true);
                    intent.putExtra("hint",false);
                    intent.putExtra("hintcontent", addresshint);
                }
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_confirm:
                address_select.setVisibility(View.GONE);
                address.setText(mCurrentProviceName + "-" + mCurrentCityName);
                break;
            case R.id.btn_off:
                address_select.setVisibility(View.GONE);
                break;
            case R.id.briefintroduction_text:
                String name = briefintroduction_text.getText().toString().trim();
                intent = new Intent(this, SingleUpInfoActivity.class);
                intent.putExtra("title", "店铺名称");
                if (name ==null||"".equals(name)){
                    intent.putExtra("hintcontent", "请输入店铺名称");
                }else {
                    intent.putExtra("hintcontent", name);
                }
                startActivityForResult(intent, 0);
                break;
        }
    }
    private String name;
    private String region;
    private String detailednessaddress;
    public void getSave() {
        String uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
        name = briefintroduction_text.getText().toString();
        region = address.getText().toString();
        detailednessaddress = detailedness_address.getText().toString();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("name", name==null?"":name);//店铺名称
        params.put("region", region==null?"":region);//所在地区
        params.put("address", detailednessaddress==null?"":detailednessaddress);//详细地址
        if (image_head !=null&&!"".equals(image_head)){
            params.put("headimg", image_head);
        }
        okHttpImgPost(100, GlobalParam.PERFECT, params);
    }
    private String image_head;
    @Override
    public void uploadImage(List<File> list) {
        super.uploadImage(list);
        // 调用上传
        for (File imagepath : list) {
           image_head = imagepath.getPath();
            uploadHead(image_head);
        }
    }

    private void uploadHead(String imageurl) {
        try {
            Glide.with(this)
                    .load(imageurl)
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

    //选择地区
    public void selectRegion() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(address.getWindowToken(), 0); //强制隐藏键盘
        if (mProvinceDatas ==null){
            boolean permissions = checkupPermissions(WRITEREADPERMISSIONS);
            if (permissions){
                setUpData();
            }
        }else {
            address_select.setVisibility(View.VISIBLE);
        }
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        if (mProvinceDatas !=null){
            updateCities();
            updateAreas();
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
//        Log.e("TAG_","pCurrent="+pCurrent);
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        mViewDistrict.setVisibility(View.VISIBLE);
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);

        if (mDistrictDatasMap ==null||mDistrictDatasMap.size()==0){
            mViewDistrict.setVisibility(View.GONE);
        }else {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        String[] cities = null;
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        if (mCitisDatasMap ==null){
            cities = new String[]{""};
        }else {
            cities = mCitisDatasMap.get(mCurrentProviceName);
            if (cities == null) {
                cities = new String[]{""};
            }
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public boolean getIsShowChoiceDialog() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String hintcontent = "";
            switch (requestCode) {
                case 0:
                    hintcontent = data.getStringExtra("hintcontent");
                    briefintroduction_text.setText(hintcontent);
                    break;
                case 1:
                    hintcontent = data.getStringExtra("hintcontent");
                    detailedness_address.setText(hintcontent);
                    break;
            }
        }
    }


    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            switch (requestCode){
                case 100:
                    UpResumeInfo upResumeInfo = JSON.parseObject(returnData,UpResumeInfo.class);
                    UpResumeInfo.DataBean data = upResumeInfo.getData();
                    String headimg = data.getHeadimg();
                    if (headimg !=null&&!"".equals(headimg)){
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("headimg", headimg);
                    }
                    XCDSharePreference.getInstantiation(this).setSharedPreferences("name", name);
                    XCDSharePreference.getInstantiation(this).setSharedPreferences("region", region);
                    XCDSharePreference.getInstantiation(this).setSharedPreferences("address", detailednessaddress);
                    this.setResult(Activity.RESULT_OK);
                    finish();
                    break;
            }
        }
        ToastUtil.showToast(returnMsg);

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


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

}
