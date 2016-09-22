package com.neworin.photocarousel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    private PhotoCarousel mPhotoCarousel;
    int[] images = {R.drawable.cheese_1, R.drawable.cheese_2, R.drawable.cheese_3, R.drawable.cheese_4, R.drawable.cheese_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mPhotoCarousel = (PhotoCarousel) findViewById(R.id.photo_carousel);
        mPhotoCarousel.setImageRes(images);
    }
}
