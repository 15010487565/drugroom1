package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;

/**
 * Created by Android on 2017/9/20.
 */

public class AilPayOrderMoney implements Serializable{

    /**
     * errorcode : 200
     * msg : u4feeu6539u6210u529f
     * data :
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
