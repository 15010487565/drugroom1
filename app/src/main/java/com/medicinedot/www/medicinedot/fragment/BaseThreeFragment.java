package com.medicinedot.www.medicinedot.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.medicinedot.www.medicinedot.bean.CityListAllInfo;
import com.medicinedot.www.medicinedot.threelevelganged.CityModel;
import com.medicinedot.www.medicinedot.threelevelganged.DistrictModel;
import com.medicinedot.www.medicinedot.threelevelganged.ProvinceModel;
import com.medicinedot.www.medicinedot.threelevelganged.XmlParserHandler;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import www.xcd.com.mylibrary.activity.PermissionsActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.utils.ToastUtil;

import static www.xcd.com.mylibrary.activity.PermissionsActivity.PERMISSIONS_GRANTED;


public class BaseThreeFragment extends BaseFragment {

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    public boolean checkupPermissions(String... permissions) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            PermissionsActivity.startActivityForResult(getActivity()
                    , PERMISSIONS_GRANTED, permissions);
        return false;
        }else {
            Log.e("TAG_获取列表","已执行");

            Map<String, Object> params = new HashMap<String, Object>();
            //获取城市雷列表
            params.put("uid", "1");
            okHttpGet(101, GlobalParam.ALLCITYLIST, params);
            return true;
        }
    }

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        try {
            File path = new File(Environment.getExternalStorageDirectory(),
                    "province_data.xml");
            FileInputStream input = new FileInputStream(path);
            //        AssetManager asset = getAssets();
//            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && provinceList.size() > 0) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    if (districtList != null && !districtList.isEmpty()) {
                        mCurrentDistrictName = districtList.get(0).getName();
                        mCurrentZipCode = districtList.get(0).getZipcode();
                    }
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    if (districtList != null && districtList.size() > 0) {
                        String[] distrinctNameArray = new String[districtList.size()];
                        DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                        for (int k = 0; k < districtList.size(); k++) {
                            // 遍历市下面所有区/县的数据
                            DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                            // 区/县对于的邮编，保存到mZipcodeDatasMap
                            mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                            distrinctArray[k] = districtModel;
                            distrinctNameArray[k] = districtModel.getName();
                        }
                        // 市-区/县的数据，保存到mDistrictDatasMap
                        mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    protected void initProvinceDatas(List<CityListAllInfo.DataBean> provinceList) {

        try {
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && provinceList.size() > 0) {
                CityListAllInfo.DataBean provincedataBean = provinceList.get(0);
                mCurrentProviceName = provincedataBean.getProviceName();
                List<CityListAllInfo.DataBean.CityBean> cityList = provincedataBean.getCity();
                if (cityList != null && !cityList.isEmpty()) {
                    CityListAllInfo.DataBean.CityBean cityBean = cityList.get(0);
                    mCurrentCityName = cityBean.getCityName();
                    List<CityListAllInfo.DataBean.CityBean.AreaBean> districtList = cityBean.getArea();
                    if (districtList != null && !districtList.isEmpty()) {
                        CityListAllInfo.DataBean.CityBean.AreaBean districtModel = districtList.get(0);
                        mCurrentDistrictName = districtModel.getAreaName();
//                        mCurrentZipCode = districtModel.getZipcode();
                    }
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getProviceName();
                List<CityListAllInfo.DataBean.CityBean> cityList = provinceList.get(i).getCity();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getCityName();
                    List<CityListAllInfo.DataBean.CityBean.AreaBean> districtList = cityList.get(j).getArea();
                    if (districtList != null && districtList.size() > 0) {
                        String[] distrinctNameArray = new String[districtList.size()];
                        DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
//                        for (int k = 0; k < districtList.size(); k++) {
//                            // 遍历市下面所有区/县的数据
//                            DistrictModel districtModel = new DistrictModel(districtList.get(k).getAreaName(), districtList.get(k).getZipcode());
//                            // 区/县对于的邮编，保存到mZipcodeDatasMap
//                            mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
//                            distrinctArray[k] = districtModel;
//                            distrinctNameArray[k] = districtModel.getName();
//                        }
                        // 市-区/县的数据，保存到mDistrictDatasMap
                        mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getProviceName(), cityNames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
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
                            if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
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

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("TAG_读写权限", "requestCode=" + requestCode );
        switch (requestCode) {
            case PERMISSIONS_GRANTED:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    //获取城市雷列表
                    params.put("uid", "1");
                    okHttpGet(101, GlobalParam.ALLCITYLIST, params);
                } else {
                    // Permission Denied
                    ToastUtil.showToast("请允许相关权限再进行下一步操作！");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void creatCityList(CityListAllInfo cityallinfo) {
        try {

            File file = new File(Environment.getExternalStorageDirectory(),
                    "province_data.xml");
            if (file.exists()) {
                file.delete();
            } else {
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
            if (provincedata != null && provincedata.size() > 0) {
                for (int i = 0, j = provincedata.size(); i < j; i++) {
                    CityListAllInfo.DataBean provincedataBean = provincedata.get(i);
                    //省名字
                    String proviceName = provincedataBean.getProviceName();
                    serializer.startTag(null, "province");
                    serializer.attribute(null, "name", proviceName);
                    //市集合
                    List<CityListAllInfo.DataBean.CityBean> citydata = provincedataBean.getCity();
                    if (citydata != null && citydata.size() > 0) {
                        for (int k = 0, l = citydata.size(); k < l; k++) {
                            CityListAllInfo.DataBean.CityBean cityBean = citydata.get(k);
                            //市名字
                            String cityName = cityBean.getCityName();
                            serializer.startTag(null, "city");
                            serializer.attribute(null, "name", cityName);
                            //区集合
                            List<CityListAllInfo.DataBean.CityBean.AreaBean> area = cityBean.getArea();
                            if (area != null && area.size() > 0) {
                                for (int m = 0, n = area.size(); m < n; m++) {
                                    //区名字
                                    CityListAllInfo.DataBean.CityBean.AreaBean areaBean = area.get(m);
                                    String areaName = areaBean.getAreaName();
                                    serializer.startTag(null, "district");
                                    serializer.attribute(null, "name", areaName);
                                    serializer.attribute(null, "zipcode",
                                            String.valueOf(i) + String.valueOf(k) + "000");
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
