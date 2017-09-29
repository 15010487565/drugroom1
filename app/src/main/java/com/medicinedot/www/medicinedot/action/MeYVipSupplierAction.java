package com.medicinedot.www.medicinedot.action;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.LocalityWebView;
import com.medicinedot.www.medicinedot.adapter.MeGridViewAdapter;
import com.medicinedot.www.medicinedot.adapter.MeVipSupplierNLoaclityAdapter;
import com.medicinedot.www.medicinedot.bean.MeVipCityListInfo;
import com.medicinedot.www.medicinedot.bean.SettingAboutInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;
import com.medicinedot.www.medicinedot.func.MeNServiceTextTopBtnFunc;

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

import static com.medicinedot.www.medicinedot.R.id.mevipfragment_head;
import static com.medicinedot.www.medicinedot.R.id.mevipfragment_headbg;
import static www.xcd.com.mylibrary.utils.ClassUtils.REQUEST_CODE_ASK_CALL_PHONE;
import static www.xcd.com.mylibrary.utils.XCDSharePreference.getInstantiation;

public class MeYVipSupplierAction extends SimpleTopbarActivity implements AdapterView.OnItemClickListener {

    private TextView mefragment_name, meyvip_expiretime, meyvip_expiremoney
            ,vipcity_coun,vipuser_agreement;
    private LinearLayout mevip_regionparent;
    private ImageView mefragment_head;
    private ImageView mefragment_headbg;
    private ImageView mefragment_headalpha;
    private ListView listview;
    private MeVipSupplierNLoaclityAdapter adapter;
    private List<MeVipCityListInfo.DataBean> data;
    public static final int MEVIPNLOCALITYSUPPLIERRENEW = 100;
    private GridView gridview;
    private MeGridViewAdapter gridadapter;
    private int[] ItemTexttop = {R.mipmap.moredrugstore, R.mipmap.getrugstorenumber, R.mipmap.morelook};
    private int[] ItemTextbottom = {R.string.moredrugstore, R.string.getrugstorenumber, R.string.morelook};
    private String endtime = "";
    private String city = "";
    private String uid = "" ;
    /**
     * 客服电话
     */
    private String phoneservice = "";
    private static Class<?> rightFuncArray[] = {MeNServiceTextTopBtnFunc.class};

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
        setContentView(R.layout.activity_meyvipsupplier);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initView();
        initData();
        //加载城市列表
        initCityList();

    }
    private void initCityList() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(100, GlobalParam.MEVIPCITYLIST, params);
    }

    private void initView() {
        mefragment_name = (TextView) findViewById(R.id.mevipfragment_name);
        String name = XCDSharePreference.getInstantiation(this).getSharedPreferences("name");
        mefragment_name.setText(name);
        //会员到期时间
        meyvip_expiretime = (TextView) findViewById(R.id.meyvip_expiretime);
        //续费按钮
        meyvip_expiremoney = (TextView) findViewById(R.id.meyvip_expiremoney);
        meyvip_expiremoney.setOnClickListener(this);
        endtime = XCDSharePreference.getInstantiation(this).getSharedPreferences("endtime");
        city = XCDSharePreference.getInstantiation(this).getSharedPreferences("region");
        if (endtime ==null ||"".equals(endtime)) {
            if (city.indexOf("-") != -1) {
                String[] split = city.split("-");
                if (split.length>1){
                    city = split[1];
                }
            }
            meyvip_expiretime.setText("您还不是" + city + "会员");
            meyvip_expiremoney.setText("立即购买");
        } else {
            if (city.indexOf("-") != -1) {
                String[] split = city.split("-");
                if (split.length>1){
                    city = split[1];
                }

            }
            meyvip_expiretime.setText("您的" + city + "会员 " + endtime.substring(0,10) + " 到期");
            meyvip_expiremoney.setText("立即续费");
        }
        vipcity_coun = (TextView) findViewById(R.id.vipcity_coun);
        mefragment_head = (ImageView) findViewById(mevipfragment_head);
        mefragment_head.setOnClickListener(this);
        mefragment_headbg = (ImageView) findViewById(mevipfragment_headbg);
        mefragment_headalpha = (ImageView) findViewById(R.id.mevipfragment_headalpha);
        mefragment_headalpha.getBackground().setAlpha(50);//0--255为透明度值
        listview = (ListView) findViewById(R.id.mevipnlocalitysupplier_listview);
        adapter = new MeVipSupplierNLoaclityAdapter(MeYVipSupplierAction.this, handler);
        gridview = (GridView) findViewById(R.id.gridview);
        gridadapter = new MeGridViewAdapter(this, ItemTexttop, ItemTextbottom);
        gridview.setAdapter(gridadapter);
        //添加消息处理
        gridview.setOnItemClickListener(this);
        //开通其他城市
        mevip_regionparent = (LinearLayout) findViewById(R.id.mevip_regionparent);
        mevip_regionparent.setOnClickListener(this);
        //开通会员用户协议
        vipuser_agreement = (TextView) findViewById(R.id.vipuser_agreement);
        vipuser_agreement.setOnClickListener(this);
        //获取客服手机号
        uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
        createDialogshow();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(101, GlobalParam.ABOUNT, params);
    }

    private void initData() {
        //加载圆形头像
        String headimg = getInstantiation(this).getSharedPreferences("headimg");
        try {
            Glide.with(this)
                    .load(GlobalParam.IP+headimg)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.defaulthead)
                    .error(R.mipmap.defaulthead)
                    .into(mefragment_head);
            if (headimg == null || "".equals(headimg)) {
                Glide.with(this)
                        .load(GlobalParam.headurl)
                        .placeholder(R.mipmap.upload_image_side)
                        .error(R.mipmap.upload_image_side)
                        .crossFade(1000)
                        .bitmapTransform(new BlurTransformation(this, 23, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                        .into(mefragment_headbg);
            } else {
                Glide.with(this)
                        .load(GlobalParam.IP+headimg)
                        .placeholder(R.mipmap.defaulthead)
                        .error(R.mipmap.defaulthead)
                        .crossFade(1000)
                        .bitmapTransform(new BlurTransformation(this, 23, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                        .into(mefragment_headbg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.meyvip_expiremoney:
                getBuyVip(city,endtime);
                break;
            case R.id.vipuser_agreement:
                Intent intent = new Intent(this, LocalityWebView.class);
                intent.putExtra("url", "file:///android_asset/vipuseragreement.html");
                startActivity(intent);
                break;
            case R.id.mevip_regionparent:
                getBuyVip(city,endtime);
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MEVIPNLOCALITYSUPPLIERRENEW:
                    Bundle bundle_car = msg.getData();
                    int position_chat = bundle_car.getInt("position");
                    if (data!=null&&data.size()>0){
                        MeVipCityListInfo.DataBean dataBean = data.get(position_chat);
                        String city =dataBean.getCity();
                        String endtime = dataBean.getEndtime();
                        getBuyVip(city,endtime);
                    }
                    break;
            }
        }
    };
    public void getBuyVip(String city,String time){
        Intent intent =new Intent(this,MeNVipSupplierAction.class);
        intent.putExtra("city",city);
        intent.putExtra("endtime",time);
        intent.putExtra("ordertype","2");
        startActivity(intent);
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

            switch (requestCode) {
                case 100:
                    if (returnCode == 200) {
                        MeVipCityListInfo info = JSON.parseObject(returnData, MeVipCityListInfo.class);
                        data = info.getData();
                        if (data != null || data.size() > 0) {
                            adapter.setData(data);
                            listview.setAdapter(adapter);
                            setListViewHeightBasedOnChildren(listview);
                        }
                        vipcity_coun.setText("我的会员城市("+data.size()+")");
                    }
                    break;
                case 101:
                    if (returnCode == 200) {
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
        gridadapter.setSeclection(i);
        gridadapter.notifyDataSetChanged();
        Message msg = null;
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
                    ClassUtils.call(this, phoneservice, false);
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
