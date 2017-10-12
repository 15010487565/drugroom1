package www.xcd.com.mylibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * Created by xcd15 on 2017/5/9.
 */

public class ClassUtils {
    //判断手机号是否为手机号
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((17[0-9])|(14[5,7])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
    //保留两位小数
    public static String doubledigit(String reString) {
        Double d = Double.valueOf(reString);
        DecimalFormat f1 = new DecimalFormat("######0.00");
        reString = f1.format(d);
        return reString;
    }
//    public static boolean cleanCatchDisk(Context context) {
//        String cachePath = YYStorageUtil.getSystemDisCachePath(context);
//        boolean deleteFolderFile = deleteFolderFile(cachePath, true);
//        boolean deleteFolderFile1 = deleteFolderFile(BaseApplication.getApp().getCacheDir() + "/" + Config.GLIDE_CARCH_DIR, true);
//        return deleteFolderFile&&deleteFolderFile1;
//    }
    // 按目录删除文件夹文件方法
    private static boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFolderFile(file1.getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static String getCacheSize() {
//        try {
//            return getFormatSize(getFolderSize(
//                    new File(BaseApplication.getApp().getCacheDir()
//                            + "/" + Config.GLIDE_CARCH_DIR)));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "获取失败";
//        }
//    }
    // 格式化单位
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
    // 获取指定文件夹内所有文件大小的和
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     *
     * @param context
     * @param mobile  手机号
     * @param isDialInterface 是否跳转本地拨号界面
     */
    public final static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    public static void call(Context context,String mobile,boolean isDialInterface) {
        Log.e("TAG_拨打电话","isDialInterface="+isDialInterface);
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }else{
                //上面已经写好的拨号方法
                callDirectly(context,mobile,isDialInterface);
            }
        } else {
            //上面已经写好的拨号方法
            callDirectly(context,mobile,isDialInterface);
        }

    }
    public static void callDirectly(Context context,String mobile,boolean isDialInterface){
        Intent intent = new Intent();
        if (isDialInterface){
            intent.setAction("android.intent.action.DIAL");
        }else {
            intent.setAction("android.intent.action.CALL");
        }
        intent.setData(Uri.parse("tel:" + mobile));
        context.startActivity(intent);
    }
    public static String getDeviceId(Context context){
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        if (szImei ==null || "".equals(szImei)){
            String serial = null;

            String m_szDevIDShort = "35" +
                    Build.BOARD.length()%10+ Build.BRAND.length()%10 +

                    Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +

                    Build.DISPLAY.length()%10 + Build.HOST.length()%10 +

                    Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +

                    Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +

                    Build.TAGS.length()%10 + Build.TYPE.length()%10 +

                    Build.USER.length()%10 ; //13 位

            try {
                serial = android.os.Build.class.getField("SERIAL").get(null).toString();
                //API>=9 使用serial号
                return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            } catch (Exception exception) {
                //serial需要一个初始化
                serial = "serial"; // 随便一个初始化
            }
            //使用硬件信息拼凑出来的15位号码
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }else {
            return szImei;
        }
    }
    public static void setHintSize(String hintstring,EditText editText){
        if (hintstring==null||"".equals(hintstring)){
            return;
        }
        SpannableString ss = new SpannableString(hintstring);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(16, true);
        // 附加属性到文本
        ss.setSpan(absoluteSizeSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }
}
