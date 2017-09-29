package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2017/9/11.
 */

public class InformationInfo implements Serializable {
    /**
     * errorcode : 200
     * msg : 查询成功
     * data : [{"id":"1","type":"1","time":"2017-09-11 10:47:25","title":"测试","image":"1.jpg","content":"都能打时间大牛哈伦裤骄傲山东矿机"}]
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

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * type : 1
         * time : 2017-09-11 10:47:25
         * title : 测试
         * image : 1.jpg
         * content : 都能打时间大牛哈伦裤骄傲山东矿机
         */

        private String id;
        private String type;
        private String time;
        private String title;
        private String image;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
