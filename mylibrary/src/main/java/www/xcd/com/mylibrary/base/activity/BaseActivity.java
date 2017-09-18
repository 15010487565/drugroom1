package www.xcd.com.mylibrary.base.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.utils.AppManager;
import www.xcd.com.mylibrary.utils.SystemBarTintManager;


/**薛传东
 * Created by xcd15 on 2017/5/3.
 */

public abstract class BaseActivity  extends FragmentActivity {

    private boolean isActive = true;
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS
            ,Manifest.permission.WRITE_CONTACTS
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.ACCESS_COARSE_LOCATION
            ,Manifest.permission.CAMERA
            ,Manifest.permission.ACCESS_COARSE_LOCATION
            ,Manifest.permission.RECORD_AUDIO
            ,Manifest.permission.READ_PHONE_STATE
            ,Manifest.permission.SEND_SMS
            ,Manifest.permission.RECEIVE_SMS
            ,Manifest.permission.READ_SMS
            ,Manifest.permission.CALL_PHONE

    };
    private SystemBarTintManager tintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(R.color.top_bar_background);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
        }
        super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    /**
     * 获取当前页面需要的权限
     *
     * @return
     */
    protected String[] getPermissions() {
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isActive = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    public boolean activityIsActivity() {
        return isActive;
    }

    @Override
    public void finish() {
        super.finish();
        AppManager.getInstance().removeActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView();
    }



    protected void afterSetContentView() {

    }

    /**
     * 根据resId获得一个Bitmap
     *
     * @param resId
     * @return
     */
    public Bitmap getDrawableBitmap(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        } else {
            return null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyDeatchView();
    }
    protected void onDestroyDeatchView() {
    }
}
