package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2017/8/9.
 */

public class HomeSupplierinfo implements Serializable{


    /**
     * errorcode : 200
     * msg : 查询成功
     * page : 1
     * is_member : 2
     * endtime :
     * day : 0
     * count : 7
     * data : [{"uid":"153","utype":"1","name":"韩先生","phone":"18764820001","region":"北京市","content":"是个狗狗狗狗狗狗狗狗","address":"","sex":"1","headimg":"/uploads/headimg/20171012/302dfd87b34c3b1e43b1c404d8502084.","ronguserId":"59cdc7b778357","rongtoken":"QSS8oaF5rBUY2qFFFdOdiJ9TdHVM4Zx8SvtOB+e/datq7vJvLgDRmuM45HqYLAFenmt8C9o8EceB1xMfw93zAmzxhMoq0S4kPcgppDozWo8="},{"uid":"156","utype":"1","name":"梅子","phone":"18510286626","region":"北京市","content":"","address":"","sex":"","headimg":"","ronguserId":"59db0ef009ffd","rongtoken":"Oz0wukRa0xmUOZx9ABP8D59TdHVM4Zx8SvtOB+e/datENYlfpa0WqdrjCiz54gDd5bUR1ixNc3E1ldiHl3NC9YfZJpk9hbLyPcgppDozWo8="}]
     */

    private String errorcode;
    private String msg;
    private String page;
    private String is_member;
    private String endtime;
    private String day;
    private String count;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIs_member() {
        return is_member;
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 153
         * utype : 1
         * name : 韩先生
         * phone : 18764820001
         * region : 北京市
         * content : 是个狗狗狗狗狗狗狗狗
         * address :
         * sex : 1
         * headimg : /uploads/headimg/20171012/302dfd87b34c3b1e43b1c404d8502084.
         * ronguserId : 59cdc7b778357
         * rongtoken : QSS8oaF5rBUY2qFFFdOdiJ9TdHVM4Zx8SvtOB+e/datq7vJvLgDRmuM45HqYLAFenmt8C9o8EceB1xMfw93zAmzxhMoq0S4kPcgppDozWo8=
         */

        private String uid;
        private String utype;
        private String name;
        private String phone;
        private String region;
        private String content;
        private String address;
        private String sex;
        private String headimg;
        private String ronguserId;
        private String rongtoken;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUtype() {
            return utype;
        }

        public void setUtype(String utype) {
            this.utype = utype;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getRonguserId() {
            return ronguserId;
        }

        public void setRonguserId(String ronguserId) {
            this.ronguserId = ronguserId;
        }

        public String getRongtoken() {
            return rongtoken;
        }

        public void setRongtoken(String rongtoken) {
            this.rongtoken = rongtoken;
        }
    }
}
