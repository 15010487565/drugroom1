package com.medicinedot.www.medicinedot.action;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.medicinedot.www.medicinedot.adapter.MeGridViewAdapter;
import com.medicinedot.www.medicinedot.bean.SettingAboutInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;
import com.medicinedot.www.medicinedot.func.MeYServiceTextTopBtnFunc;

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

public class MeNVipSupplierAction extends SimpleTopbarActivity  implements AdapterView.OnItemClickListener{

    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE
    };
    private static Class<?> rightFuncArray[] = {MeYServiceTextTopBtnFunc.class};
    /**
     * 昵称
     */
    private TextView mevipfragment_name,mevipfragment_expiretime,mevipfragment_expiremoney,mevip_region;
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
    private LinearLayout mevip_wexin,mevip_alipay;
    private Button okalipay;
    /**
     * 客服电话
     */
    private String phoneservice = "";
    private String uid;

    /**
     *会员套餐父布局
     */
    private LinearLayout mevip_monthparent,mevip_quarterparent,mevip_halfyearparent,mevip_yearparent;
    //选中的会员套餐
    private String level;
    /**
     * 会员套餐文本
     * @return
     */
    private TextView mevip_monthmoney,mevip_quartermoney,mevip_halfyear,mevip_yearmonay;
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
        if (stringExtra ==null||"".equals(stringExtra)){
            mevipfragment_expiretime.setText("您还不是" + city + "会员");
            mevip_region.setText(this.city);
        }else {
            String endtimeExtra = getIntent().getStringExtra("endtime");
            mevipfragment_expiretime.setText("您的" + stringExtra + "会员 " + endtimeExtra.substring(0,10) + " 到期");
            mevip_region.setText(stringExtra);
        }
        //续费按钮
//        mevipfragment_expiremoney = (TextView) findViewById(R.id.mevipfragment_expiremoney);
//        mevipfragment_expiremoney.setOnClickListener(this);
//        if (endtime ==null ||"".equals(endtime)) {
//            mevipfragment_expiretime.setText("您还不是" + city + "会员");
//            mevipfragment_expiremoney.setText("立即购买");
//        }
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
        mevip_alipay  = (LinearLayout) findViewById(R.id.mevip_alipay);
        mevip_alipay.setOnClickListener(this);

        mevipfragment_name = (TextView) findViewById(R.id.mevipfragment_name);
        mevipfragment_name.setText(name);
        mevipfragment_head = (ImageView) findViewById(R.id.mevipfragment_head);
        mevipfragment_head.setOnClickListener(this);
        mevipfragment_headbg = (ImageView) findViewById(R.id.mevipfragment_headbg);
        mevipfragment_headalpha = (ImageView) findViewById(R.id.mevipfragment_headalpha);
        mevipfragment_headalpha.getBackground().setAlpha(50);//0--255为透明度值
        gridview = (GridView) findViewById(R.id.gridview);
        adapter = new MeGridViewAdapter(this, ItemTexttop,ItemTextbottom);
        gridview.setAdapter(adapter);
        //支付按钮
        okalipay = (Button) findViewById(R.id.okalipay);
        okalipay.setOnClickListener(this);
        //添加消息处理
        gridview.setOnItemClickListener(this);
        //临时数据
        initData();
        //加载城市列表
//        initCityList();
        //获取客服电话
        getServicePhone();
    }
    private void getServicePhone() {
        createDialogshow();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(101, GlobalParam.ABOUNT, params);
    }
//    private void initCityList() {
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("uid", uid);
//        okHttpGet(100, GlobalParam.MEVIPCITYLIST, params);
//    }
    private void initData(){
        //加载圆形头像
        String headimg = getInstantiation(this).getSharedPreferences("headimg");
        Glide.with(this)
                .load(GlobalParam.IP+headimg)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.defaulthead)
                .error(R.mipmap.defaulthead)
                .into(mevipfragment_head);
        if (headimg ==null||"".equals(headimg)){
            Glide.with(this)
                    .load(GlobalParam.headurl)
                    .placeholder(R.mipmap.upload_image_side)
                    .error(R.mipmap.upload_image_side)
                    .crossFade(1000)
                    .bitmapTransform(new BlurTransformation(this,23,4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .into(mevipfragment_headbg);
        }else {
            Glide.with(this)
                    .load(GlobalParam.IP+headimg)
                    .placeholder(R.mipmap.defaulthead)
                    .error(R.mipmap.defaulthead)
                    .crossFade(1000)
                    .bitmapTransform(new BlurTransformation(this,23,4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .into(mevipfragment_headbg);
        }
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.okalipay:
                createDialogshow();
                if ("".equals(level)){
                    ToastUtil.showToast("请选择开通的级别!");
                    return;
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("uid", uid);
                params.put("city", city);
                params.put("level", level);//1 月度会员 2 季度会员 3 半年会员 4 年费会员
                params.put("summoney", "1");
                okHttpGet(102, GlobalParam.CREATEORDER, params);
                break;
            case R.id.mevip_wexin:
                mevip_wexin.setBackgroundColor(getColor(R.color.line_c3));
                mevip_alipay.setBackgroundColor(getColor(R.color.white));
                break;
            case R.id.mevip_alipay:
                mevip_wexin.setBackgroundColor(getColor(R.color.white));
                mevip_alipay.setBackgroundColor(getColor(R.color.line_c3));
                break;
            case R.id.mevip_monthparent://月度会员
                Log.e("TAG_点击","月度会员");
                level = "1";
                mevip_monthparent.setBackgroundResource(R.color.line_c3);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.white);
                break;
            case R.id.mevip_quarterparent://季度会员
                Log.e("TAG_点击","季度会员");
                level = "2";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.line_c3);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.white);
                break;
            case R.id.mevip_halfyearparent://半年会员
                Log.e("TAG_点击","半年会员");
                level = "3";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.line_c3);
                mevip_yearparent.setBackgroundResource(R.color.white);
                break;
            case R.id.mevip_yearparent://年费会员
                Log.e("TAG_点击","年费会员");
                level = "4";
                mevip_monthparent.setBackgroundResource(R.color.white);
                mevip_quarterparent.setBackgroundResource(R.color.white);
                mevip_halfyearparent.setBackgroundResource(R.color.white);
                mevip_yearparent.setBackgroundResource(R.color.line_c3);
                break;
        }
    }
    public void getRelationService() {
        if (phoneservice==null||"".equals(phoneservice)){
            ToastUtil.showToast("未获取到客服电话!");
        }else {
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
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200) {
            switch (requestCode) {
                case 101:
                    try {
                        SettingAboutInfo phoneinfo = JSON.parseObject(returnData,SettingAboutInfo.class);
                        List<SettingAboutInfo.DataBean> data = phoneinfo.getData();
                        if (data!=null&&data.size()>0){
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        adapter.setSeclection(i);
        adapter.notifyDataSetChanged();
        ToastUtil.showToast("点击了第"+i+"页面");
        Message msg = null;
        if (i==0){
//            Intent intent = new Intent(this, MeInfoActivity.class);
//            intent.putExtra("MEINFOHEAD",image_head);
//            intent.putExtra("NICKNAME",nickname);
//            intent.putExtra("SEX",sex);
//            intent.putExtra("USERBIRTHDAY",userbirthday);
//            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        }else if (i==1){
//            startActivity(new Intent(this, MyPetActivity.class));
        }else if (i==2){
//            startActivity(new Intent(this, MyPetIntegralTaskActivty.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("TAG_电话权限","requestCode="+requestCode);
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    ClassUtils.call(this, GlobalParam.TEXTPHOME,false);
                } else {
                    // Permission Denied
                    ToastUtil.showToast("无电话拨打权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
