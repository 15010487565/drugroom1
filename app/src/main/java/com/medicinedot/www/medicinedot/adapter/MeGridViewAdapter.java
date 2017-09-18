package com.medicinedot.www.medicinedot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;


/**
 * Created by Android on 2017/5/16.
 */
public class MeGridViewAdapter extends BaseAdapter {

    private Context context;
    private int[] image;
    private int[] text;

    public MeGridViewAdapter(Context context, int[] image,int[] text) {
        this.context = context;
        this.image = image;
        this.text = text;
    }

    @Override
    public int getCount() {
        return image == null ? 0 : image.length;
    }

    @Override
    public Object getItem(int position) {
        if (image != null && image.length> position)
            return image[position];
        return null;
    }

    private int clickTemp = -1;

    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.megridviewadapter_item, null);
            hodler.relat = (LinearLayout) convertView.findViewById(R.id.relat);
            hodler.ItemTexttop = (TextView) convertView.findViewById(R.id.ItemTexttop);
            hodler.ItemTextbottom = (ImageView) convertView.findViewById(R.id.ItemTextbottom);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        hodler.ItemTexttop.setText(text[position]);
        hodler.ItemTextbottom.setImageResource(image[position]);
        return convertView;
    }

    class ViewHodler {
        private TextView ItemTexttop;
        private ImageView ItemTextbottom;
        private LinearLayout relat;
    }
}
