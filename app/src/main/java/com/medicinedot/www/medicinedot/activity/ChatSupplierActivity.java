package com.medicinedot.www.medicinedot.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.adapter.ChatSupplierAdapter;
import com.medicinedot.www.medicinedot.bean.ChatSupplierInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.view.XListView;
import www.xcd.com.mylibrary.utils.ToastUtil;

public class ChatSupplierActivity extends SimpleTopbarActivity implements
        XListView.IXListViewListener, AdapterView.OnItemClickListener {

    private XListView listview;
    private Handler mHandler;
    private ChatSupplierAdapter adapter;
    private List<ChatSupplierInfo.DataBean> data;
    public static final int CHATDETAILS = 100;

    @Override
    protected Object getTopbarTitle() {
        return "消息通知";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatsupplier);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        listview = (XListView) findViewById(R.id.chat_listview);
        listview.setPullLoadEnable(false);//设置上拉刷新
        listview.setPullRefreshEnable(false);//设置下拉刷新
        listview.setXListViewListener(this); //设置监听事件，重写两个方法
        listview.setOnItemClickListener(this);
        adapter = new ChatSupplierAdapter(this, handler);
        mHandler = new Handler();
        initData();

    }

    private void initData() {
        //临时数据接入
        data = new ArrayList<ChatSupplierInfo.DataBean>();
        for (int i = 0; i < 5; i++) {
            ChatSupplierInfo.DataBean dataBean = new ChatSupplierInfo.DataBean();
            if (i==0){
                dataBean.setTitle("锄禾日当午");
                dataBean.setType("1");
                dataBean.setContent("电风扇电风扇电风扇反对");
                dataBean.setImage(GlobalParam.headurl);
            }else if (i==1){
                dataBean.setTitle("汗滴禾下土");
                dataBean.setType("3");
                dataBean.setContent("与交通银行");
            }else if (i==2){
                dataBean.setTitle("系统消息");
                dataBean.setType("2");
                dataBean.setContent("电风扇电风扇电风扇反对");
                dataBean.setImage(GlobalParam.headurl);
            }
            data.add(dataBean);
        }
        adapter.setData(data);
        listview.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(listview);
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("type", "top");
//        params.put("key", "49c4ede2b341aa948a3760c578647054");
//        okHttpPost(100,"http://v.juhe.cn/toutiao/index?", params);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHATDETAILS:
                    Bundle bundle_car = msg.getData();
                    int position_chat = bundle_car.getInt("position");
                    String userid = bundle_car.getString("userid");
                    ToastUtil.showToast("点击的是第" + position_chat + "item" + ",他的id=" + userid);
                    break;
            }
        }
    };

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
