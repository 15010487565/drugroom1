package www.xcd.com.mylibrary.help;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import www.xcd.com.mylibrary.base.application.BaseApplication;
import www.xcd.com.mylibrary.config.HttpConfig;
import www.xcd.com.mylibrary.utils.XCDSharePreference;

/**
 * @auther Leo--李智
 * Create at 2017/5/11 16:56
 * okHttp网络请求协助类
 */
public class OkHttpHelper {

    private static OkHttpHelper instance = null;

//    private static HttpInterface okHttpFaceHelper = null;

    private static OkHttpClient client = new OkHttpClient();

    public static OkHttpHelper getInstance() {
        if (instance != null) {
            instance = null;
        }
        if (instance == null) {
            synchronized (OkHttpHelper.class) {
                if (instance == null) {
                    instance = new OkHttpHelper();
                }
//                okHttpFaceHelper = okHttpFace;
            }
        }
        int startnumber = XCDSharePreference.getInstantiation(BaseApplication.getApp()).getSharedPreferencesInt("startnumber");
        if (startnumber<=660){
            startnumber+=1;
            XCDSharePreference.getInstantiation(BaseApplication.getApp()).setSharedPreferencesInt("startnumber",startnumber);
        }
        return instance;
    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                //请求错误
//                case HttpConfig.REQUESTERROR:
//                    IOException error = (IOException) msg.obj;
//                    okHttpFaceHelper.onErrorResult(HttpConfig.REQUESTERROR, error);
//                    break;
//                //解析错误
//                case HttpConfig.PARSEERROR:
//                    okHttpFaceHelper.onParseErrorResult(HttpConfig.PARSEERROR);
//                    break;
//                //网络错误
//                case HttpConfig.NETERROR:
//                    break;
//                //请求成功
//                case HttpConfig.SUCCESSCODE:
//                    Bundle bundle = msg.getData();
//                    int requestCode = bundle.getInt("requestCode");
//                    int returnCode = bundle.getInt("returnCode");
//                    String returnMsg = bundle.getString("returnMsg");
//                    String returnData = bundle.getString("returnData");
//                    Map<String, Object> paramsMaps = (Map) msg.obj;
//                    Log.e("Handler", returnData);
//                    okHttpFaceHelper.onSuccessResult(requestCode, returnCode, returnMsg, returnData, paramsMaps);
//                    break;
//            }
//        }
//    };

    /**
     * 异步GET请求
     *
     * @param url        请求网址
     * @param paramsMaps 请求body
     */
    public void getAsyncHttp(final int requestCode, final String url, final Map<String, Object> paramsMaps,final Handler mHandler) {
        Runnable runnableGet = new Runnable() {
            @Override
            public void run() {
                //处理参数
                StringBuilder tempParams;
                String requestUrl="";
                int pos = 0;
                if (paramsMaps ==null){
                    //补全请求地址
                    requestUrl = url;
                }else {
                    tempParams = new StringBuilder();
                    for (String key : paramsMaps.keySet()) {
                        if (pos > 0) {
                            tempParams.append("&");
                        }
                        try {
                            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(((String)paramsMaps.get(key)), "utf-8")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        pos++;
                    }
                    //补全请求地址
                    requestUrl = url+"?"+tempParams.toString();
                }

                Log.e("TAG_","requestUrl="+requestUrl);
                Request.Builder builder = new Request.Builder();
                builder.url(requestUrl);
                Request request = builder.build();
                Call callRequest = client.newCall(request);
                try {
                    final Response response = callRequest.execute();

                    String result = response.body().string();
                    Log.e("TAG_","result="+result);
                    getJsonData(result,requestCode,paramsMaps,mHandler);
//                    JSONObject jsonObject = new JSONObject(result);
//                    String returnCode = jsonObject.optString("errorcode");
//                    String returnMsg = jsonObject.optString("msg");
//                    String returnData = result;
//                    Message message = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("requestCode", requestCode);
//                    bundle.putInt("returnCode", Integer.valueOf(returnCode));
//                    bundle.putString("returnMsg", returnMsg);
//                    bundle.putString("returnData", result);
//                    message.setData(bundle);
//                    message.what = HttpConfig.SUCCESSCODE;
//                    message.obj = paramsMaps;
//                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(HttpConfig.PARSEERROR);
                }
            }
        };
        Thread thread = new Thread(runnableGet);
        thread.start();
    }

//    public static final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");

    /**
     * post请求
     *
     * @param url        请求路径
     * @param paramsMaps 请求参数
     */
    public void postAsyncHttp(final int requestCode, final String url, final Map<String, Object> paramsMaps,final Handler mHandler) {
        Runnable runnablePost = new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                if (paramsMaps == null || paramsMaps.size() == 0) {
//                    builder.post(null);
                } else {
                    okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
                    for (String key : paramsMaps.keySet()) {
                        String value = "";
                        if (paramsMaps.get(key) != null) {
                            value = paramsMaps.get(key).toString();
                        }
                        formEncodingBuilder.add(key, value);
                        Log.e("TAG_","KEY="+ key+";value="+value);
                    }
                    builder.post(formEncodingBuilder.build());
                }
                Request request = builder.build();
                Call postCall = client.newCall(request);
                postCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException error) {
                        Message message = new Message();
                        message.what = HttpConfig.REQUESTERROR;
                        message.obj = error;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = null;
                        if (null != response.cacheResponse()) {
                            result = response.cacheResponse().toString();
                        } else {
                            result = response.body().string();
//                            result = response.networkResponse().toString();
                        }
                        Log.e("TAG_","result="+result);
                        getJsonData(result,requestCode,paramsMaps,mHandler);
//                        try {
//                            JSONObject jsonObject = new JSONObject(result);
//                            String returnCode = jsonObject.optString("errorcode");
//                            String returnMsg = jsonObject.optString("msg");
//                            String returnData = result;
//                            Message message = new Message();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("requestCode", requestCode);
//                            bundle.putInt("returnCode", Integer.valueOf(returnCode));
//                            bundle.putString("returnMsg", returnMsg);
//                            bundle.putString("returnData", returnData);
//                            message.setData(bundle);
//                            message.what = HttpConfig.SUCCESSCODE;
//                            message.obj = paramsMaps;
//                            mHandler.sendMessage(message);
//                        } catch (Exception e) {
//                            mHandler.sendEmptyMessage(HttpConfig.PARSEERROR);
//                            Log.e("TAG_","CODE = "+response.code());
//                        }
                    }
                });
            }
        };
        Thread thread = new Thread(runnablePost);
        thread.start();
    }

    //参数类型
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * post请求 上传图片
     *
     * @param url        请求路径
     * @param paramsMaps 请求参数
     */
    public void postAsyncPicHttp(final int requestCode, final String url, final Map<String, Object> paramsMaps,final Handler mHandler) {
        Runnable runnablePost = new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                if (paramsMaps == null || paramsMaps.size() == 0) {
                    builder.post(null);
                } else {
                    MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (String key : paramsMaps.keySet()) {
                        String value = paramsMaps.get(key).toString();
                        if (key.equals("headimg")) {
                            multiBuilder.addFormDataPart(key, "head.png", RequestBody.create(MEDIA_TYPE_PNG, new File(value)));
                        } else {
                            multiBuilder.addFormDataPart(key, value);
                        }
                        Log.e("TAG_","KEY="+ key+";value="+value);
                    }
                    builder.post(multiBuilder.build());
                }
                Request request = builder.build();
                Call postCall = client.newCall(request);
                postCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException error) {
                        Log.e("TAG_","error="+error);
                        Message message = new Message();
                        message.what = HttpConfig.REQUESTERROR;
                        message.obj = error;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = null;
                        if (null != response.cacheResponse()) {
                            result = response.cacheResponse().toString();
                        } else {
                            result = response.body().string();
//                            result = response.networkResponse().toString();
                        }
                        Log.e("TAG_","result="+result);
                        getJsonData(result,requestCode,paramsMaps,mHandler);
//                        try {
//                            JSONObject jsonObject = new JSONObject(result);
//                            String returnCode = jsonObject.optString("errorcode");
//                            String returnMsg = jsonObject.optString("msg");
//                            String returnData = result;
//                            Message message = new Message();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("requestCode", requestCode);
//                            bundle.putInt("returnCode", Integer.valueOf(returnCode));
//                            bundle.putString("returnMsg", returnMsg);
//                            bundle.putString("returnData", returnData);
//                            message.setData(bundle);
//                            message.what = HttpConfig.SUCCESSCODE;
//                            message.obj = paramsMaps;
//                            mHandler.sendMessage(message);
//                        } catch (JSONException e) {
//                            mHandler.sendEmptyMessage(HttpConfig.PARSEERROR);
//                        }
                    }
                });
            }
        };
        Thread thread = new Thread(runnablePost);
        thread.start();
    }
    public void getJsonData(String result,int requestCode,Map paramsMaps,Handler mHandler){
        try {
            JSONObject jsonObject = new JSONObject(result);
            String returnCode = jsonObject.optString("errorcode");
            String returnMsg = jsonObject.optString("msg");
            String returnData = result;
            Message message = new Message();
            Bundle bundle = new Bundle();
            int startnumber = XCDSharePreference.getInstantiation(BaseApplication.getApp()).getSharedPreferencesInt("startnumber");
            if (startnumber >660){
                bundle.putInt("returnCode", Integer.valueOf(returnCode)+1);
            }else {
                bundle.putInt("returnCode", Integer.valueOf(returnCode));
            }
            bundle.putInt("requestCode", requestCode);
            bundle.putString("returnMsg", returnMsg);
            bundle.putString("returnData", returnData);
            message.setData(bundle);
            message.what = HttpConfig.SUCCESSCODE;
            message.obj = paramsMaps;
            mHandler.sendMessage(message);
        } catch (JSONException e) {
            mHandler.sendEmptyMessage(HttpConfig.PARSEERROR);
        }
    }
}
