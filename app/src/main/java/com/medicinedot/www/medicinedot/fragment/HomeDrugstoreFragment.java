package com.medicinedot.www.medicinedot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.adapter.HomeDrugstoreFragmentAdapter;
import com.medicinedot.www.medicinedot.bean.ErroeInfo;
import com.medicinedot.www.medicinedot.bean.HomeDrugstoreinfo;
import com.medicinedot.www.medicinedot.bean.HomeViewPagerImageinfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.base.view.XListViewHome;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static com.medicinedot.www.medicinedot.R.id.homelookovernull;

/**
 * Created by Android on 2017/9/5.
 */
public class HomeDrugstoreFragment extends BaseFragment implements
        View.OnClickListener, OnItemClickListener
        , XListViewHome.IXListViewListener, AdapterView.OnItemClickListener {

    private List<HomeViewPagerImageinfo.DataBean> imagedata;
    ConvenientBanner homeConvenientBanner;
    private XListViewHome listview;
    private HomeDrugstoreFragmentAdapter adapter;
    private List<HomeDrugstoreinfo.DataBean> data;
    private Handler mHandler;
    private LinearLayout nulllinear;
    public static final int HOMECHATIMAGE = 100;
    private String uid;
    private TextView count;

    @Override
    protected Object getTopbarTitle() {
        return R.string.home;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homedrugstore;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        uid = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("uid");
        listview = (XListViewHome) view.findViewById(R.id.listview);
        count = (TextView) view.findViewById(R.id.count);
        listview.setPullLoadEnable(false);//设置上拉刷新
        listview.setPullRefreshEnable(false);//设置下拉刷新
        listview.setXListViewListener(this); //设置监听事件，重写两个方法
        listview.setOnItemClickListener(this);
        adapter = new HomeDrugstoreFragmentAdapter(getActivity(), handler);
        mHandler = new Handler();
        nulllinear = (LinearLayout) view.findViewById(R.id.nulllinear);
        homeConvenientBanner = (ConvenientBanner) view.findViewById(R.id.home_convenientBanner);
        //初始化轮播图
        initViewPagerImage();
        //首页数据
        String region = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("region");
        initData(region);
    }

    private void initViewPagerImage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(101, GlobalParam.BANNERIMG, params);
    }
    private String titleregion;
    private void initData(String titleregion) {
        if (titleregion == null || "".equals(titleregion)) {
            titleregion = "北京市";
        } else {
            if (titleregion.indexOf("-") != -1) {
                String[] split = titleregion.split("-");
                if (split.length>1){
                    titleregion = split[1];
                }else {
                    titleregion = "北京市";
                }
                Log.e("TAG_首页城市选择", "titleregion=" + titleregion);
            } else {
                titleregion = "北京市";
            }
        }
        this.titleregion = titleregion;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("city",titleregion);
        okHttpGet(100, GlobalParam.HOMEDATA, params);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HOMECHATIMAGE:
                    Bundle bundle_car = msg.getData();
                    int position_chat = bundle_car.getInt("position");
//                    String uid = bundle_car.getString("uid");
                    HomeDrugstoreinfo.DataBean dataBean = data.get(position_chat);
                    String ronguserId = dataBean.getRonguserId();
                    String name = dataBean.getName();
                    Log.e("TAG_","容云id="+ronguserId+";昵稱"+name);
                    if (RongIM.getInstance() != null&& !TextUtils.isEmpty(ronguserId)&&!TextUtils.isEmpty(name)) {
                        RongIM.getInstance().startPrivateChat(getActivity(),ronguserId,name);
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case homelookovernull://首页无数据查看其他地区
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200) {
            switch (requestCode) {
                case 100:
                    if (returnData == null) {
                        nulllinear.setVisibility(View.VISIBLE);
//                    home_viphint.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);
                    } else {
                        listview.setVisibility(View.VISIBLE);
                        nulllinear.setVisibility(View.GONE);
                        HomeDrugstoreinfo info = JSON.parseObject(returnData, HomeDrugstoreinfo.class);
                        String infoCount = info.getCount();
                        String infocount_ = ((infoCount == null) || ("".equals(infoCount)) ? "0" : infoCount);
                        count.setText(titleregion+"共入住" + infocount_ + "位供应商");
                        data = info.getData();
                        adapter.setData(data);
                        listview.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(listview);
                    }
                    break;
                case 101:
                    HomeViewPagerImageinfo info = JSON.parseObject(returnData, HomeViewPagerImageinfo.class);
                    imagedata = info.getData();
                    if (imagedata != null && imagedata.size() > 0) {
                        homeConvenientBanner.startTurning(2000);
                        homeConvenientBanner.setPages(new CBViewHolderCreator() {
                            @Override
                            public Object createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, imagedata)//imgs是图片地址的集合
                                .setPointViewVisible(false)    //设置指示器是否可见
                                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.normal, R.drawable.unnormal})
                                //设置指示器位置（左、中、右）
                                .setOnItemClickListener(this)
                                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                                .startTurning(2000)     //设置自动切换（同时设置了切换时间间隔）
                                .setManualPageable(true)  //设置手动影响（设置了该项无法手动切换）
                        //设置点击监听事件
                        ;
                    }
                    break;
            }
        } else {
            ErroeInfo errorinfo = JSON.parseObject(returnData, ErroeInfo.class);
            String errorinfoData = errorinfo.getData();
            if (errorinfoData == null || "".equals(errorinfoData)) {
                nulllinear.setVisibility(View.VISIBLE);
                count.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
                ToastUtil.showToast(returnMsg);
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
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                list.clear();
//                geneItems();//获取数据
//                initData();
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                geneItems();//获取数据
                adapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    private void onLoad() {
        listview.stopRefresh();
        listview.stopLoadMore();
        listview.setRefreshTime("刚刚");
    }

    public class LocalImageHolderView implements Holder<HomeViewPagerImageinfo.DataBean> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, HomeViewPagerImageinfo.DataBean data) {
//            imageView.setImageResource(data);
            Glide.with(getActivity())
                    .load(GlobalParam.IP + data.getImage())
                    .centerCrop()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.upload_image_side)
                    .error(R.mipmap.upload_image_side)
                    .into(imageView);
        }
    }
}
