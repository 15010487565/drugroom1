package com.medicinedot.www.medicinedot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2017/9/11.
 */

public class ChatSupplierInfo implements Serializable {

    /**
     * errorcode : 200
     * msg : u4feeu6539u6210u529f
     * data : [{"type":"1","time":"2013-12-31","title":"u6b64u6761u6d88u606f","image":"url","content":"u70b9u51fbuff0cu5219u4e0du663eu793au201cu67e5u770bu8be6u60c5u6309u94ae"},{"type":"1","time":"2013-12-31","title":"u6b64u6761u6d88u606f","image":"url","content":"u70b9u51fbu201cu67e5u770bu8be6u60c5u201du8df3u8f6cu81f3u5bf9u5e94u9875u9762u3002u82e5u8be5u6761u6d88u606fu65e0u5bf9u5e94u8be6u60c5u9875u9762uff0cu5219u4e0du663eu793au201cu67e5u770bu8be6u60c5u201du6309u94ae"}]
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
         * type : 1
         * time : 2013-12-31
         * title : u6b64u6761u6d88u606f
         * image : url
         * content : u70b9u51fbuff0cu5219u4e0du663eu793au201cu67e5u770bu8be6u60c5u6309u94ae
         */

        private String type;
        private String time;
        private String title;
        private String image;
        private String content;

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
