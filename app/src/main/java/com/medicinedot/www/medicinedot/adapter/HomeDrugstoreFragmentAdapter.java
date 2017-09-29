package com.medicinedot.www.medicinedot.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.HomeDrugstoreinfo;
import com.medicinedot.www.medicinedot.entity.GlobalParam;
import com.medicinedot.www.medicinedot.fragment.HomeDrugstoreFragment;

import java.util.List;

import www.xcd.com.mylibrary.utils.GlideCircleTransform;


/**
 * Created by Android on 2017/8/9.
 */

public class HomeDrugstoreFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<HomeDrugstoreinfo.DataBean> list;
    private Handler handler;

    public HomeDrugstoreFragmentAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public void setData(List<HomeDrugstoreinfo.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_homedrugstorelistitem, null);
            hodler.home_name = (TextView) convertView.findViewById(R.id.home_name);
            hodler.home_number = (TextView) convertView.findViewById(R.id.home_number);
            hodler.home_location = (TextView) convertView.findViewById(R.id.home_location);
            hodler.home_context = (TextView) convertView.findViewById(R.id.home_context);
            hodler.titleimage = (ImageView) convertView.findViewById(R.id.titleimage);
            hodler.homechatimage = (ImageView) convertView.findViewById(R.id.homechatimage);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        HomeDrugstoreinfo.DataBean dataBean = list.get(position);
        String title = dataBean.getName();
        hodler.home_name.setText(title == null ? "未知" : title);
        String phone = dataBean.getPhone();
        String phoneTemp = "";
        if (phone == null || "".equals(phone)) {
            phoneTemp = "未知";
        } else {
            phoneTemp = phone;
            TextPaint paint = hodler.home_number.getPaint();
            paint.setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            paint.setAntiAlias(true);//抗锯齿
            paint.setColor(context.getResources().getColor(R.color.top_bar_background));
//            phoneTemp = phone.substring(0,3)+"****"+phone.substring(phone.length()-4,phone.length());
        }
        hodler.home_number.setText(phoneTemp);

        String date = dataBean.getRegion();
        hodler.home_location.setText(date == null ? "未知" : date);
        String drugcontent = dataBean.getContent();
        String homecontext = "";
        if (drugcontent == null || "".equals(drugcontent)) {
            homecontext = "个人简介：暂无";
        } else {
            homecontext = "个人简介：" + drugcontent;
        }
        SpannableString styledText = new SpannableString(homecontext);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_66), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_99), 6, homecontext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        hodler.home_context.setText(styledText, TextView.BufferType.SPANNABLE);

        String headimg = dataBean.getHeadimg();
        Glide.with(context.getApplicationContext())
                .load(GlobalParam.IP + headimg)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.upload_image_side)
                .error(R.mipmap.upload_image_side)
                .into(hodler.titleimage);
        final String uid = dataBean.getUid();
        //聊天按键
        hodler.homechatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = handler.obtainMessage();
                message.what = HomeDrugstoreFragment.HOMEDRUGSTORECHATIMAGE;
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("uid", uid);
                message.setData(bundle);
                message.sendToTarget();
            }
        });
        //拨打电话
        hodler.home_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = handler.obtainMessage();
                message.what = HomeDrugstoreFragment.HOMEDRUGSTOREMOILE;
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                message.setData(bundle);
                message.sendToTarget();
            }
        });
        return convertView;
    }

    class ViewHodler {
        private TextView home_name, home_number;
        private TextView home_location, home_context;
        private ImageView titleimage, homechatimage;
    }
}
