package com.medicinedot.www.medicinedot.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.LocalityWebView;
import com.medicinedot.www.medicinedot.adapter.InformationAdapter;
import com.medicinedot.www.medicinedot.bean.InformationInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.base.view.XListView;
import www.xcd.com.mylibrary.entity.GlobalParam;
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
    private RelativeLayout nomessagerelat;
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

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
        listview.setPullRefreshEnable(false);//设置下拉刷新
        listview.setXListViewListener(this); //设置监听事件，重写两个方法
        listview.setOnItemClickListener(this);
        adapter = new InformationAdapter(getActivity());
        mHandler = new Handler();
        nomessagerelat = (RelativeLayout) view.findViewById(R.id.nomessagerelat);
        initData();
    }


    private void initData() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(100, GlobalParam.INFORMATION, params);
    }



    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            switch (requestCode){
                case 100:
                    informationInfo = JSON.parseObject(returnData,InformationInfo.class);
                    dataBean = informationInfo.getData();
                    if (dataBean !=null &&dataBean.size()>0){
                        listview.setVisibility(View.VISIBLE);
                        nomessagerelat.setVisibility(View.GONE);
                        adapter.setData(dataBean);
                        listview.setAdapter(adapter);
                    }else {
                        listview.setVisibility(View.GONE);
                        nomessagerelat.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }else {
            listview.setVisibility(View.GONE);
            nomessagerelat.setVisibility(View.VISIBLE);
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
        InformationInfo.DataBean dataBean = this.dataBean.get(i-1);
        String url = dataBean.getUrl();
        Intent intent = new Intent(getActivity(), LocalityWebView.class);
        intent.putExtra("url", GlobalParam.IP+url);
        intent.putExtra("urltitle", "详情");
        startActivity(intent);
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
