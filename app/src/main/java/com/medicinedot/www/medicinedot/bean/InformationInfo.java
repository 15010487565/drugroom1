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
     * data : [{"id":"7","type":"2","time":"2017-09-28 17:38:51","title":"czxc","image":"/uploads/message/20170928/1c560d142f8e624a06c8ec710e525f44.jpg","content":"czxcxzc","url":"/index/news/message?id=7"},{"id":"6","type":"1","time":"2017-09-28 13:52:47","title":"测试，无图片","image":"","content":"测试 无图片","url":"/index/news/message?id=6"},{"id":"5","type":"1","time":"2017-09-27 20:49:02","title":"测试数据","image":"/uploads/message/20170927/7a82f7e590161574693de17d192d1fda.jpg","content":"这是测试数据","url":"/index/news/message?id=5"},{"id":"1","type":"1","time":"2017-09-11 10:47:25","title":"测试","image":"/uploads/1.jpg","content":"都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机都能打时间大牛哈伦裤骄傲山东矿机","url":"/index/news/message?id=1"},{"id":"2","type":"1","time":"2017-09-11 10:47:25","title":"测试","image":"/uploads/1.jpg","content":"dd","url":"/index/news/message?id=2"},{"id":"3","type":"1","time":"2017-09-11 10:47:25","title":"测试2","image":"/uploads/1.jpg","content":"大声道","url":"/index/news/message?id=3"}]
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
         * id : 7
         * type : 2
         * time : 2017-09-28 17:38:51
         * title : czxc
         * image : /uploads/message/20170928/1c560d142f8e624a06c8ec710e525f44.jpg
         * content : czxcxzc
         * url : /index/news/message?id=7
         */

        private String id;
        private String type;
        private String time;
        private String title;
        private String image;
        private String content;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
