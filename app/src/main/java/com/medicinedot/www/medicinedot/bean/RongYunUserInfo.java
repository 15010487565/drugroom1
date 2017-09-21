package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;

/**
 * Created by Android on 2017/9/19.
 */

public class RongYunUserInfo implements Serializable{

    /**
     * errorcode : 200
     * msg : 登录成功
     * data : {"uid":"49","utype":"1","name":"15510495865","phone":"15510495865","region":"--","content":"","address":"","sex":"","headimg":"","token":"385fa72852dd9160cee4e039ea0bdbb23ce202c5","ronguserId":"59c0be0fb151b","rongtoken":"E3TxnbyS2TyZIrOz5ky/Kgb3vh6SIllgqxhLeu85kFMZZOXCU9QEL6hwknG+ajuzEIqS2Dqb+x31bprEl0yPJirYIM/qYJLv9zGdlQfZObM="}
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
         * uid : 49
         * utype : 1
         * name : 15510495865
         * phone : 15510495865
         * region : --
         * content :
         * address :
         * sex :
         * headimg :
         * token : 385fa72852dd9160cee4e039ea0bdbb23ce202c5
         * ronguserId : 59c0be0fb151b
         * rongtoken : E3TxnbyS2TyZIrOz5ky/Kgb3vh6SIllgqxhLeu85kFMZZOXCU9QEL6hwknG+ajuzEIqS2Dqb+x31bprEl0yPJirYIM/qYJLv9zGdlQfZObM=
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
        private String token;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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
