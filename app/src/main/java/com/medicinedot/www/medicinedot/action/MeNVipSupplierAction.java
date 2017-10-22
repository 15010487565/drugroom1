package com.medicinedot.www.medicinedot.action;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.LocalityWebView;
import com.medicinedot.www.medicinedot.activity.MainSupplierActivity;
import com.medicinedot.www.medicinedot.adapter.MeGridViewAdapter;
import com.medicinedot.www.medicinedot.bean.AilPayOrderMoney;
import com.medicinedot.www.medicinedot.bean.CityListAllInfo;
import com.medicinedot.www.medicinedot.bean.HomeSupplierinfo;
import com.medicinedot.www.medicinedot.bean.SettingAboutInfo;
import com.medicinedot.www.medicinedot.bean.VipLevelMoney;
import com.medicinedot.www.medicinedot.func.MeYServiceTextTopBtnFunc;
import com.medicinedot.www.medicinedot.pay.PayResult;
import com.medicinedot.www.medicinedot.pay.PrePayIdAsyncTask;
import com.medicinedot.www.medicinedot.pay.ZFBPay;
import com.medicinedot.www.medicinedot.threelevelganged.ArrayWheelAdapter;
import com.medicinedot.www.medicinedot.threelevelganged.BaseThreeActivity;
import com.medicinedot.www.medicinedot.threelevelganged.OnWheelChangedListener;
import com.medicinedot.www.medicinedot.threelevelganged.WheelView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static www.xcd.com.mylibrary.utils.ClassUtils.REQUEST_CODE_ASK_CALL_PHONE;
import static www.xcd.com.mylibrary.utils.XCDSharePreference.getInstantiation;

public class MeNVipSupplierAction extends BaseThreeActivity implements AdapterView.OnItemClickListener, OnWheelChangedListener {

    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE
    };
    private static Class<?> rightFuncArray[] = {MeYServiceTextTopBtnFunc.class};
    /**
     * 昵称
     */
    private TextView mevipfragment_name, mevipfragment_expiretime, vipuser_agreement, mevip_region;
    /**
     * 头像
     */
    private ImageView mevipfragment_head;
    /**
     * 头像背景
     */
    private ImageView mevipfragment_headbg;
    private ImageView mevipfragment_headalpha;
    private GridView gridview;
    private MeGridViewAdapter adapter;
    private String endtime = "";
    private String city = "";
    private int[] ItemTexttop = {R.mipmap.moredrugstore, R.mipmap.getrugstorenumber, R.mipmap.morelook};
    private int[] ItemTextbottom = {R.string.moredrugstore, R.string.getrugstorenumber, R.string.morelook};
    private LinearLayout mevip_wexin, mevip_alipay;
    private Button okalipay;
    /**
     * 客服电话
     */
    private String phoneservice = "";
    private String uid;

    /**
     * 会员套餐父布局
     */
    private LinearLayout mevip_monthparent, mevip_quarterparent, mevip_halfyearparent, mevip_yearparent;
    //选中的会员套餐
    private String level = "1";
    /**
     * 会员套餐文本
     *
     * @return
     */
    private TextView mevip_monthmoney, mevip_quartermoney, mevip_halfyear, mevip_yearmonay;

    /**
     * 支付方式标志
     */
    private String selectpayway = "2";

    public static final int SDK_PAY_FLAG = 1000;
    private String ordertype = "1";

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private LinearLayout address_select;
    private TextView btn_confirm, btn_off, mevip_allmoney;

    @Override
    protected String[] getPermissions() {
        return PERMISSIONS;
    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.mevip;
    }

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    public boolean getIsShowChoiceDialog() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menvipsupplier);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        address_select = (LinearLayout) findViewById(R.id.address_select);
        //
        btn_confirm = (TextView) findViewById(R.id.btn_confirm);
        btn_off = (TextView) findViewById(R.id.btn_off);
        btn_confirm.setOnClickListener(this);
        btn_off.setOnClickListener(this);

        uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
        endtime = XCDSharePreference.getInstantiation(this).getSharedPreferences("endtime");
        city = XCDSharePreference.getInstantiation(this).getSharedPreferences("region");
        String name = XCDSharePreference.getInstantiation(this).getSharedPreferences("name");
        //会员到期时间
        mevipfragment_expiretime = (TextView) findViewById(R.id.mevipfragment_expiretime);
        //所在地区
        mevip_region = (TextView) findViewById(R.id.mevip_region);
        mevip_region.setOnClickListener(this);
        String stringExtra = getIntent().getStringExtra("city");
        ordertype = getIntent().getStringExtra("ordertype");
        if (stringExtra == null || "".equals(stringExtra)) {
            if (city.indexOf("-") != -1) {
                String[] split = city.split("-");
                if (split.length > 1) {
                    city = split[1];
                }
            }
            mevipfragment_expiretime.setText("您还不是" + city + "会员");
            mevip_region.setText(this.city);
        } else {
            String isMember = getIntent().getStringExtra("isMember");
            if ("1".equals(isMember)){
                String endtimeExtra = getIntent().getStringExtra("endtime");
            }else if ("3".equals(isMember)){
                mevipfragment_expiretime.setText("您的" + stringExtra + "会员已到期");
            }
            mevip_region.setText(stringExtra);
        }
        mevip_monthparent = (LinearLayout) findViewById(R.id.mevip_monthparent);
        mevip_monthparent.setOnClickListener(this);
        mevip_quarterparent = (LinearLayout) findViewById(R.id.mevip_quarterparent);
        mevip_quarterparent.setOnClickListener(this);
        mevip_halfyearparent = (LinearLayout) findViewById(R.id.mevip_halfyearparent);
        mevip_halfyearparent.setOnClickListener(this);
        mevip_yearparent = (LinearLayout) findViewById(R.id.mevip_yearparent);
        mevip_yearparent.setOnClickListener(this);
        //月
        mevip_monthmoney = (TextView) findViewById(R.id.mevip_monthmoney);
        //季度
        mevip_quartermoney = (TextView) findViewById(R.id.mevip_quartermoney);
        //半年
        mevip_halfyear = (TextView) findViewById(R.id.mevip_halfyear);
        //年费
        mevip_yearmonay = (TextView) findViewById(R.id.mevip_yearmonay);
        //
        mevip_wexin = (LinearLayout) findViewById(R.id.mevip_wexin);
        mevip_wexin.setOnClickListener(this);
        mevip_alipay = (LinearLayout) findViewById(R.id.mevip_alipay);
        mevip_alipay.setOnClickListener(this);

        mevipfragment_name = (TextView) findViewById(R.id.mevipfragment_name);
        mevipfragment_name.setText(name);
        mevipfragment_head = (ImageView) findViewById(R.id.mevipfragment_head);
        mevipfragment_head.setOnClickListener(this);
        mevipfragment_headbg = (ImageView) findViewById(R.id.mevipfragment_headbg);
        mevipfragment_headalpha = (ImageView) findViewById(R.id.mevipfragment_headalpha);
        mevipfragment_headalpha.getBackground().setAlpha(50);//0--255为透明度值
        gridview = (GridView) findViewById(R.id.gridview);
        adapter = new MeGridViewAdapter(this, ItemTexttop, ItemTextbottom);
        gridview.setAdapter(adapter);
        //支付按钮
        okalipay = (Button) findViewById(R.id.okalipay);
        okalipay.setOnClickListener(this);
        //开通会员用户协议
        vipuser_agreement = (TextView) findViewById(R.id.vipuser_agreement);
        vipuser_agreement.setOnClickListener(this);
        //支付总金额
        mevip_allmoney = (TextView) findViewById(R.id.mevip_allmoney);
        //添加消息处理
        gridview.setOnItemClickListener(this);
        //临时数据
        initData();
        //加载城市列表
//        initCityList();
        //获取客服电话
        getServicePhone();
        //加载会员套餐价格
        getMoneyForLevel();

        setUpViews();
        setUpListener();
        //获取允许开头的列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(100, GlobalParam.CITYLISTFORMEMBER, params);
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

    private void setUpData(List<CityListAllInfo.DataBean> provinceList) {
        initProvinceDatas(provinceList);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        if (mProvinceDatas != null) {
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

        if (mDistrictDatasMap == null || mDistrictDatasMap.size() == 0) {
            mViewDistrict.setVisibility(View.GONE);
        } else {
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
        if (mCitisDatasMap == null) {
            cities = new String[]{""};
        } else {
            cities = mCitisDatasMap.get(mCurrentProviceName);
            if (cities == null) {
                cities = new String[]{""};
            }
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }


    public void getMoneyForLevel() {
        String levelcity = mevip_region.getText().toString().trim();
        if (levelcity == null) {
            ToastUtil.showToast("获取开通地址错误！");
            return;
        }
        //初始化会员套餐金额
        mevip_monthmoney.setText("00.00");
        mevip_quartermoney.setText("00.00");
        mevip_halfyear.setText("00.00");
        mevip_yearmonay.setText("00.00");
        //支付总金额
        mevip_allmoney.setText("00.00");
        //重新获取套餐金额
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("city", levelcity);
        okHttpGet(103, GlobalParam.GETMONEYFORLEVEL, params);
    }

    private void getServicePhone() {
        createDialogshow();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(101, GlobalParam.ABOUNT, params);
    }

    private void initData() {
        try {
            //加载圆形头像
            String headimg = getInstantiation(this).getSharedPreferences("headimg");
            Glide.with(this)
                    .load(GlobalParam.IP + headimg)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.defaulthead)
                    .error(R.mipmap.defaulthead)
                    .into(mevipfragment_head);
            if (headimg == null || "".equals(headimg)) {
                Glide.with(this)
                        .load(GlobalParam.headurl)
                        .placeholder(R.mipmap.upload_image_side)
                        .error(R.mipmap.upload_image_side)
                        .crossFade(1000)
                        .bitmapTransform(new BlurTransformation(this, 23, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                        .into(mevipfragment_headbg);
            } else {
                Glide.with(this)
                        .load(GlobalParam.IP + headimg)
                        .placeholder(R.mipmap.defaulthead)
                        .error(R.mipmap.defaulthead)
                        .crossFade(1000)
                        .bitmapTransform(new BlurTransformation(this, 23, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                        .into(mevipfragment_headbg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VipLevelMoney.DataBean dataBean;
//    private String money = null;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.okalipay:
                if (TextUtils.isEmpty(level)) {
                    level = "1";
                }
                Log.e("TAG_ordertype", "ordertype = " + ordertype);
                if (ordertype == null) {
                    ordertype = "1";
                }
                String payallmoney = mevip_allmoney.getText().toString().trim();
                Log.e("TAG_payallmoney", "payallmoney = " + payallmoney);
                if (TextUtils.isEmpty(payallmoney) || "00.00".equals(payallmoney)) {
                    ToastUtil.showToast("未获取到套餐金额！");
                    return;
                }
                //支付类型 1 支付宝 2 微信
                payType(payallmoney,selectpayway);
                break;
            case R.id.mevip_wexin:
                mevip_wexin.setBackgroundColor(getColor(R.color.background_eaeaea));
                mevip_alipay.setBackgroundColor(getColor(R.color.white));
                selectpayway = "2";
                break;
            case R.id.mevip_alipay:
                mevip_wexin.setBackgroundColor(getColor(R.color.white));
                mevip_alipay.setBackgroundColor(getColor(R.color.background_eaeaea));
                selectpayway = "1";
                break;
            case R.id.mevip_monthparent://月度会员
                Log.e("TAG_点击", "月度会员");
                level = "1";
                mevip_monthparent.setBackgroundResource(R.color.background_eaeaea);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.white);
                payAllMoney(mevip_monthmoney.getText().toString().trim());
                break;
            case R.id.mevip_quarterparent://季度会员
                Log.e("TAG_点击", "季度会员");
                level = "2";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.background_eaeaea);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.white);
                payAllMoney(mevip_quartermoney.getText().toString().trim());
                break;
            case R.id.mevip_halfyearparent://半年会员
                Log.e("TAG_点击", "半年会员");
                level = "3";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.background_eaeaea);
                mevip_yearparent.setBackgroundResource(R.color.white);
                payAllMoney(mevip_halfyear.getText().toString().trim());
                break;
            case R.id.mevip_yearparent://年费会员
                Log.e("TAG_点击", "年费会员");
                level = "4";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.background_eaeaea);
                payAllMoney(mevip_yearmonay.getText().toString().trim());
                break;
            case R.id.vipuser_agreement:
                Intent intent = new Intent(this, LocalityWebView.class);
                intent.putExtra("urltitle","用户协议");
                intent.putExtra("url", "file:///android_asset/vipuseragreement.html");
                startActivity(intent);
                break;
            case R.id.mevip_region:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mevip_region.getWindowToken(), 0); //强制隐藏键盘
                if (mProvinceDatas == null) {
                    boolean permissions = checkupPermissions(WRITEREADPERMISSIONS);
                    if (permissions&&citylistForMember.size()>0&&citylistForMember !=null) {
                        setUpData(citylistForMember);
                    }
                } else {
                    address_select.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_confirm:
                address_select.setVisibility(View.GONE);
                mevip_region.setText(mCurrentCityName);
                getMoneyForLevel();
                break;
            case R.id.btn_off:
                address_select.setVisibility(View.GONE);
                break;
        }
    }

    private void payType(String payallmoney,String paytype) {
        createDialogshow();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("city", mevip_region.getText().toString().trim());
        params.put("level", level);//1 月度会员 2 季度会员 3 半年会员 4 年费会员
        params.put("summoney", payallmoney);
        params.put("ordertype", ordertype);//订单类型 1 开通会员 2 续费会员
        params.put("paytype", paytype);//支付类型 1 支付宝 2 微信
        okHttpPost(102, GlobalParam.CREATEORDER, params);
    }

    private void payAllMoney(String payallmoney){

        if (TextUtils.isEmpty(payallmoney) || "00.00".equals(payallmoney)) {
            ToastUtil.showToast("未获取到套餐金额！");
            mevip_allmoney.setText("00.00");
            return;
        } else {
            mevip_allmoney.setText(payallmoney);
        }
    }
    public void getRelationService() {
        if (phoneservice == null || "".equals(phoneservice)) {
            ToastUtil.showToast("未获取到客服电话!");
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CODE_ASK_CALL_PHONE);
            } else {
                ClassUtils.callDirectly(this, phoneservice, true);
            }
        }

    }

    List<VipLevelMoney.DataBean> vipmoneydata;
    List<CityListAllInfo.DataBean> citylistForMember;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 100:
                if (returnCode == 200){
                    CityListAllInfo cityallinfo = JSON.parseObject(returnData, CityListAllInfo.class);
                    citylistForMember = cityallinfo.getData();
                    setUpData(citylistForMember);
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
                if (returnCode == 200) {
                    try {
                        SettingAboutInfo phoneinfo = JSON.parseObject(returnData, SettingAboutInfo.class);
                        List<SettingAboutInfo.DataBean> data = phoneinfo.getData();
                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                SettingAboutInfo.DataBean dataBean = data.get(i);
                                phoneservice = dataBean.getPhone();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 102:
                if (returnCode == 200) {
                    if ("2".equals(selectpayway)) {//微信
                        try {
                            JSONObject jsonObject = new JSONObject(returnData);
                            JSONObject data = jsonObject.getJSONObject("data");
                            String appid = data.optString("appid");
                            String partnerId = data.optString("partnerid");
                            String packageValue = data.optString("package");
                            String timestamp = data.optString("timestamp");
                            String prepayid = data.optString("prepayid");
                            String sign = data.optString("sign");
                            String noncestr = data.optString("noncestr");
                            String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                            PrePayIdAsyncTask prePayIdAsyncTask
                                    = new PrePayIdAsyncTask(this, appid,partnerId
                                    ,packageValue,noncestr,timestamp,prepayid,sign);
//                            prePayIdAsyncTask.execute(urlString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if ("1".equals(selectpayway)) {//支付宝
                        AilPayOrderMoney payorder = JSON.parseObject(returnData, AilPayOrderMoney.class);
                        String data = payorder.getData();
                        Log.e("TAG_支付宝", "data=" + data);
                        new ZFBPay(handler).buttonAlipay(this, data);
                    }
                }

                break;
            case 103:
                if (returnCode == 200) {
                    VipLevelMoney vipmoney = JSON.parseObject(returnData, VipLevelMoney.class);
                    vipmoneydata = vipmoney.getData();
                    for (int i = 0, j = vipmoneydata.size(); i < j; i++) {
                        VipLevelMoney.DataBean dataBean = vipmoneydata.get(i);
                        String level = dataBean.getLevel();
                        String money = dataBean.getMoney();
                        if ("1".equals(level)) {
                            mevip_monthmoney.setText(money);
                        } else if ("2".equals(level)) {
                            mevip_quartermoney.setText(money);
                        } else if ("3".equals(level)) {
                            mevip_halfyear.setText(money);
                        } else if ("4".equals(level)) {
                            mevip_yearmonay.setText(money);
                        }
                        Log.e("TAG_ZHIFU","level="+level+";this.level="+this.level+"money"+money);
                        if ("1".equals(this.level)) {
                            mevip_allmoney.setText(mevip_monthmoney.getText().toString());
                        } else if ("2".equals(this.level)) {
                            mevip_allmoney.setText(mevip_quartermoney.getText().toString());
                        } else if ("3".equals(this.level)) {
                            mevip_allmoney.setText(mevip_halfyear.getText().toString());
                        } else if ("4".equals(this.level)) {
                            mevip_allmoney.setText(mevip_yearmonay.getText().toString());
                        }
                    }
                }else {
                    mevip_allmoney.setText("");
                }

                break;
            case 104:
                if (returnCode == 200) {
                    if (returnData != null) {
                        HomeSupplierinfo info = JSON.parseObject(returnData, HomeSupplierinfo.class);
                        String is_member = info.getIs_member();
                        if ("1".equals(is_member)) {
                            XCDSharePreference.getInstantiation(this).setSharedPreferences("is_member", "1");
                        } else {
                            XCDSharePreference.getInstantiation(this).setSharedPreferences("is_member", "2");
                        }
                    }
                }

                break;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        adapter.setSeclection(i);
        adapter.notifyDataSetChanged();
        if (i == 0) {
//            Intent intent = new Intent(this, MeInfoActivity.class);
//            intent.putExtra("MEINFOHEAD",image_head);
//            intent.putExtra("NICKNAME",nickname);
//            intent.putExtra("SEX",sex);
//            intent.putExtra("USERBIRTHDAY",userbirthday);
//            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        } else if (i == 1) {
//            startActivity(new Intent(this, MyPetActivity.class));
        } else if (i == 2) {
//            startActivity(new Intent(this, MyPetIntegralTaskActivty.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    ClassUtils.call(this, phoneservice, true);
                } else {
                    // Permission Denied
                    ToastUtil.showToast("无电话拨打权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    Log.e("TAG_resultStatus", "resultStatus=" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtil.showToast("支付成功");
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("uid", uid);
                        params.put("city", mevip_region.getText().toString().trim());
                        okHttpGet(104, GlobalParam.HOMEDATA, params);
                        startActivity(new Intent(MeNVipSupplierAction.this, MainSupplierActivity.class));
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.showToast("支付结果确认中");
                            finish();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtil.showToast("支付失败");
                        }
                    }
                    break;

                }
            }
        }
    };

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
