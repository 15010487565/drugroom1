package com.medicinedot.www.medicinedot.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.HomeSupplierinfo;
import com.medicinedot.www.medicinedot.fragment.HomeSupplierFragment;

import java.util.List;

import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.utils.GlideCircleTransform;


/**
 * Created by Android on 2017/8/9.
 */

public class HomeSupplierFragmentAdapter extends BaseAdapter{
    private Context context;
    private List<HomeSupplierinfo.DataBean> list;
    private Handler handler;
    private String is_member;
    public HomeSupplierFragmentAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
    public void  setData( List<HomeSupplierinfo.DataBean> list,String is_member){
        this.is_member = is_member;
        this.list = list;
        notifyDataSetChanged();
    }
    public void  addData( List<HomeSupplierinfo.DataBean> list,String is_member){
        this.is_member = is_member;
        if (this.list==null){
            this.list = list;
        }else {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {

        if ("1".equals(is_member)){
            return list==null?0:list.size();
        }else {
            if (list == null){
                return 0;
            }else {
                if (list.size()>3){
                    return 3;
                }else {
                    return list.size();
                }

            }
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_homesupplierlistitem, null);
            hodler.home_locationparent = (LinearLayout) convertView.findViewById(R.id.home_locationparent);
            hodler.home_title = (TextView) convertView.findViewById(R.id.home_title);
            hodler.home_location = (TextView) convertView.findViewById(R.id.home_location);
            hodler.home_context = (TextView) convertView.findViewById(R.id.home_context);
            hodler.titleimage = (ImageView) convertView.findViewById(R.id.titleimage);
            hodler.homechatimage = (ImageView) convertView.findViewById(R.id.homechatimage);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        HomeSupplierinfo.DataBean dataBean = list.get(position);
        String title = dataBean.getName();
        hodler.home_title.setText(title==null?"未知":title);
        String region = dataBean.getRegion();
        hodler.home_location.setText(region==null?"未知":region);
        String drugcontent = dataBean.getContent();
        String homecontext ="";
        if (drugcontent == null||"".equals(drugcontent)){
            homecontext = "药品需求：暂无";
        }else {
           homecontext = "药品需求："+drugcontent;
        }
        SpannableString styledText = new SpannableString(homecontext);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_66), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_66_), 5, homecontext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        hodler.home_context.setText(styledText, TextView.BufferType.SPANNABLE);

        if ("1".equals(is_member)){
            hodler.home_locationparent.setVisibility(View.VISIBLE);
            hodler.home_locationparent.setVisibility(View.VISIBLE);
            hodler.homechatimage.setVisibility(View.VISIBLE);
        }else{
            hodler.home_locationparent.setVisibility(View.GONE);
            hodler.home_locationparent.setVisibility(View.GONE);
            hodler.homechatimage.setVisibility(View.GONE);
        }
        String headimg = dataBean.getHeadimg();
        Glide.with(context.getApplicationContext())
                .load(GlobalParam.IP+headimg)
                .centerCrop()
                .crossFade()
                .fitCenter()
                .transform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.defaulthead)
                .error(R.mipmap.defaulthead)
                .into( hodler.titleimage);
        final String uid = dataBean.getUid();
        hodler.homechatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = handler.obtainMessage();
                message.what = HomeSupplierFragment.HOMECHATIMAGE;
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("uid",uid);
                message.setData(bundle);
                message.sendToTarget();
            }
        });
        return convertView;
    }

    class ViewHodler {
        private TextView home_title;
        private TextView home_location,home_context;
        private ImageView titleimage,homechatimage;
        private LinearLayout home_locationparent;
    }
}
