package com.medicinedot.www.medicinedot.rong;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.action.MeNVipSupplierAction;

import io.rong.imkit.RongExtension;
import io.rong.imkit.fragment.ConversationFragment;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static www.xcd.com.mylibrary.utils.XCDSharePreference.context;

/**
 * Created by Android on 2017/10/20.
 */

public class BaseConversationFragment extends ConversationFragment implements View.OnClickListener{
    RongExtension mRongExtension;
    String rongvipshow;
    TextView rongviphint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rongvipshow = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("RONGVIPSHOW");
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRongExtension = (RongExtension) view.findViewById(R.id.rc_extension);
        rongviphint = (TextView) view.findViewById(R.id.rongviphint);
        if ("1".equals(rongvipshow)) {
            mRongExtension.setVisibility(View.VISIBLE);
            rongviphint.setVisibility(View.GONE);
        } else {
            String utype = XCDSharePreference.getInstantiation(getActivity()).getSharedPreferences("utype");
            String rongviphintString = "";
            if ("1".equals(utype)) {
                rongviphintString = "您好，您的会员已到期，请及时充值";
                SpannableString styledText = new SpannableString(rongviphintString);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_99), 0, rongviphintString.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_top_bar_background), rongviphintString.length()-2, rongviphintString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                rongviphint.setText(styledText, TextView.BufferType.SPANNABLE);
                rongviphint.setOnClickListener(this);
            } else if ("2".equals(utype)) {
                rongviphintString = "您好，对方会员已到期！";
                SpannableString styledText = new SpannableString(rongviphintString);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_black_99), 0, rongviphintString.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style_textcolor_top_bar_background), rongviphintString.length()-2, rongviphintString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                rongviphint.setText(styledText, TextView.BufferType.SPANNABLE);
            }
            mRongExtension.setVisibility(View.GONE);
            rongviphint.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rongviphint:
                getActivity().startActivity(new Intent(getActivity(), MeNVipSupplierAction.class));
                break;
        }
    }
}
