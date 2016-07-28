package com.neworin.photocarousel;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //广告轮播
    private ViewPager mViewPager;
    private ImageView imageView;
    //广告集合
    private List<ImageView> imageViewList = new ArrayList<>();
    private MyPageAdapter myPageAdapter;
    private LinearLayout mDotLayout;
    private int currentPosition;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mDotLayout = (LinearLayout) findViewById(R.id.dot_layout);
    }

    private void initData() {
        int[] images = {R.drawable.cheese_1, R.drawable.cheese_2, R.drawable.cheese_3, R.drawable.cheese_4, R.drawable.cheese_5};
        for (int i = 0; i < images.length; i++) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setBackgroundResource(images[i]);
            imageViewList.add(imageView);
        }
        // 多少个轮播广告就多少个点dot
        for (int i = 0; i < imageViewList.size(); i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {//第一个点不需要左边距
                params.leftMargin = 5;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.dot_selector);
            mDotLayout.addView(view);
        }
        myPageAdapter = new MyPageAdapter(imageViewList);
        mViewPager.setAdapter(myPageAdapter);
    }

    /**
     * 更新dot
     */
    private void updateDot() {
        int currentPage = mViewPager.getCurrentItem() % imageViewList.size();
        for (int i = 0; i < mDotLayout.getChildCount(); i++) {
            mDotLayout.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    private void initListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                updateDot();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            //每隔3秒更新一次
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };
}
