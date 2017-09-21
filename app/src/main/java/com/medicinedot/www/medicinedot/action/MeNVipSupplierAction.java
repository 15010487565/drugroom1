package com.medicinedot.www.medicinedot.action;

import android.Manifest;
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
import com.medicinedot.www.medicinedot.adapter.MeGridViewAdapter;
import com.medicinedot.www.medicinedot.bean.AilPayOrderMoney;
import com.medicinedot.www.medicinedot.bean.SettingAboutInfo;
import com.medicinedot.www.medicinedot.bean.VipLevelMoney;
import com.medicinedot.www.medicinedot.entity.GlobalParam;
import com.medicinedot.www.medicinedot.func.MeYServiceTextTopBtnFunc;
import com.medicinedot.www.medicinedot.pay.PayResult;
import com.medicinedot.www.medicinedot.pay.PrePayIdAsyncTask;
import com.medicinedot.www.medicinedot.pay.ZFBPay;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ClassUtils;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static www.xcd.com.mylibrary.utils.ClassUtils.REQUEST_CODE_ASK_CALL_PHONE;
import static www.xcd.com.mylibrary.utils.XCDSharePreference.getInstantiation;

public class MeNVipSupplierAction extends SimpleTopbarActivity implements AdapterView.OnItemClickListener {

    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE
    };
    private static Class<?> rightFuncArray[] = {MeYServiceTextTopBtnFunc.class};
    /**
     * 昵称
     */
    private TextView mevipfragment_name, mevipfragment_expiretime
            , mevip_region,vipuser_agreement;
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
    private String level = "";
    /**
     * 会员套餐文本
     *
     * @return
     */
    private TextView mevip_monthmoney, mevip_quartermoney, mevip_halfyear, mevip_yearmonay;

    /**
     * 支付方式标志
     */
    private String selectpayway = "-1";

    public  static final int SDK_PAY_FLAG = 1000;
    private String ordertype = "1";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menvipsupplier);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
        endtime = XCDSharePreference.getInstantiation(this).getSharedPreferences("endtime");
        city = XCDSharePreference.getInstantiation(this).getSharedPreferences("region");
        String name = XCDSharePreference.getInstantiation(this).getSharedPreferences("name");
        //会员到期时间
        mevipfragment_expiretime = (TextView) findViewById(R.id.mevipfragment_expiretime);
        //所在地区
        mevip_region = (TextView) findViewById(R.id.mevip_region);
        String stringExtra = getIntent().getStringExtra("city");
        ordertype = getIntent().getStringExtra("ordertype");
        if (stringExtra == null || "".equals(stringExtra)) {
            if (city.indexOf("-") != -1) {
                String[] split = city.split("-");
                if (split.length>1){
                    city = split[1];
                }
            }
            mevipfragment_expiretime.setText("您还不是" + city + "会员");
            mevip_region.setText(this.city);
        } else {
            String endtimeExtra = getIntent().getStringExtra("endtime");
            mevipfragment_expiretime.setText("您的" + stringExtra + "会员 " + endtimeExtra.substring(0, 10) + " 到期");
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
    }
    public void getMoneyForLevel() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(103, GlobalParam.GETMONEYFORLEVEL, params);
    }
    private void getServicePhone() {
        createDialogshow();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(101, GlobalParam.ABOUNT, params);
    }

    private void initData() {
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
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.okalipay:
                if (TextUtils.isEmpty(level)){
                    ToastUtil.showToast("请选择开通的会员套餐");
                    return;
                }
                Log.e("TAG_ordertype","ordertype = "+ordertype);
                if (ordertype == null){
                    ordertype = "1";
                }
                if (vipmoneydata == null ||vipmoneydata.size()==0){
                    ToastUtil.showToast("未获取到套餐金额！");
                    return;
                }
                VipLevelMoney.DataBean dataBean = vipmoneydata.get(Integer.parseInt(level)-1);
                String money = dataBean.getMoney();
                if (money == null ||"".equals(money)){
                    ToastUtil.showToast("未获取到套餐金额！");
                    return;
                }
                createDialogshow();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("uid", uid);
                params.put("city", city);
                params.put("level", level);//1 月度会员 2 季度会员 3 半年会员 4 年费会员
                params.put("summoney", money);
                params.put("ordertype", ordertype);//订单类型 1 开通会员 2 续费会员
                okHttpPost(102, GlobalParam.CREATEORDER, params);
                break;
            case R.id.mevip_wexin:
                mevip_wexin.setBackgroundColor(getColor(R.color.line_c3));
                mevip_alipay.setBackgroundColor(getColor(R.color.white));
                selectpayway = "1";
                break;
            case R.id.mevip_alipay:
                mevip_wexin.setBackgroundColor(getColor(R.color.white));
                mevip_alipay.setBackgroundColor(getColor(R.color.line_c3));
                selectpayway = "2";
                break;
            case R.id.mevip_monthparent://月度会员
                Log.e("TAG_点击", "月度会员");
                level = "1";
                mevip_monthparent.setBackgroundResource(R.color.line_c3);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.white);

                break;
            case R.id.mevip_quarterparent://季度会员
                Log.e("TAG_点击", "季度会员");
                level = "2";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.line_c3);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.white);
                break;
            case R.id.mevip_halfyearparent://半年会员
                Log.e("TAG_点击", "半年会员");
                level = "3";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.line_c3);
                mevip_yearparent.setBackgroundResource(R.color.white);
                break;
            case R.id.mevip_yearparent://年费会员
                Log.e("TAG_点击", "年费会员");
                level = "4";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.line_c3);
                break;
            case R.id.vipuser_agreement:
                Intent intent = new Intent(this, LocalityWebView.class);
                intent.putExtra("url", "file:///android_asset/vipuseragreement.html");
                startActivity(intent);
                break;
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
                ClassUtils.callDirectly(this, phoneservice, false);
            }
        }

    }
    List<VipLevelMoney.DataBean> vipmoneydata;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200) {
            switch (requestCode) {
                case 101:
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
                    break;
                case 102:
                    if ("1".equals(selectpayway)) {//微信
//                        wexinlpay(ordernum,summoney);
                    } else if ("2".equals(selectpayway)) {//支付宝
                        AilPayOrderMoney payorder = JSON.parseObject(returnData, AilPayOrderMoney.class);
                        String data = payorder.getData();
                        Log.e("TAG_支付宝","data="+data);
                        new ZFBPay(handler).buttonAlipay(this,data);
                    }
                    break;
                case 103:
                    VipLevelMoney vipmoney = JSON.parseObject(returnData,VipLevelMoney.class);
                    vipmoneydata = vipmoney.getData();
                    for (int i = 0,j = vipmoneydata.size(); i < j; i++) {
                        VipLevelMoney.DataBean dataBean = vipmoneydata.get(i);
                        String level = dataBean.getLevel();
                        String money = dataBean.getMoney();
                        if ("1".equals(level)){
                            mevip_monthmoney.setText(money);
                        }else  if ("2".equals(level)){
                            mevip_quartermoney.setText(money);
                        }else  if ("3".equals(level)){
                            mevip_halfyear.setText(money);
                        }else  if ("4".equals(level)){
                            mevip_yearmonay.setText(money);
                        }
                    }
                    break;
            }
        }
    }
    private void wexinlpay(String ordernum,String summoney) {
        summoney = String.valueOf((int) (Double.valueOf(summoney) * 100));
        String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        PrePayIdAsyncTask prePayIdAsyncTask = new PrePayIdAsyncTask(this, summoney, ordernum);
        prePayIdAsyncTask.execute(urlString);
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
        Log.e("TAG_电话权限", "requestCode=" + requestCode);
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    ClassUtils.call(this, GlobalParam.TEXTPHOME, false);
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
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.showToast("支付结果确认中");
                            finish();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtil.showToast("支付失败");
                            finish();
                        }
                    }
                    break;

                }
            }
        }
    };
}
