package com.yuyh.cavaliers.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.ui.adapter.NewsVPAdapter;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.view.viewpager.indicator.IndicatorViewPager;
import com.yuyh.library.view.viewpager.indicator.ScrollIndicatorView;
import com.yuyh.library.view.viewpager.indicator.slidebar.DrawableBar;
import com.yuyh.library.view.viewpager.indicator.slidebar.ScrollBar;
import com.yuyh.library.view.viewpager.indicator.transition.OnTransitionTextListener;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class NBANewsFragment extends BaseLazyFragment {


    private IndicatorViewPager indicatorViewPager;
    private String[] names = {"今日头条", "新闻资讯"};
    private ScrollIndicatorView scrollIndicatorView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_nba_news);

        scrollIndicatorView = (ScrollIndicatorView) findViewById(R.id.nba_news_indicator);
        scrollIndicatorView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        scrollIndicatorView.setScrollBar(new DrawableBar(mActivity, R.drawable.round_border_white_selector, ScrollBar.Gravity.CENTENT_BACKGROUND) {
            @Override
            public int getHeight(int tabHeight) {
                return tabHeight - DimenUtils.dpToPxInt(12);
            }

            @Override
            public int getWidth(int tabWidth) {
                return tabWidth - DimenUtils.dpToPxInt(12);
            }
        });
        scrollIndicatorView.setSplitAuto(true);
        // 设置滚动监听
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(getResources().getColor(R.color.colorPrimary), Color.WHITE));

        ViewPager viewPager = (ViewPager) findViewById(R.id.nba_news_viewPager);
        //viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);

        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        indicatorViewPager.setAdapter(new NewsVPAdapter(mActivity, names, getChildFragmentManager()));
    }
}
