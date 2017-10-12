package www.xcd.com.mylibrary.base.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.entity.HomeViewPagerImageinfo;

/**
 * Created by Android on 2017/9/29.
 */

public class CarouselView extends LinearLayout {

    private Context context;

    private List<HomeViewPagerImageinfo.DataBean> carouselDataList;

    private ViewPager viewPager;

    private TextView tv_title;

    private LinearLayout dotLayout;

    private List<View> dotList;   //指示点

    private static final int MSG_UPDATE = 1;


    private Handler handler;


    public CarouselView(Context context) {
        this(context, null);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_carousel, this, true);
        initView();
        initData();

    }

    private void initData() {
        dotList = new ArrayList<>();
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_title = (TextView) findViewById(R.id.tv_title);
        dotLayout = (LinearLayout) findViewById(R.id.dot_layout);
    }


    public void start(List<HomeViewPagerImageinfo.DataBean> carouselDataList) {
        this.carouselDataList = carouselDataList;
        if (this.carouselDataList == null || this.carouselDataList.size() < 1) {
            return;
        }
        View view = null;
        LayoutParams params = new LayoutParams(5, 5);
        //根据轮播图要显示的数量来创建指示点的个数
        for (int i = 0; i < this.carouselDataList.size(); i++) {
            view = new View(context);
            //设置dot的宽和高,相当于在xml中设置layout_height和layout_width
            if (i != 0) {  //设置左边距
                params.leftMargin = 5;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.carousel_dot_selector);
            dotList.add(view);  //加入到list集合中
            dotLayout.addView(view);  //加入父布局
        }

        viewPager.setAdapter(new MyPagerAdapter());

        viewPager.setOnPageChangeListener(new MyPagerChangeListener());

        updateTitleDot();

        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case MSG_UPDATE:
                            int currentItem = viewPager.getCurrentItem();
                            if (currentItem < CarouselView.this.carouselDataList.size() - 1) {  //从0开始
                                currentItem++;
                            } else {
                                currentItem = 0;
                            }
                            viewPager.setCurrentItem(currentItem);
                            handler.sendEmptyMessageDelayed(MSG_UPDATE, 2000);
                            break;
                    }

                }
            };
            handler.sendEmptyMessageDelayed(MSG_UPDATE, 2000);
        }



    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return carouselDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;

        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            HomeViewPagerImageinfo.DataBean carouselData = carouselDataList.get(position);
            ImageView imageView = new ImageView(context);
//            imageView.setImageResource(carouselData.getImage());
            Glide.with(context.getApplicationContext())
                    .load(GlobalParam.IP+carouselData.getImage())
                    .centerCrop()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.album_photo_default)
                    .error(R.mipmap.album_photo_default)
                    .into(imageView);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickCallback.onClick(position, position);
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            updateTitleDot();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void updateTitleDot() {
        int currentPosition = viewPager.getCurrentItem() % carouselDataList.size();
        HomeViewPagerImageinfo.DataBean dataBean = carouselDataList.get(currentPosition);
        tv_title.setText(currentPosition+"");
        for (int i = 0; i < carouselDataList.size(); i++) {
            dotLayout.getChildAt(i).setEnabled(i == currentPosition);
        }
    }


    ClickCallback clickCallback;

    public interface ClickCallback {
        void onClick(int id, int position);
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}