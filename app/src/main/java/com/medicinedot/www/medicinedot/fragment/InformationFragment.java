package com.medicinedot.www.medicinedot.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.adapter.InformationAdapter;
import com.medicinedot.www.medicinedot.bean.InformationInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.base.view.XListView;
import www.xcd.com.mylibrary.utils.XCDSharePreference;


/**
 * Created by Android on 2017/9/5.
 */
public class InformationFragment extends BaseFragment implements
        XListView.IXListViewListener, AdapterView.OnItemClickListener {

    private XListView listview;
    private Handler mHandler;
    private InformationAdapter adapter;
    private String uid;
    private InformationInfo informationInfo;
    private boolean mHasLoadedOnce = false;// 页面已经加载过
    private List<InformationInfo.DataBean> dataBean;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !mHasLoadedOnce && dataBean == null) {
            Log.i("TestData", "FoundFragment 加载请求网络数据");
            //TO-DO 执行网络数据请求
            initData();
            mHasLoadedOnce = true;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mHasLoadedOnce = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mHasLoadedOnce = false;
    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.information;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }
    @Override
    protected void initView(LayoutInflater inflater, View view) {
        uid = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("uid");
        listview = (XListView) view.findViewById(R.id.chat_listview);
        listview.setPullLoadEnable(false);//设置上拉刷新
        listview.setPullRefreshEnable(true);//设置下拉刷新
        listview.setXListViewListener(this); //设置监听事件，重写两个方法
        listview.setOnItemClickListener(this);
        adapter = new InformationAdapter(getActivity());
        mHandler = new Handler();
    }


    private void initData() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(100, GlobalParam.GETMESSAGEINFORM, params);
    }



    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            switch (requestCode){
                case 100:
                    informationInfo = JSON.parseObject(returnData,InformationInfo.class);
                    dataBean = informationInfo.getData();
                    if (dataBean !=null &&dataBean.size()>0){
                        adapter.setData(dataBean);
                        listview.setAdapter(adapter);
                    }
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

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                list.clear();
//                geneItems();//获取数据
                initData();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
