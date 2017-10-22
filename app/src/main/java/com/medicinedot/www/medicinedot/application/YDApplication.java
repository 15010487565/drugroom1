package com.medicinedot.www.medicinedot.application;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.bean.RongYunUserInfo;
import com.medicinedot.www.medicinedot.rong.BaseExtensionModule;
import com.medicinedot.www.medicinedot.rong.RongReceiveMessageListener;
import com.medicinedot.www.medicinedot.rong.SendMessageListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.yonyou.sns.im.core.YYIMChat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import www.xcd.com.mylibrary.base.application.BaseApplication;
import www.xcd.com.mylibrary.config.HttpConfig;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.help.OkHttpHelper;
import www.xcd.com.mylibrary.http.HttpInterface;
import www.xcd.com.mylibrary.utils.AppManager;
import www.xcd.com.mylibrary.utils.CrashHandler;
import www.xcd.com.mylibrary.utils.NetUtil;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

import static www.xcd.com.mylibrary.utils.ToastUtil.showToast;

/**
 * Created by Android on 2017/9/5.
 */

public class YDApplication extends BaseApplication implements RongIM.UserInfoProvider
        , HttpInterface, RongIM.ConversationListBehaviorListener, RongIMClient.ConnectionStatusListener{


    private void initAppCrashHandler() {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            if (GlobalParam.DEBUG){
                initAppCrashHandler();
            }
            MultiDex.install(this);
            //初始化头像jar
            YYIMChat.getInstance().init(getApplicationContext());
            YYIMChat.getInstance().configLogger(Log.VERBOSE, true, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            /**
             * 初始化融云
             */
            RongIM.init(this);
            RongIM.setOnReceiveMessageListener(new RongReceiveMessageListener());
            RongIM.getInstance().setSendMessageListener(new SendMessageListener());
            RongIM.setUserInfoProvider(this, true);
            RongIM.setConnectionStatusListener(this);
            setMyExtensionModule();
            /**
             * 设置会话列表界面操作的监听器。
             */
            RongIM.setConversationListBehaviorListener(this);
            RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
                @Override
                public void getGroupMembers(String groupId, RongIM.IGroupMemberCallback callback) {
                }
            });
            Log.e("TAG_","=========");
            RongPushClient.checkManifest(getApplicationContext());
//            RongIM.setConversationListBehaviorListener(new RongConversationListBehaviorListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String versionName = null;
        try {
            versionName = getVersionName();
            Context appContext = getApplicationContext();
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(appContext);
            strategy.setAppVersion("测试" + versionName);
            CrashReport.initCrashReport
                    (appContext, getResources().getString(R.string.tengbugly_appID)
                            , false, strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }


    @Override
    public UserInfo getUserInfo(String userid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", userid);
        okHttpGet(102, GlobalParam.GETUSERINFOFORID, params);
        return null;
    }

    /**
     * GET网络请求
     *
     * @param url        地址
     * @param paramsMaps 参数
     */
    public void okHttpGet(final int requestCode, String url, final Map<String, Object> paramsMaps) {
        if (NetUtil.getNetWorking(this) == false) {
            showToast("请检查网络。。。");
            return;
        }

        OkHttpHelper.getInstance().getAsyncHttp(requestCode, url, paramsMaps, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //请求错误
                    case HttpConfig.REQUESTERROR:
                        IOException error = (IOException) msg.obj;
                        onErrorResult(HttpConfig.REQUESTERROR, error);
                        break;
                    //解析错误
                    case HttpConfig.PARSEERROR:
                        onParseErrorResult(HttpConfig.PARSEERROR);

                        break;
                    //网络错误
                    case HttpConfig.NETERROR:

                        break;
                    //请求成功
                    case HttpConfig.SUCCESSCODE:
                        Bundle bundle = msg.getData();
                        int requestCode = bundle.getInt("requestCode");
                        int returnCode = bundle.getInt("returnCode");
                        String returnMsg = bundle.getString("returnMsg");
                        String returnData = bundle.getString("returnData");
                        Map<String, Object> paramsMaps = (Map) msg.obj;
                        onSuccessResult(requestCode, returnCode, returnMsg, returnData, paramsMaps);
                        break;
                }
            }
        });
    }

    private String pharmacyid;//药店id
    private String supplierid;//供应商id

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 102:
                if (returnCode == 200) {
                    RongYunUserInfo result = JSON.parseObject(returnData, RongYunUserInfo.class);
                    RongYunUserInfo.DataBean userdata = result.getData();
                    String nickname = userdata.getName();
                    String image_head = userdata.getHeadimg();
                    String userid = userdata.getRonguserId();
                    Log.e("TAG_融云绘画列表","nickname="+nickname+"userid="+userid);
                    if (nickname != null && !TextUtils.isEmpty(userid)) {
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userid, nickname, Uri.parse(GlobalParam.IP + image_head)));
                    }
                }
                break;
            case 103:
                if (RongIM.getInstance() != null && !TextUtils.isEmpty(conversationTargetId) && !TextUtils.isEmpty(uiConversationTitle)) {
                    if (returnCode == 200) {
                        //显示发送按钮
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("RONGVIPSHOW","1");
                        Log.e("TAG_====","1");
//                        Activity activity = AppManager.getInstance().currentActivity();
//                        RongIM.getInstance().startPrivateChat(activity, conversationTargetId, uiConversationTitle);
                    } else {
                        XCDSharePreference.getInstantiation(this).setSharedPreferences("RONGVIPSHOW","2");
                        Log.e("TAG_====","2");
//                        String utype = XCDSharePreference.getInstantiation(this).getSharedPreferences("utype");
//                        if ("1".equals(utype)) {
//                            ToastUtil.showToast("您还不是会员！");
//                        } else if ("2".equals(utype)) {
//                            ToastUtil.showToast(returnMsg);
//                        }
//
                    }
                    Activity activity = AppManager.getInstance().currentActivity();
                    RongIM.getInstance().startPrivateChat(activity, conversationTargetId, uiConversationTitle);
                }else {
                    XCDSharePreference.getInstantiation(this).setSharedPreferences("RONGVIPSHOW","2");
                    Log.e("TAG_====","3");
                }

                break;
            case 104:
                RongYunUserInfo result = JSON.parseObject(returnData, RongYunUserInfo.class);
                RongYunUserInfo.DataBean userdata = result.getData();
                if (userdata!=null){
                    String utype = userdata.getUtype();
                    String tpyeuid = userdata.getUid();
                    if (uid == null || "".equals(uid)) {
                        uid = XCDSharePreference.getInstantiation(this).getSharedPreferences("uid");
                    }
                    if (uid != null && !"".equals(uid)) {
                        Map<String, Object> params = new HashMap<String, Object>();
                        if ("1".equals(utype)) {//用户类型 1 供应商 2 药店
                            supplierid = tpyeuid;//供应商id
                            pharmacyid = uid;
                        } else {
                            pharmacyid = tpyeuid;//药店id
                            supplierid = uid;
                        }
                        params.put("uid", supplierid);
                        params.put("pharmacyid", pharmacyid);
                        okHttpGet(103, GlobalParam.ISMEMBERFORUSER, params);
                    }
                }


                break;
        }

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

    /**
     * 当点击会话头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param targetId         被点击的用户id。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String targetId) {
        Log.e("TAG_会话列表头像", "targetId=" + targetId);
        return true;
    }

    /**
     * 当长按会话头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param targetId         被点击的用户id。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String targetId) {

        return false;
    }

    /**
     * 长按会话列表中的 item 时执行。
     *
     * @param context        上下文。
     * @param view           触发点击的 View。
     * @param uiConversation 长按时的会话条目。
     * @return 如果用户自己处理了长按会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    /**
     * 点击会话列表中的 item 时执行。
     *
     * @param context        上下文。
     * @param view           触发点击的 View。
     * @param uiConversation 会话条目。
     * @return 如果用户自己处理了点击会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
     */
    public String uid;
    private String conversationTargetId;
    private String uiConversationTitle;

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        Conversation.ConversationType conversationType = uiConversation.getConversationType();
        if (Conversation.ConversationType.PRIVATE.equals(conversationType)) {
            if (uiConversation != null) {
                conversationTargetId = uiConversation.getConversationTargetId();
                Log.e("TAG_会话列表id", "conversationTargetId=" + conversationTargetId);
                if (conversationTargetId != null && !"".equals(conversationTargetId)) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("id", conversationTargetId);
                    okHttpGet(104, GlobalParam.GETUSERINFOFORID, params);
                    uiConversationTitle = uiConversation.getUIConversationTitle();
                }
            }
            return true;
        } else {
            XCDSharePreference.getInstantiation(this).setSharedPreferences("RONGVIPSHOW","2");
            Log.e("TAG_====","4");
            return false;
        }
    }
    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new BaseExtensionModule());
            }
        }
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

    }
}
