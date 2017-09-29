package com.medicinedot.www.medicinedot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.MessageInformInfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;

public class MessageinforDetailsActivity extends SimpleTopbarActivity {

   private MessageInformInfo messageinform;
    private TextView messagetitle,messagecontent,messagetime;
    private ImageView messageimage;

    @Override
    protected Object getTopbarTitle() {
        return "消息通知";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageinformdetails);

        messagetitle = (TextView) findViewById(R.id.message_title);
         messagecontent = (TextView) findViewById(R.id.message_content);
         messagetime = (TextView) findViewById(R.id.message_time);
         messageimage = (ImageView) findViewById(R.id.message_image);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        messageinform = (MessageInformInfo) intent.getSerializableExtra("messageinform");
        List<MessageInformInfo.DataBean> data = messageinform.getData();
        int position = intent.getIntExtra("position", -1);
        if (data !=null&&data.size()>0&&position !=-1){
            MessageInformInfo.DataBean dataBean = data.get(position);
            String title = dataBean.getTitle();
            messagetitle.setText(title ==null?"":title);
            String time = dataBean.getTime();
            messagetime.setText(time ==null?"":time);
            String content = dataBean.getContent();
            String image = dataBean.getImage();
            if ("".equals(image)){
                messageimage.setVisibility(View.GONE);
                messagecontent.setText(content ==null?"":content);
            }else {
                messagecontent.setText(content ==null?"":"\t\t\t\t" +content);
                messageimage.setVisibility(View.VISIBLE);
                try {
                    Glide.with(this)
                            .load(GlobalParam.IP +image)
                            .centerCrop()
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.mipmap.upload_image_side)
                            .error(R.mipmap.upload_image_side)
                            .into(messageimage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            ToastUtil.showToast("未获取到详细信息!");
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
