package com.neworin.photocarousel;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Project:</b> PhotoCarousel<br>
 * <b>Create Date:</b> 16/9/22<br>
 * <b>Author:</b> NewOrin<br>
 * <b>Description:</b>
 */

public class PhotoCarousel extends FrameLayout {

    private List<ImageView> mPhotoIVList;
    private List<ImageView> mDotIVList;
    private ViewPager mViewPager;
    private Context mContext;
    private LinearLayout mDotLinearLayout;
    private int delaySeconds = 2000;
    private int count;
    private int currentItem;
    private Handler mHandler;
    private boolean isAutoPlay;

    public PhotoCarousel(Context context) {
        this(context, null);
    }

    public PhotoCarousel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoCarousel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initData();
    }

    private void initData() {
        mPhotoIVList = new ArrayList<>();
        mDotIVList = new ArrayList<>();
        mHandler = new Handler();
    }

    private void initLayout() {
        mPhotoIVList.clear();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.carousel_layout, this, true);
        mViewPager = (ViewPager) mView.findViewById(R.id.carousel_viewpager);
        mDotLinearLayout = (LinearLayout) mView.findViewById(R.id.ll_dot);
        mDotLinearLayout.removeAllViews();
    }

    private void initImgFromRes(int[] imagesRes) {
        count = imagesRes.length;
        for (int i = 0; i < count; i++) {
            ImageView iv_dot = new ImageView(mContext);
            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLayoutParams.leftMargin = 5;
            mLayoutParams.rightMargin = 5;
            iv_dot.setImageResource(R.drawable.dot_unfocus);
            mDotLinearLayout.addView(iv_dot, mLayoutParams);
            mDotIVList.add(iv_dot);
        }
        mDotIVList.get(0).setImageResource(R.drawable.dot_focus);
        for (int i = 0; i <= count; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            if (i == 0) {
                iv.setImageResource(imagesRes[count - 1]);
            } else if (i == count + 1) {
                iv.setImageResource(imagesRes[i - 1]);
            }
            mPhotoIVList.add(iv);
        }
    }

    private void showTime() {
        mViewPager.setAdapter(new CarouselPagerAdapter());
        mViewPager.setFocusable(true);
        mViewPager.setCurrentItem(0);
        currentItem = 1;
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mHandler.postDelayed(mRunnable, delaySeconds);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    mViewPager.setCurrentItem(currentItem, false);
                    mHandler.post(mRunnable);
                } else {
                    mViewPager.setCurrentItem(currentItem);
                    mHandler.postDelayed(mRunnable, delaySeconds);
                }
            } else {
                mHandler.postDelayed(mRunnable, delaySeconds);
            }
        }
    };

    public void setImageRes(int[] imagesRes) {
        initLayout();
        initImgFromRes(imagesRes);
        showTime();
    }

    class CarouselPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPhotoIVList.get(position));
            return mPhotoIVList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPhotoIVList.get(position));
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mDotIVList.size(); i++) {
                if (i == position - 1) {
                    mDotIVList.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    mDotIVList.get(i).setImageResource(R.drawable.dot_unfocus);
                }
            }
        }


        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 0: {
                    if (mViewPager.getCurrentItem() == 0) {
                        mViewPager.setCurrentItem(count, false);
                    } else if (mViewPager.getCurrentItem() == count + 1) {
                        mViewPager.setCurrentItem(1, false);
                    }
                    currentItem = mViewPager.getCurrentItem();
                    isAutoPlay = true;
                    break;
                }
                case 1: {
                    isAutoPlay = false;
                    break;
                }
                case 2: {
                    isAutoPlay = true;
                    break;
                }
            }
        }
    }
}
