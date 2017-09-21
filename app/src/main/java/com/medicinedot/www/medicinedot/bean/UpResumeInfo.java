package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;

/**
 * Created by Android on 2017/9/20.
 */

public class UpResumeInfo implements Serializable{

    /**
     * errorcode : 200
     * msg : 信息更新成功
     * data : {"uid":"55","utype":"1","name":"1572000","region":"天津-天津市","content":"购物","address":"","sex":"1","headimg":"/uploads/headimg/20170919/f23831264e02b3fc5d0f5d38941ef5ff.png","token":"d690b2e509483da3e565650403c7fb81d8dc7f40"}
     */

    private String errorcode;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 55
         * utype : 1
         * name : 1572000
         * region : 天津-天津市
         * content : 购物
         * address :
         * sex : 1
         * headimg : /uploads/headimg/20170919/f23831264e02b3fc5d0f5d38941ef5ff.png
         * token : d690b2e509483da3e565650403c7fb81d8dc7f40
         */

        private String uid;
        private String utype;
        private String name;
        private String region;
        private String content;
        private String address;
        private String sex;
        private String headimg;
        private String token;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
