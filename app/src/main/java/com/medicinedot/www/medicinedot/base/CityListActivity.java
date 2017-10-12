package com.medicinedot.www.medicinedot.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.bean.CityListAllInfo;
import www.xcd.com.mylibrary.entity.GlobalParam;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;

public class CityListActivity extends SimpleTopbarActivity {
    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        Map<String, Object> params = new HashMap<String, Object>();
        //获取城市雷列表
        params.put("uid", "1");
        okHttpGet(101, GlobalParam.ALLCITYLIST, params);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200) {
            switch (requestCode) {
                case 101:
                    CityListAllInfo cityallinfo = JSON.parseObject(returnData, CityListAllInfo.class);
                    creatCityList(cityallinfo);
                    if (Build.VERSION.SDK_INT >= 23) {
                        int REQUEST_CODE_CONTACT = 101;
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //验证是否许可权限
                        for (String str : permissions) {
                            if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                                //申请权限
                                this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                                return;
                            }
                        }
                    }
                    break;

            }
        } else {
            ToastUtil.showToast(returnMsg);
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
    public void creatCityList(CityListAllInfo cityallinfo){
        try {

            File file = new File(Environment.getExternalStorageDirectory(),
                    "province_data.xml");
            if(file.exists()){
                file.delete();
            }else {
                file.mkdir(); //如果不存在则创建
            }
            FileOutputStream fos = new FileOutputStream(file);
            // 获得一个序列化工具
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "utf-8");
            // 设置文件头
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "root");

            //省集合
            List<CityListAllInfo.DataBean> provincedata = cityallinfo.getData();
            if (provincedata !=null&&provincedata.size()>0){
                for (int i = 0,j = provincedata.size(); i < j; i++) {
                    CityListAllInfo.DataBean  provincedataBean = provincedata.get(i);
                    //省名字
                    String proviceName =  provincedataBean.getProviceName();
                    serializer.startTag(null, "province");
                    serializer.attribute(null, "name", proviceName);
                    //市集合
                    List<CityListAllInfo.DataBean.CityBean> citydata = provincedataBean.getCity();
                    if (citydata !=null&&citydata.size()>0){
                        for (int k = 0,l = citydata.size(); k < l ; k++) {
                            CityListAllInfo.DataBean.CityBean cityBean = citydata.get(k);
                            //市名字
                            String cityName = cityBean.getCityName();
                            serializer.startTag(null, "city");
                            serializer.attribute(null, "name", cityName);
                            //区集合
                            List<CityListAllInfo.DataBean.CityBean.AreaBean> area = cityBean.getArea();
                            if (area !=null&&area.size()>0){
                                for (int m = 0,n =area.size(); m < n; m++) {
                                    //区名字
                                    CityListAllInfo.DataBean.CityBean.AreaBean areaBean = area.get(m);
                                    String areaName = areaBean.getAreaName();
                                    serializer.startTag(null, "district");
                                    serializer.attribute(null, "name", areaName);
                                    serializer.attribute(null, "zipcode",
                                            String.valueOf(i)+String.valueOf(k)+"000");
                                    serializer.endTag(null, "district");
                                }
                            }
                            serializer.endTag(null, "city");
                        }
                    }
                    serializer.endTag(null, "province");
                }
            }
            serializer.endTag(null, "root");
            serializer.endDocument();
            fos.close();
            Log.e("TAG_城市列表", "写入成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG_城市列表", "写入失败");
        }
    }
}
