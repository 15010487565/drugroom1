package www.xcd.com.mylibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Android on 2017/5/19.
 */

public class XCDSharePreference {
    public static Context context;
    public static XCDSharePreference getInstantiation(Context context){
        XCDSharePreference instantiation = new XCDSharePreference(context);
        return instantiation;
    }
    public XCDSharePreference(Context context) {
        this.context = context;

    }

    /**
     * 保     *
     */
    public static void setSharedPreferences(String key, String value) {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= context.getSharedPreferences("drugroomsp",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString(key, value);
        //提交当前数据
        editor.commit();
    }

    /**
     *  获取各项配置参数
     */
    public static String getSharedPreferences(String key) {
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= context.getSharedPreferences("drugroomsp",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String value =sharedPreferences.getString(key, "");
        return value;
    }
    /**
     * 保     *
     */
    public static void setSharedPreferencesInt(String key, int value) {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= context.getSharedPreferences("drugroomsp",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putInt(key, value);
        //提交当前数据
        editor.commit();
    }

    /**
     *  获取各项配置参数
     */
    public static int getSharedPreferencesInt(String key) {
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= context.getSharedPreferences("drugroomsp",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        int value =sharedPreferences.getInt(key, 1);
        return value;
    }
}
