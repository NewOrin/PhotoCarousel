package com.neworin.photocarousel;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/7/28.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class MyPageAdapter extends PagerAdapter {

    private List<ImageView> imageViewList;
    private String TAG = "MyPageAdapter";

    public MyPageAdapter(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = imageViewList.get(position % imageViewList.size());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "点击了第" + (position % 5) + 1 + "张图片");
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
