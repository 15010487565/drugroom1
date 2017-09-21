package com.medicinedot.www.medicinedot.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.UpResumeInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;
import com.medicinedot.www.medicinedot.func.AccountGenderFunc;
import com.medicinedot.www.medicinedot.func.AccountHeadFunc;
import com.medicinedot.www.medicinedot.func.AccountNameFunc;
import com.medicinedot.www.medicinedot.func.AccountRegionFunc;
import com.medicinedot.www.medicinedot.func.MeInfoSaveTextTopBtnFunc;
import com.medicinedot.www.medicinedot.threelevelganged.ArrayWheelAdapter;
import com.medicinedot.www.medicinedot.threelevelganged.BaseThreeActivity;
import com.medicinedot.www.medicinedot.threelevelganged.OnWheelChangedListener;
import com.medicinedot.www.medicinedot.threelevelganged.WheelView;
import com.medicinedot.www.medicinedot.view.SelectGenderPopWin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.func.BaseFunc;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

public class MeSupplierInfoActivity extends BaseThreeActivity
        implements View.OnClickListener,OnWheelChangedListener ,RadioGroup.OnCheckedChangeListener{
    /**
     * 功能对象
     */
    private Hashtable<Integer, BaseFunc> htFunc = new Hashtable<Integer, BaseFunc>();
    /**
     * 自定义功能View
     */
    private LinearLayout mainFuncView;
    /**
     * 自定义功能列表
     */
    private LinearLayout mainFuncList;
    /**
     * 系统功能View
     */
    private LinearLayout subFuncView;
    /**
     * 系统功能列表
     */
    private LinearLayout subFuncList;
    //个人简介
    private TextView briefintroduction;
    private LinearLayout meinfomain_linear;
    private LinearLayout address_select;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView btn_confirm,btn_off;
    private static Class<?> rightFuncArray[] = {MeInfoSaveTextTopBtnFunc.class};
    private static Class<?> mainFuncArray[] = {
            AccountHeadFunc.class,
            AccountNameFunc.class,
            AccountGenderFunc.class,
            AccountRegionFunc.class
    };

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected Object getTopbarTitle() {
        return "个人信息";
    }
    protected Class<?>[] getMainFuncArray() {
        return mainFuncArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesupplierinfo);
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

        meinfomain_linear = (LinearLayout) findViewById(R.id.meinfomain_linear);
        briefintroduction = (TextView) findViewById(R.id.briefintroduction);
        briefintroduction.setOnClickListener(this);
        String content = XCDSharePreference.getInstantiation(this).getSharedPreferences("content");
        briefintroduction.setText(content);
        mainFuncView = (LinearLayout) findViewById(R.id.account_main_view);
        mainFuncList = (LinearLayout) findViewById(R.id.account_main_list);
        subFuncView = (LinearLayout) findViewById(R.id.account_sub_view);
        subFuncList = (LinearLayout) findViewById(R.id.account_sub_list);
        // 初始化主功能
        initMainFunc();
        setUpViews();
        setUpListener();
        setUpData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()){
            case R.id.briefintroduction://个人简介
                intent = new Intent(this,MultUpInfoActivity.class);
                intent.putExtra("title","个人简介");
                intent.putExtra("hintcontent","个人简介");
                intent.putExtra("hintshowcontent","填写您的个人简介，可以增加药店主动联系您的机会呦！");
                startActivityForResult(intent,3);
                break;
            case R.id.btn_confirm:
                address_select.setVisibility(View.GONE);
                AccountRegionFunc regionfunc = (AccountRegionFunc) htFunc.get(R.id.me_account_region);
                regionfunc.refreshRigion(mCurrentProviceName + "-" + mCurrentCityName
//                        +mCurrentDistrictName
                );
                break;
            case R.id.btn_off:
                address_select.setVisibility(View.GONE);
                break;

            default:
                // func
                BaseFunc func = htFunc.get(v.getId());
                // 处理点击事件
                if (func != null) {
                    func.onclick();
                }
                break;
        }
    }
    //更换头像
    public void upHeadImage(){
        getChoiceDialog().show();
    }
    //选择地区
    public void selectRegion(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(meinfomain_linear.getWindowToken(), 0); //强制隐藏键盘
        address_select.setVisibility(View.VISIBLE);
    }
    private String image_head ="";
    @Override
    public void uploadImage(List<File> list) {
        super.uploadImage(list);
        // 调用上传
        for (File imagepath : list) {
            image_head = imagepath.getPath();
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("file", imagepath);
//            params.put("type", "user");
//            params.put("user_id", userid);
//            okHttpImgPost(101, GlobalParam.UPIMAGE, params);
            AccountHeadFunc func = (AccountHeadFunc) htFunc.get(R.id.me_account_head);
            func.refreshHead(image_head);
        }
    }
    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mViewDistrict.setVisibility(View.GONE);
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
        updateCities();
        updateAreas();
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
    /**
     * 初始化主功能
     */
    protected void initMainFunc() {
        Class<?>[] mainFuncs = getMainFuncArray();
        // 功能列表为空,隐藏区域
        if (mainFuncs == null || mainFuncs.length == 0) {
            mainFuncView.setVisibility(View.GONE);
            return;
        }
        // 初始化功能
        boolean isSeparator = false;
        for (Class<?> mainFunc : mainFuncs) {
            // view
            View funcView = getFuncView(getLayoutInflater(), mainFunc, isSeparator);
            if (funcView != null) {
                // 点击事件
                funcView.setOnClickListener(this);
                // 加入页面
                mainFuncList.addView(funcView);
                isSeparator = true;
            }
        }
        // 设置列表显示
        mainFuncList.setVisibility(View.VISIBLE);
    }
    /**
     * 获得功能View
     *
     * @param inflater
     * @return
     */
    private View getFuncView(LayoutInflater inflater, Class<?> funcClazz, boolean isSeparator) {
        BaseFunc func = BaseFunc.newInstance(funcClazz, this);
        if (func == null) {
            return null;
        }
        htFunc.put(func.getFuncId(), func);
        // view
        View funcView = func.initFuncView(isSeparator);
        return funcView;
    }
    //点击保存
    public void getSaveInfo() {
        String uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        if (!"".equals(nameright)){
            params.put("name", nameright);//姓名
        }
        if ("".equals(selectGender)||"男".equals(selectGender)){
            params.put("sex", "1");//性别
        }else {
            params.put("sex", "2");//性别
        }
        AccountRegionFunc regionfunc = (AccountRegionFunc) htFunc.get(R.id.me_account_region);
        String textString = regionfunc.getTextString();
        if ("".equals(textString)||textString==null){
            ToastUtil.showToast("所在地区不能为空!");
            return;

        }else {
            params.put("region", textString);//所在地区
        }
        String content = briefintroduction.getText().toString().trim();
        if (!"".equals(content)){
            params.put("content", content);//姓名
        }

        if (image_head !=null&&!"".equals(image_head)){
            params.put("headimg", image_head);
        }
        okHttpImgPost(100, GlobalParam.PERFECT, params);
    }
    //性别
    public void getGenderInfo() {
        AccountGenderFunc genderfunc = (AccountGenderFunc) htFunc.get(R.id.me_account_gender);
        String gender = genderfunc.getresetRightGender();
        SelectGenderPopWin takePhotoPopWin = new SelectGenderPopWin(this, this,gender);
        takePhotoPopWin.showAtLocation(findViewById(R.id.mesupplierinfo_main), Gravity.CENTER, 0, 0);
    }

    @Override
    public boolean getIsShowChoiceDialog() {
        return false;
    }

    private String nameright ="";
    private String hintcontent = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){

            switch (requestCode){
                case 1:
                    nameright = data.getStringExtra("hintcontent");
                    AccountNameFunc namefunc = (AccountNameFunc) htFunc.get(R.id.me_account_name);
                    namefunc.resetRightName(nameright);
                    break;
                case 3:
                    hintcontent = data.getStringExtra("hintcontent");
                    briefintroduction.setText(hintcontent);
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
                    if (!"".equals(nameright)){
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("name", nameright);
                    }
                    if ("".equals(selectGender)||"男".equals(selectGender)){
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("sex", "1");
                    }else {
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("sex", "2");
                    }
                    AccountRegionFunc regionfunc = (AccountRegionFunc) htFunc.get(R.id.me_account_region);
                    String textString = regionfunc.getTextString();
                    if (!"".equals(textString)||textString!=null){

                        XCDSharePreference.getInstantiation(this).setSharedPreferences("region", textString);
                    }
                    String content = briefintroduction.getText().toString().trim();
                    if (!"".equals(content)){
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("content", content);
                    }
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
    private String selectGender = "";
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId ==R.id.man){
            selectGender ="男";
        }else if (checkedId ==R.id.woman){
            selectGender = "女";
        }
        AccountGenderFunc genderfunc = (AccountGenderFunc) htFunc.get(R.id.me_account_gender);
        genderfunc.resetRightGender(selectGender);

    }
}
