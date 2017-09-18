package com.medicinedot.www.medicinedot.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.action.MeYVipSupplierAction;
import com.medicinedot.www.medicinedot.bean.MeVipCityListInfo;

import java.util.List;


/**
 * Created by Android on 2017/8/9.
 */

public class MeVipSupplierNLoaclityAdapter extends BaseAdapter{
    private Context context;
    private List<MeVipCityListInfo.DataBean> list;
    private Handler handler;
    public MeVipSupplierNLoaclityAdapter(Context context,Handler handler) {
        this.context = context;
        this.handler = handler;
    }
    public void  setData( List<MeVipCityListInfo.DataBean> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.action_mevipsuppnloaciltylistitem, null);
            hodler.loactlity = (TextView) convertView.findViewById(R.id.loactlity);
            hodler.vip_time = (TextView) convertView.findViewById(R.id.vip_time);
            hodler.homechatimage = (TextView) convertView.findViewById(R.id.homechatimage);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        MeVipCityListInfo.DataBean dataBean = list.get(position);
        String city = dataBean.getCity();
        hodler.loactlity.setText(city==null?"未知":city);
        String endtime = dataBean.getEndtime();
        hodler.vip_time.setText(endtime==null?"未知":endtime);
        hodler.homechatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = handler.obtainMessage();
                message.what = MeYVipSupplierAction.MEVIPNLOCALITYSUPPLIERRENEW;
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                message.setData(bundle);
                message.sendToTarget();
            }
        });
        return convertView;
    }

    class ViewHodler {
        private TextView loactlity;
        private TextView vip_time,homechatimage;
    }
}
