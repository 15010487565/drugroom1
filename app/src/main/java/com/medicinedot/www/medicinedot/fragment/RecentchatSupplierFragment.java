package com.medicinedot.www.medicinedot.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.medicinedot.www.medicinedot.R;

import java.io.IOException;
import java.util.Map;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;


/**
 * Created by Android on 2017/9/5.
 */
public class RecentchatSupplierFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected Object getTopbarTitle() {
        return R.string.recentchat;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        ConversationListFragment listFragment=new ConversationListFragment();
        Uri uri=Uri.parse("rong://com.medicinedot.www.medicinedot").buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),"false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(),"false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        listFragment.setUri(uri);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.rong_container,listFragment,null)
                .commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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