package www.xcd.com.mylibrary.base.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import www.xcd.com.mylibrary.utils.AppManager;


/**
 * application基类
 *
 * @author litfb
 * @version 1.0
 * @date 2014年9月10日
 */
public class BaseApplication extends Application {
    private static BaseApplication mBaseApplication;

    public static BaseApplication getApp() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        SDKInitializer.initialize(getApplicationContext());
        initView();
//        String versionName = null;
//        try {
////            versionName = getVersionName();
////            Context appContext = getApplicationContext();
////            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(appContext);
////            strategy.setAppVersion(versionName);
////            CrashReport.initCrashReport(appContext, "1e75ac81e8", false,strategy);
////            //初始化第三方jar
////            YYIMChat.getInstance().init(getApplicationContext());
////            YYIMChat.getInstance().configLogger(Log.VERBOSE, true, true, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例  
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息  
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    private void initView() {
        mBaseApplication = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_RUNNING_CRITICAL://内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
            case TRIM_MEMORY_RUNNING_LOW://内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
            case TRIM_MEMORY_RUNNING_MODERATE://内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
                System.gc();
                break;
            default:
                break;
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            // 杀死进程前需要关闭app 相关的服务，activity
            AppManager.getInstance().finishAllActivity();
            //停止所有服务
//            stopService(new Intent(getApplicationContext(), YYIMService.class));
            ActivityManager activityMgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(getPackageName());
        } catch (Exception e) {
//            YYIMLogger.d(e);
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
