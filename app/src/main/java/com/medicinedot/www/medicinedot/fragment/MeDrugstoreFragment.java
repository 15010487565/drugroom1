package com.medicinedot.www.medicinedot.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.MeDrugstoreInfoActivity;
import com.medicinedot.www.medicinedot.entity.GlobalParam;
import com.medicinedot.www.medicinedot.func.MainAddTopBtnFunc;
import com.medicinedot.www.medicinedot.func.MeChatSupplierFunc;
import com.medicinedot.www.medicinedot.func.MeDrugstoreNeedFunc;
import com.medicinedot.www.medicinedot.func.MeSettingSupplierFunc;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.func.BaseFunc;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

/**
 * 我
 *
 * @author litfb
 * @version 1.0
 * @date 2014年10月8日
 */
public class MeDrugstoreFragment extends BaseFragment implements OnClickListener {

    /**
     * 简单系统功能列表
     */
    private static Class<?>[] systemFuncArray = {
            MeDrugstoreNeedFunc.class,
            MeChatSupplierFunc.class,
            MeSettingSupplierFunc.class
            
    };

    /**
     * 个人信息设置
     */
    private View viewAccountSetting;
    /**
     * 自定义功能View
     */
    private LinearLayout customFuncView;
    /**
     * 自定义功能列表
     */
    private LinearLayout customFuncList;
    /**
     * 系统功能View
     */
    private LinearLayout systemFuncView;
    /**
     * 系统功能列表
     */
    private LinearLayout systemFuncList;
    /**
     * 昵称
     */
    private TextView mefragment_name;
    /**
     * 头像
     */
    private ImageView mefragment_head;
    /**
     * 头像背景
     */
    private ImageView mefragment_headbg;
    /**
     * 功能对象
     */
    private Hashtable<Integer, BaseFunc> htFunc = new Hashtable<>();

    /**
     * 获得自定义功能列表
     *
     * @return
     */
    protected Class<?>[] getCustomFuncArray() {
        return null;
    }

    /**
     * 获得系统功能列表
     *
     * @return
     */
    protected Class<?>[] getSystemFuncArray() {
        return systemFuncArray;
    }

    /**
     * Topbar功能列表
     */
    private static Class<?> rightFuncArray[] = {MainAddTopBtnFunc.class};
    private ImageView mefragment_headalpha;
    private LinearLayout mefragment_info;
    @Override
    protected Object getTopbarTitle() {
        return "我的";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_medrugstore;
    }

//    @Override
//    protected Class<?>[] getTopbarRightFuncArray() {
//
//        return rightFuncArray;
//    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        mefragment_info = (LinearLayout) view.findViewById(R.id.mefragment_info);
        mefragment_info.setOnClickListener(this);
        systemFuncList = (LinearLayout) view.findViewById(R.id.me_system_func_list);
        customFuncList = (LinearLayout) view.findViewById(R.id.me_custom_func_list);
        customFuncView = (LinearLayout) view.findViewById(R.id.me_custom_func_view);
        systemFuncView = (LinearLayout) view.findViewById(R.id.me_system_func_view);
        //昵称
        mefragment_name = (TextView) view.findViewById(R.id.mefragment_name);
        //头像
        mefragment_head = (ImageView) view.findViewById(R.id.mefragment_head);

        mefragment_headbg = (ImageView) view.findViewById(R.id.mefragment_headbg);
        mefragment_headalpha = (ImageView) view.findViewById(R.id.mefragment_headalpha);
        mefragment_headalpha.getBackground().setAlpha(50);//0--255为透明度值
        // 初始化自定义功能
        initCustomFunc();
        // 初始化系统功能
        initSystemFunc();
        String headimg = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("headimg");
        String name = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("name");
        initData(name,GlobalParam.IP+headimg);
    }
    private void initData(String name,String headimg){
        mefragment_name.setText(name);
        //加载圆形头像
        Glide.with(getActivity())
                .load(headimg)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.defaulthead)
                .error(R.mipmap.defaulthead)
                .into(mefragment_head);
        if (headimg ==null||(GlobalParam.IP).equals(headimg)){
            Glide.with(getActivity())
                    .load(GlobalParam.headurl)
                    .placeholder(R.mipmap.upload_image_side)
                    .error(R.mipmap.upload_image_side)
                    .crossFade(1000)
                    .bitmapTransform(new BlurTransformation(getActivity(),23,4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .into(mefragment_headbg);
        }else {
            Glide.with(getActivity())
                    .load(headimg)
                    .placeholder(R.mipmap.defaulthead)
                    .error(R.mipmap.defaulthead)
                    .crossFade(1000)
                    .bitmapTransform(new BlurTransformation(getActivity(),23,4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .into(mefragment_headbg);
        }
    }
    /**
     * 初始化系统功能
     */
    protected void initSystemFunc() {
        Class<?>[] systemFuncs = getSystemFuncArray();
        // 功能列表为空,隐藏区域
        if (systemFuncs == null || systemFuncs.length == 0) {
            systemFuncView.setVisibility(View.GONE);
            return;
        }
        // 初始化功能
        boolean isSeparator = false;
        for (Class<?> systemFunc : systemFuncs) {
            // view
            View funcView = getFuncView(systemFunc, isSeparator);
            if (funcView != null) {
                // 点击事件
                funcView.setOnClickListener(this);
                // 加入页面
                systemFuncList.addView(funcView);
                isSeparator = true;
            }
        }
        // 设置列表显示
        systemFuncList.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化自定义功能
     */
    protected void initCustomFunc() {
        Class<?>[] customFuncs = getCustomFuncArray();
        // 功能列表为空,隐藏区域
        if (customFuncs == null || customFuncs.length == 0) {
            customFuncView.setVisibility(View.GONE);
            return;
        }
        // 初始化功能
        boolean isSeparator = false;
        for (Class<?> customFunc : customFuncs) {
            // view
            View funcView = getFuncView(customFunc, isSeparator);
            if (funcView != null) {
                // 点击事件
                funcView.setOnClickListener(this);
                // 加入页面
                customFuncList.addView(funcView);
                isSeparator = true;
            }
        }
        // 设置列表显示
        customFuncList.setVisibility(View.VISIBLE);
    }

    /**
     * 获得功能View
     *
     * @return
     */
    private View getFuncView(Class<?> funcClazz, boolean isSeparator) {
        BaseFunc func = BaseFunc.newInstance(funcClazz, getActivity());
        if (func == null) {
            return null;
        }
        htFunc.put(func.getFuncId(), func);
        // view
        return func.initFuncView(isSeparator);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mefragment_info:
                startActivityForResult(new Intent(getActivity(), MeDrugstoreInfoActivity.class),0);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    String headimg = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("headimg");
                    String name = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("name");
                    initData(name,GlobalParam.IP+headimg);
                    break;
            }
        }
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
