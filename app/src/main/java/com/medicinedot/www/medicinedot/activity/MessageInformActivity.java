package com.medicinedot.www.medicinedot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.adapter.MessageInformAdapter;
import com.medicinedot.www.medicinedot.bean.MessageInformInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.view.XListView;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

public class MessageInformActivity extends SimpleTopbarActivity implements
        XListView.IXListViewListener, AdapterView.OnItemClickListener {

    private XListView listview;
    private Handler mHandler;
    private MessageInformAdapter adapter;
    public static final int CHATDETAILS = 100;
    private String uid;
    @Override
    protected Object getTopbarTitle() {
        return "消息通知";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageinform);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
        listview = (XListView) findViewById(R.id.chat_listview);
        listview.setPullLoadEnable(false);//设置上拉刷新
        listview.setPullRefreshEnable(true);//设置下拉刷新
        listview.setXListViewListener(this); //设置监听事件，重写两个方法
        listview.setOnItemClickListener(this);
        adapter = new MessageInformAdapter(this, handler);
        mHandler = new Handler();
        initData();
    }

    private void initData() {
        createDialogshow();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        okHttpGet(100, GlobalParam.GETMESSAGEINFORM, params);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHATDETAILS:
                    Bundle bundle_car = msg.getData();
                    int position_chat = bundle_car.getInt("position");
                    ToastUtil.showToast("点击的是第" + position_chat + "item");
                    Intent intent = new Intent(MessageInformActivity.this, MessageinforDetailsActivity.class);
                    intent.putExtra("messageinform", (Serializable)messageInformInfo );
                    intent.putExtra("position",position_chat);
                    startActivity(intent);
                    break;
            }
        }
    };
    private MessageInformInfo messageInformInfo;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            switch (requestCode){
                case 100:
                    messageInformInfo = JSON.parseObject(returnData,MessageInformInfo.class);
                    List<MessageInformInfo.DataBean> dataBean = messageInformInfo.getData();
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
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
