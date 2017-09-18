package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;

/**
 * Created by Android on 2017/8/9.
 */

public class RegisterSuppliercodeinfo implements Serializable{

    /**
     * errorcode : 200
     * msg : 短信发送成功
     * data : 409426
     */

    private String errorcode;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
