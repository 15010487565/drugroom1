package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2017/8/9.
 */

public class SettingAboutInfo implements Serializable{

    /**
     * errorcode : 200
     * msg : 查询成功
     * data : [{"image":"1.jpg","version":"v1.0231","content":"测试内容","phone":"010-88888888"}]
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
         * image : 1.jpg
         * version : v1.0231
         * content : 测试内容
         * phone : 010-88888888
         */

        private String image;
        private String version;
        private String content;
        private String phone;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
