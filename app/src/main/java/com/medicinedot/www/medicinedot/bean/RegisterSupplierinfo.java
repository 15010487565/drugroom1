package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;

/**
 * Created by Android on 2017/8/9.
 */

public class RegisterSupplierinfo implements Serializable{


    /**
     * errorcode : 200
     * msg : 注册成功
     * data : {"uid":"7","utype":"2","token":"47ff3003875ce78c891f3d130b63368cedb51d0c"}
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
         * uid : 7
         * utype : 2
         * token : 47ff3003875ce78c891f3d130b63368cedb51d0c
         */

        private String uid;
        private String utype;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
