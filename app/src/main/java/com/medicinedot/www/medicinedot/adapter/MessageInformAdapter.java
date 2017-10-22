package com.medicinedot.www.medicinedot.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.activity.MessageInformActivity;
import com.medicinedot.www.medicinedot.bean.MessageInformInfo;

import java.util.List;

import www.xcd.com.mylibrary.entity.GlobalParam;

/**
 * Created by Android on 2017/9/11.
 */
public class MessageInformAdapter extends BaseAdapter {
    private Context context;
    private List<MessageInformInfo.DataBean> list;
    private Handler handler;
    public MessageInformAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
    public void  setData( List<MessageInformInfo.DataBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.information_listitem,viewGroup,false);
            hodler.chat_title = (TextView) convertView.findViewById(R.id.chat_title);
            hodler.chat_content = (TextView) convertView.findViewById(R.id.chat_content);
            hodler.chat_time = (TextView) convertView.findViewById(R.id.chat_time);
            hodler.chat_look = (TextView) convertView.findViewById(R.id.chat_look);
            hodler.chat_image = (ImageView) convertView.findViewById(R.id.chat_image);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        MessageInformInfo.DataBean dataBean = list.get(position);
        String type = dataBean.getType();
//        if ("1".equals(type)){//1:正文，2:系统消息，3:优惠活动
//            hodler.chat_look.setVisibility(View.VISIBLE);
//        }else if ("2".equals(type)){
//            hodler.chat_look.setVisibility(View.GONE);
//        }else if ("3".equals(type)){
//            hodler.chat_look.setVisibility(View.VISIBLE);
//        }else{
//            hodler.chat_look.setVisibility(View.VISIBLE);
//        }
        String time = dataBean.getTime();
        hodler.chat_time.setText(time);
        String title = dataBean.getTitle();
        hodler.chat_title.setText(title);
        String chatcontent = dataBean.getContent();
        hodler.chat_content.setText(chatcontent);
        String image = dataBean.getImage();
        if ("".equals(image)||dataBean==null){
            hodler.chat_image.setVisibility(View.GONE);
        }else {
            hodler.chat_image.setVisibility(View.VISIBLE);
            Glide.with(context.getApplicationContext())
                    .load(GlobalParam.IP+image)
                    .fitCenter()
                    .crossFade()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.upload_image_side)
                    .error(R.mipmap.upload_image_side)
                    .into(hodler.chat_image);
        }

        hodler.chat_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = handler.obtainMessage();
                message.what = MessageInformActivity.CHATDETAILS;
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                message.setData(bundle);
                message.sendToTarget();
            }
        });
        return convertView;
    }

    class ViewHodler {
        private TextView chat_title;
        private TextView chat_content,chat_time,chat_look;
        private ImageView chat_image;
    }
}
