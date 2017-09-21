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
     * is_member : 2
     * endtime : 
     * count : 1
     * data : [{"uid":"19","utype":"1","name":"Qqqqq","phone":"15727393984","region":"北京市","content":"Qqqqqqq","address":"","sex":"1","headimg":"/uploads/headimg/20170916/30983428c1bcab5fc2051e0989fc7bb3."}]
     */

    private String errorcode;
    private String msg;
    private String is_member;
    private String endtime;
    private String count;
    private List<HomeSupplierinfo.DataBean> data;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<HomeSupplierinfo.DataBean> getData() {
        return data;
    }

    public void setData(List<HomeSupplierinfo.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * uid : 19
         * utype : 1
         * name : Qqqqq
         * phone : 15727393984
         * region : 北京市
         * content : Qqqqqqq
         * address :
         * sex : 1
         * headimg : /uploads/headimg/20170916/30983428c1bcab5fc2051e0989fc7bb3.
         * ronguserId :
         * rongtoken :
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
