package com.medicinedot.www.medicinedot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.medicinedot.www.medicinedot.action.MeNVipSupplierAction;
import com.medicinedot.www.medicinedot.adapter.HomeSupplierFragmentAdapter;
import com.medicinedot.www.medicinedot.bean.CityListAllInfo;
import com.medicinedot.www.medicinedot.bean.ErroeInfo;
import com.medicinedot.www.medicinedot.bean.HomeSupplierinfo;
import com.medicinedot.www.medicinedot.bean.VipLevelMoney;
import com.medicinedot.www.medicinedot.threelevelganged.ArrayWheelAdapter;
import com.medicinedot.www.medicinedot.threelevelganged.OnWheelChangedListener;
import com.medicinedot.www.medicinedot.threelevelganged.WheelView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import www.xcd.com.mylibrary.base.view.PullToRefreshLayout;
import www.xcd.com.mylibrary.base.view.XListViewHome;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.entity.HomeViewPagerImageinfo;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static com.medicinedot.www.medicinedot.R.id.home_convenientBanner;
import static www.xcd.com.mylibrary.utils.XCDSharePreference.context;
import static www.xcd.com.mylibrary.utils.XCDSharePreference.getInstantiation;

/**
 * Created by Android on 2017/9/5.
 */
public class HomeSupplierFragment extends BaseThreeFragment implements
        View.OnClickListener, OnItemClickListener
        , OnWheelChangedListener
        , PullToRefreshLayout.OnRefreshListener {

    ConvenientBanner homeConvenientBanner;
    private XListViewHome listview;
    private HomeSupplierFragmentAdapter adapter;
    private List<HomeSupplierinfo.DataBean> data;
    private Handler mHandler;
    private LinearLayout nulllinear, home_viphint;
    private Button homelookovernull;
    private TextView home_dredgeviphint, count;
    public static final int HOMECHATIMAGE = 100;
    public static final int HOMEMOILE = 101;
    private List<HomeViewPagerImageinfo.DataBean> imagedata;
    private String uid;
    //城市选择器
    private LinearLayout address_select;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView btn_confirm, btn_off;
    private String is_member;//供应商是否是会员标记
    private String page = "1";//分页加载默认首页
    @Override
    protected Object getTopbarTitle() {
        return R.string.home;
    }

    @Override
    protected boolean getTitleIsVisibilityImage() {
        return true;
    }

    @Override
    protected void topTitleOnclick() {
        super.topTitleOnclick();
        Log.e("TAG_", (mProvinceDatas == null) + "");
        if (mProvinceDatas == null) {
            boolean permissions = checkupPermissions(WRITEREADPERMISSIONS);
            if (permissions&&citylistForMember !=null&&citylistForMember.size()>0) {
                setUpData(citylistForMember);
            }
        } else {
            address_select.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homesupplier;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        uid = getInstantiation(getActivity()).getSharedPreferences("uid");
        is_member = XCDSharePreference.getInstantiation(context).getSharedPreferences("is_member");
        listview = (XListViewHome) view.findViewById(R.id.listview);
        count = (TextView) view.findViewById(R.id.count);
        listview.setPullLoadEnable(false);//设置上拉刷新
        listview.setPullRefreshEnable(false);//设置下拉刷新
//        listview.setOnItemClickListener(this);
        adapter = new HomeSupplierFragmentAdapter(getActivity(), handler);
        mHandler = new Handler();
        nulllinear = (LinearLayout) view.findViewById(R.id.nulllinear);
        home_viphint = (LinearLayout) view.findViewById(R.id.home_viphint);
        home_dredgeviphint = (TextView) view.findViewById(R.id.home_dredgeviphint);
        home_dredgeviphint.setOnClickListener(this);
        SpannableString styledText = new SpannableString("想查看更多药店数量，快去开通会员");
        styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_textcolor_black_99), 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_textcolor_top_bar), 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        home_dredgeviphint.setText(styledText, TextView.BufferType.SPANNABLE);
        homelookovernull = (Button) view.findViewById(R.id.homelookovernull);
        homelookovernull.setOnClickListener(this);

        homeConvenientBanner = (ConvenientBanner) view.findViewById(home_convenientBanner);
        //初始化轮播图
        initViewPagerImage();
        //首页数据
        String region = getInstantiation(getActivity()).getSharedPreferences("region");
        String homecitydata = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("HOMECITYDATA");
        if (homecitydata ==null){
            initData(region,"1");
        }else {
            initData(homecitydata,"1");
        }
        //城市选择器
        setUpViews(view);
        setUpListener();
//        setUpData();
        //获取允许开头的列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(103, GlobalParam.CITYLISTFORMEMBER, params);
        //
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(this);
    }

    private void setUpViews(View view) {
        address_select = (LinearLayout) view.findViewById(R.id.address_select);
        btn_confirm = (TextView) view.findViewById(R.id.btn_confirm);
        btn_off = (TextView) view.findViewById(R.id.btn_off);
        btn_confirm.setOnClickListener(this);
        btn_off.setOnClickListener(this);
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
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

    private void setUpData(List<CityListAllInfo.DataBean> provinceList) {
        initProvinceDatas(provinceList);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), mProvinceDatas));
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
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), areas));
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
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    private void initViewPagerImage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(101, GlobalParam.BANNERIMG, params);
    }

    private String titleregion;

    private void initData(String titleregion,String page) {
        if (titleregion == null || "".equals(titleregion)) {
            titleregion = "北京市";
        } else {
            if (titleregion.indexOf("-") != -1) {
                String[] split = titleregion.split("-");
                if (split.length > 1) {
                    titleregion = split[1];
                } else {
                    titleregion = "北京市";
                }
            }
        }
        this.titleregion = titleregion;
        resetTopbarTitle(titleregion);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("city", titleregion);
        params.put("page", page);
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
                    chatListviewItem(position_chat);
                    break;
            }
        }
    };
    private String ronguserIdChatObjext;
    private String rongusernameChatObjext;

    private void chatListviewItem(int position) {
        HomeSupplierinfo.DataBean dataBean = data.get(position);
        String uidSupplier = dataBean.getUid();//供应商id
        ronguserIdChatObjext = dataBean.getRonguserId();
        rongusernameChatObjext = dataBean.getName();
        Log.e("TAG_融云", "id=" + ronguserIdChatObjext + ";name=" + rongusernameChatObjext);
        if (TextUtils.isEmpty(uidSupplier)) {
            ToastUtil.showToast("获取信息错误！");
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);//用户类型 1 供应商 2 药店
            params.put("pharmacyid", uidSupplier);    //药店id
            okHttpGet(102, GlobalParam.ISMEMBERFORUSER, params);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.homelookovernull://首页无数据查看其他地区
                if (mProvinceDatas == null) {
                    boolean permissions = checkupPermissions(WRITEREADPERMISSIONS);
                    if (permissions&&citylistForMember !=null&&citylistForMember.size()>0) {
                        setUpData(citylistForMember);
                    }
                } else {
                    address_select.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_confirm:
                address_select.setVisibility(View.GONE);
                //首页数据
                initData(mCurrentProviceName + "-" + mCurrentCityName,"1");
                XCDSharePreference.getInstantiation(getActivity()).setSharedPreferences("HOMECITYDATA", mCurrentProviceName + "-" + mCurrentCityName);
                break;
            case R.id.btn_off:
                address_select.setVisibility(View.GONE);
                break;
            case R.id.home_dredgeviphint:
                startActivity(new Intent(getActivity(), MeNVipSupplierAction.class));
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
    List<VipLevelMoney.DataBean> vipmoneydata;
    List<CityListAllInfo.DataBean> citylistForMember;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    getInstantiation(getActivity()).setSharedPreferences("region", titleregion);
                    if (returnData == null) {
                        nulllinear.setVisibility(View.VISIBLE);
                        home_viphint.setVisibility(View.GONE);
                        count.setVisibility(View.GONE);
                        listview.setVisibility(View.GONE);
                    } else {
                        HomeSupplierinfo info = JSON.parseObject(returnData, HomeSupplierinfo.class);
                        data = info.getData();
                        String is_member = info.getIs_member();
                        page = info.getPage();
                        getInstantiation(getActivity()).setSharedPreferences("is_member", is_member);
                        String endtime = info.getEndtime();
                        getInstantiation(getActivity()).setSharedPreferences("endtime", endtime);
                        String day = info.getDay();
                        XCDSharePreference.getInstantiation(getActivity()).setSharedPreferences("day", day);
                        String countint = info.getCount();
                        if ("0".equals(countint)){
                            nulllinear.setVisibility(View.VISIBLE);
                            home_viphint.setVisibility(View.GONE);
                            count.setVisibility(View.GONE);
                            listview.setVisibility(View.GONE);
                            data.clear();
                        }else {
                            if (data == null || data.size() == 0||"".equals(data)) {
                                ToastUtil.showToast("没有更多数据了");
                            } else {
                                this.count.setVisibility(View.VISIBLE);
                                listview.setVisibility(View.VISIBLE);
                                nulllinear.setVisibility(View.GONE);
                                if ("1".equals(is_member)) {//会员
                                    home_viphint.setVisibility(View.GONE);
                                } else {//非会员
                                    home_viphint.setVisibility(View.VISIBLE);
                                }

                                String infoCount = info.getCount();
                                String infocount_ = ((infoCount == null) || ("".equals(infoCount)) ? "0" : infoCount);
                                this.count.setText(titleregion + "加盟药店共" + infocount_ + "家");
                                info.getCount();
                                if (LOADMORE.equals(PullToRefreshstate)){
                                    adapter.addData(data, is_member);
                                }else {
                                    adapter.setData(data, is_member);
                                }
                                listview.setAdapter(adapter);
                                setListViewHeightBasedOnChildren(listview);
                            }
                        }
                    }
                } else {
                    ErroeInfo errorinfo = JSON.parseObject(returnData, ErroeInfo.class);
                    String errorinfoData = errorinfo.getData();
                    if (errorinfoData == null || "".equals(errorinfoData)) {
                        nulllinear.setVisibility(View.VISIBLE);
                        count.setVisibility(View.GONE);
                        listview.setVisibility(View.GONE);
                        home_viphint.setVisibility(View.GONE);
                        getInstantiation(getActivity()).setSharedPreferences("is_member", "2");
                        ToastUtil.showToast(returnMsg);
                    }
                }
                if (REFRESH.equals(PullToRefreshstate)) {
                    pullToRefreshLayoutRefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else if (LOADMORE.equals(PullToRefreshstate)) {
                    pullToRefreshLayoutLoadMore.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
                break;
            case 101:
                if (returnCode == 200) {
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
                    }else {
                        homeConvenientBanner.setVisibility(View.GONE);
                    }
                }else {
                    homeConvenientBanner.setVisibility(View.GONE);
                }
                break;
            case 102:

                    if (RongIM.getInstance() != null && !TextUtils.isEmpty(ronguserIdChatObjext) && !TextUtils.isEmpty(rongusernameChatObjext)) {
                        if (returnCode == 200) {
                            XCDSharePreference.getInstantiation(getActivity()).setSharedPreferences("RONGVIPSHOW","1");
                        }else {
                            XCDSharePreference.getInstantiation(getActivity()).setSharedPreferences("RONGVIPSHOW","2");
                        }
                        RongIM.getInstance().startPrivateChat(getActivity(), ronguserIdChatObjext, rongusernameChatObjext);
                    } else {
                        ToastUtil.showToast("获取聊天信息异常！");
                    }
                break;
            case 103:
                if (returnCode == 200){
                    CityListAllInfo cityallinfo = JSON.parseObject(returnData, CityListAllInfo.class);citylistForMember = cityallinfo.getData();
                    setUpData(citylistForMember);
                }else {
                    ToastUtil.showToast(returnMsg);
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
    public void onItemClick(int position) {

    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//        if ("1".equals(is_member)){
//            chatListviewItem(position);
//        }
//    }


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

    private String PullToRefreshstate = "";
    private final String REFRESH = "Refresh";
    private final String LOADMORE = "LOADMORE";
    private PullToRefreshLayout pullToRefreshLayoutRefresh;
    private PullToRefreshLayout pullToRefreshLayoutLoadMore;

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayoutRefresh) {
        PullToRefreshstate = REFRESH;
        this.pullToRefreshLayoutRefresh = pullToRefreshLayoutRefresh;
        // 下拉刷新操作
        initData(titleregion,"1");

    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayoutLoadMore) {
        PullToRefreshstate = LOADMORE;
        this.pullToRefreshLayoutLoadMore = pullToRefreshLayoutLoadMore;
        initData(titleregion,String.valueOf(Integer.parseInt(page)+1));
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
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.upload_image_side)
                    .error(R.mipmap.upload_image_side)
                    .into(imageView);
        }
    }
}
