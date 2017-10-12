# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\AS_SDK\adt-bundle-windows-x86-20130917\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.JSON.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
#避免混淆Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#药片代码混淆
-dontpreverify
-flattenpackagehierarchy
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*



-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class com.android.vending.licensing.ILicensingService

-keepattributes *Annotation*

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

## ----------------------------------
##   ########## Gson混淆    ##########
## ----------------------------------
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-dontwarn okio.**
-keep class okio.** {*;}
-dontwarn okhttp3.**
-keep class okhttp3.** {*;}
-dontwarn com.squareup.**
-keep class com.squareup.**  {*;}

-dontwarn com.amap.api.**
-keep class com.amap.api.**  {*;}
-dontwarn com.autonavi.amap.mapcore.**
-keep class com.autonavi.amap.mapcore.**  {*;}
-dontwarn Decoder.**
-keep class Decoder.** { *; }
-dontwarn com.tencent.bugly.**
-keep class com.tencent.bugly.** {*;}
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** {*;}
-dontwarn it.sauronsoftware.ftp4j.**
-keep class it.sauronsoftware.ftp4j.** {*;}
-dontwarn com.google.**
-keep class com.google.** {*;}
-dontwarn com.hp.hpl.sparta.**
-keep class com.hp.hpl.sparta.** {*;}
-dontwarn demo.**
-keep class demo.** {*;}
-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.** {*;}
-dontwarn pinyindb.**
-keep class pinyindb.** {*;}
-dontwarn com.bambuser.broadcaster.**
-keep class com.bambuser.broadcaster.** {*;}
-dontwarn com.opaque.project.**
-keep class com.opaque.project.** {*;}
-dontwarn com.yonyou.**
-keep class com.yonyou.** {*;}
-dontwarn org.**
-keep class org.** {*;}
-dontwarn com.basesql.**
-keep class com.basesql.** {*;}
## ----------------------------------
##   ########## FaWe    ##########
## ----------------------------------

-keepattributes EnclosingMethod
-keepattributes Exceptions,InnerClasses,Deprecated,SourceFile,LineNumberTable
-keepparameternames  #含有指定声明方法参数或者类型的被保留
-keepattributes SourceFile,LineNumberTable #声明应被保留的属性，可以使用通配符。

-keeppackagenames !**
-keep public class org.** {*;}
#-ignorewarnings
########### jar ##################

-dontwarn assets.cfg.**
-keep class assets.cfg.** {*;}
-dontwarn com.baidu.**
-keep class com.baidu.** {*;}
-dontwarn vi.com.gdi.bgl.android.java.**
-keep class vi.com.gdi.bgl.android.java.** {*;}


-dontwarn com.squareup.wire.**
-keep class com.squareup.wire.** {*;}
-dontwarn com.umeng.**
-keep class com.umeng.** {*;}
-dontwarn org.android.**
-keep class org.android.** {*;}

-dontwarn com.umeng.analytics.**
-keep class com.umeng.analytics.** {*;}
-dontwarn u.aly.**
-keep class u.aly.** {*;}

-dontwarn com.unionpay.**
-keep class com.unionpay.** {*;}

-dontwarn com.UCMobile.PayPlugin.**
-keep class com.UCMobile.PayPlugin.** {*;}

-dontwarn com.lidroid.xutils.**
-keep class com.lidroid.xutils.** {*;}
#############  library  ##############

#-libraryjars ..\\library_alipay
-dontwarn  com.ailpay.**
-keep class com.ailpay.** { *;}
-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** { *;}
-dontwarn  com.ta.device.**
-keep class com.ta.device.** { *;}
-dontwarn  org.json.alipay.**
-keep class org.json.alipay.** { *;}

#-libraryjars ..\\library_androidshapeimageview
-dontwarn  org.kxml2.**
-keep class org.xmlpull.v1.** { *;}

#-libraryjars ..\\library_sdDialog
-dontwarn  com.sunday.eventbus.**
-keep class com.sunday.eventbus.** { *;}
-dontwarn  de.greenrobot.event.**
-keep class de.greenrobot.event.** { *;}
-dontwarn  com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *;}

#-libraryjars ..\\library_umeng_social
-dontwarn  org.apache.http.entity.mime.**
-keep class org.apache.http.entity.mime.** { *;}
-dontwarn  com.umeng.socialize.**
-keep class com.umeng.socialize.** { *;}
-dontwarn  com.tencent.**
-keep class com.tencent.** { *;}
-dontwarn  assets.**
-keep class assets.** { *;}
-dontwarn  com.sina.sso.**
-keep class com.sina.sso.** { *;}

-dontwarn  io.agora.rtc.**
-keep class io.agora.rtc.** { *;}

-dontwarn  com.kymjs.kjframe.**
-keep class com.kymjs.kjframe.** { *;}
-dontwarn  org.androidannotations.**
-keep class org.androidannotations.** { *;}

-dontwarn android.annotation
-dontwarn com.alipay.euler.**
-keep class com.alipay.euler.** {*;}
-keep class * extends java.lang.annotation.Annotation
-keepclasseswithmembernames class * {
    native <methods>;
}

#-libraryjars ..\\library_ZxingDemo
-dontwarn  com.google.zxing.**
-keep class com.google.zxing.** { *;}

-keep class **.R$* { *; }

-dontwarn  android.support.multidex.**
-keep class android.support.multidex.** { *;}
#xutils的混淆
-keep class * extends java.lang.annotation.Annotation { *; }

-keep public class www.xcd.com.mylibrary.** { *; }

-keep public class com.yonyou.sns.im.callback.OrientationSensor {*;}

-keep public class com.medicinedot.www.medicinedot.wxapi.** {*;}

-keep public class com.medicinedot.www.medicinedot.pay.** {*;}

-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.**{*;}
#友盟的混淆
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**
-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep public class [your_pkg].R$*{
    public static final int *;
}
-dontwarn android.webkit.**
#前景云会议混淆
-dontwarn  us.zoom.**
-keep class us.zoom.** { *;}

-dontwarn  com.dropbox.client2.**
-keep class com.dropbox.client2.** { *;}

-dontwarn  org.json.simple.**
-keep class org.json.simple.** { *;}

-dontwarn  com.microsoft.live.**
-keep class com.microsoft.live.** { *;}

-dontwarn  com.zipow.**
-keep class com.zipow.** { *;}

-dontwarn  org.webrtc.voiceengine.**
-keep class org.webrtc.voiceengine.** { *;}
#在线支付
-dontwarn  com.netfinworks.ofa.**
-keep class com.netfinworks.ofa.** { *;}

-dontwarn  com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}

-dontwarn  org.apache.cordova.**
-keep class org.apache.cordova.** { *;}

-dontwarn  com.chanpay.paysdk.**
-keep class com.chanpay.paysdk.** { *;}
