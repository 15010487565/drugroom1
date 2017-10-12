package www.xcd.com.mylibrary.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2017/9/14.
 */
public class HomeViewPagerImageinfo implements Serializable{

    /**
     * errorcode : 200
     * msg : 查询成功
     * data : [{"image":"213.img"}]
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
         * image : 213.img
         */

        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
