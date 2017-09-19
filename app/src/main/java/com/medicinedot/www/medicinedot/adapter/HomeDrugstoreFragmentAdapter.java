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
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.HomeDrugstoreinfo;
import com.medicinedot.www.medicinedot.fragment.HomeSupplierFragment;

import java.util.List;


/**
 * Created by Android on 2017/8/9.
 */

public class HomeDrugstoreFragmentAdapter extends BaseAdapter{
    private Context context;
    private List<HomeDrugstoreinfo.DataBean> list;
    private Handler handler;
    private String is_member;
    public HomeDrugstoreFragmentAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }
    public void  setData( List<HomeDrugstoreinfo.DataBean> list){
//        is_member = XCDSharePreference.getInstantiation(context).getSharedPreferences("is_member");
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
        hodler.home_name.setText(title==null?"未知":title);
        String phone = dataBean.getPhone();
        String phoneTemp = "";
        if (phone ==null||"".equals(phone)){
            phoneTemp = "未知";
        }else {
            phoneTemp = phone.substring(0,3)+"****"+phone.substring(phone.length()-4,phone.length());
        }
        hodler.home_number.setText(phoneTemp);

        String date = dataBean.getRegion();
        hodler.home_location.setText(date==null?"未知":date);
        String drugcontent = dataBean.getContent();
        String homecontext = "药品需求："+drugcontent;
        SpannableString styledText = new SpannableString(homecontext);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_66), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_99), 6, homecontext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        hodler.home_context.setText(styledText, TextView.BufferType.SPANNABLE);
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
        private TextView home_name,home_number;
        private TextView home_location,home_context;
        private ImageView titleimage,homechatimage;
    }
}
