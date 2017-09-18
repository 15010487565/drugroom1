package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;

/**
 * Created by Android on 2017/9/17.
 */

public class ErroeInfo implements Serializable {

    /**
     * errorcode : 301
     * msg : 列表查询失败
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
