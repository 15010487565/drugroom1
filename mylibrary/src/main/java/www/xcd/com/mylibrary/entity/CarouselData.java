package www.xcd.com.mylibrary.entity;

/**
 * Created by Android on 2017/9/29.
 */

public class CarouselData {
    private int id;

    private String title;

    private int resId;

    public CarouselData(int id, String title, int resId) {
        this.id = id;
        this.title = title;
        this.resId = resId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "CarouselData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", resId=" + resId +
                '}';
    }
}