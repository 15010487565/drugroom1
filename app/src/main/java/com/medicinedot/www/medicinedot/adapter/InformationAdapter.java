package com.medicinedot.www.medicinedot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.InformationInfo;

import java.util.List;

/**
 * Created by Android on 2017/9/11.
 */
public class InformationAdapter extends BaseAdapter {
    private Context context;
    private List<InformationInfo.DataBean> list;

    public InformationAdapter(Context context) {
        this.context = context;

    }
    public void  setData( List<InformationInfo.DataBean> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.messageinform_listitem,viewGroup,false);
            hodler.chat_title = (TextView) convertView.findViewById(R.id.chat_title);
            hodler.chat_content = (TextView) convertView.findViewById(R.id.chat_content);
            hodler.chat_time = (TextView) convertView.findViewById(R.id.chat_time);
            hodler.chat_look = (TextView) convertView.findViewById(R.id.chat_look);
            hodler.chat_image = (ImageView) convertView.findViewById(R.id.chat_image);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

//            Glide.with(context.getApplicationContext())
//                    .load(GlobalParam.IP+image)
//                    .centerCrop()
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.upload_image_side)
//                    .error(R.mipmap.upload_image_side)
//                    .into(hodler.chat_image);
        return convertView;
    }

    class ViewHodler {
        private TextView chat_title;
        private TextView chat_content,chat_time,chat_look;
        private ImageView chat_image;
    }
}
