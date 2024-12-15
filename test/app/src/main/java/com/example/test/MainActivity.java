package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private int times = 3;
    private int selected = 0;
    private int length = 0;
    List<SliderItem> sliderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);

        viewPager2 = findViewById(R.id.slider);
        sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.im1));
        sliderItems.add(new SliderItem(R.drawable.im2));
        sliderItems.add(new SliderItem(R.drawable.im3));
        sliderItems.add(new SliderItem(R.drawable.im4));
        sliderItems.add(new SliderItem(R.drawable.im5));
        length = sliderItems.size();

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
//        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(0.85f + r * 0.15f);
//            }
//        });
//
//        viewPager2.setPageTransformer(compositePageTransformer);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sliderHandler.postDelayed(sliderRunnable, 100);
                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        Log.d("DEBUG", "onPageSelected: " + position);
                        sliderHandler.removeCallbacks(sliderRunnable);
                        if (position == selected + (times - 1) * length) {
                            return;
                        }
                        sliderHandler.postDelayed(sliderRunnable, 100);
                    }
                });
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
}