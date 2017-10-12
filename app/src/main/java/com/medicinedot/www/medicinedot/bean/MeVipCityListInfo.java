package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2017/8/9.
 */

public class MeVipCityListInfo implements Serializable{

    /**
     * errorcode : 200
     * msg : 查询成功
     * data : [{"city":"北京市","endtime":"2017-10-10 11:12:05","isMember":3},{"city":"天津市","endtime":"2017-10-29 18:19:40","isMember":1}]
     */

    private String errorcode;
    private String msg;
    private List<DataBean> data;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * city : 北京市
         * endtime : 2017-10-10 11:12:05
         * isMember : 3
         */

        private String city;
        private String endtime;
        private String isMember;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getIsMember() {
            return isMember;
        }

        public void setIsMember(String isMember) {
            this.isMember = isMember;
        }
    }
}
