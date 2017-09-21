package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;

/**
 * Created by Android on 2017/8/9.
 */

public class RegisterSupplierinfo implements Serializable{


    /**
     * errorcode : 200
     * msg : 注册成功
     * data : {"uid":"50","is_member":"2","utype":"2","token":"2ce72d0873e393369fbd0d1beae063968c0ea968","ronguserId":"59c0be37d2699","rongtoken":"Y3307lWVKYfWaEBTEpJv35laPtZVlHzQ6n0W45Uguqbe6VvMSXB64f3cqCEwWOSnwSyJe2LOmt76+/OxnMTfCLjDyBG0ZxzT"}
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
         * uid : 50
         * is_member : 2
         * utype : 2
         * token : 2ce72d0873e393369fbd0d1beae063968c0ea968
         * ronguserId : 59c0be37d2699
         * rongtoken : Y3307lWVKYfWaEBTEpJv35laPtZVlHzQ6n0W45Uguqbe6VvMSXB64f3cqCEwWOSnwSyJe2LOmt76+/OxnMTfCLjDyBG0ZxzT
         */

        private String uid;
        private String is_member;
        private String utype;
        private String token;
        private String ronguserId;
        private String rongtoken;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getIs_member() {
            return is_member;
        }

        public void setIs_member(String is_member) {
            this.is_member = is_member;
        }

        public String getUtype() {
            return utype;
        }

        public void setUtype(String utype) {
            this.utype = utype;
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
