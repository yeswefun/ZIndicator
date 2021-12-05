package com.z.zindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/*
    IndicatorView
        IndicatorColorTextView
        IndicatorColorTextView
        ...
 */
public class TabActivity extends AppCompatActivity {

    private static final String TAG = "TabActivity";

    private LinearLayout mIndicatorContainer;
    private List<Vtext> mIndicators;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        init();
    }

    private void init() {
        mIndicators = new ArrayList<>();
        mIndicatorContainer = findViewById(R.id.indicator_view);
        mViewPager = findViewById(R.id.view_pager);
        initIndicator();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public @NonNull
            Fragment getItem(int position) {
                return TabFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //container.removeView();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                //Log.e("TAG","position -> "+position +"  positionOffset -> "+positionOffset);
                // position 代表当前的位置
                // positionOffset 代表滚动的 0 - 1 百分比
                // positionOffsetPixels 代表滚动的px

                //Log.d("TAG", "pos: " + position + ", posOffset: " + positionOffset + ", posOffsetPx: " + positionOffsetPixels);
                //D/TAG: pos: 0, posOffset: 0.0, posOffsetPx: 0

                // 1.左边  位置 position
                Vtext left = mIndicators.get(position);
                left.setDirection(false);
                left.setCurPos(1 - positionOffset);

                try {
                    Vtext right = mIndicators.get(position + 1);
                    right.setDirection(true);
                    right.setCurPos(positionOffset);
                } catch (Exception e) {
                    // do nothing ...
                }
            }
        });
    }

    private final String[] items = {"直播", "推荐", "视频", "图片", "段子"};
    //, "精华", "音乐", "电影", "测试1", "测试2", "测试3"};

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            Vtext vtext = new Vtext(this);
            // 设置颜色
            vtext.setTextSize(20);
            vtext.setActiveFontColor(Color.RED);
            vtext.setText(items[i]);
            vtext.setLayoutParams(lp);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(vtext);
            // 加入集合
            mIndicators.add(vtext);
        }
    }
}
