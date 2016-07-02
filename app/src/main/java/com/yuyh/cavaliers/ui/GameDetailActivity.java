package com.yuyh.cavaliers.ui;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.ui.adapter.VPGameDetailAdapter;
import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.presenter.impl.GameDetailPresenter;
import com.yuyh.cavaliers.ui.view.GameDetailView;
import com.yuyh.cavaliers.widget.GameDetailScrollBar;
import com.yuyh.cavaliers.widget.StickyNavLayout;
import com.yuyh.library.view.viewpager.indicator.FixedIndicatorView;
import com.yuyh.library.view.viewpager.indicator.IndicatorViewPager;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class GameDetailActivity extends BaseSwipeBackCompatActivity implements GameDetailView, StickyNavLayout.OnStickStateChangeListener {

    @InjectView(R.id.snlViewPager)
    ViewPager viewPager;
    @InjectView(R.id.snlIindicator)
    FixedIndicatorView indicator;
    @InjectView(R.id.stickyNavLayout)
    StickyNavLayout stickyNavLayout;
    private IndicatorViewPager indicatorViewPager;
    private VPGameDetailAdapter adapter;

    private Presenter presenter;

    private LayoutInflater inflate;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_game_detail;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("骑士VS勇士");
        indicator.setScrollBar(new GameDetailScrollBar(getApplicationContext(), getResources().getColor(R.color.colorPrimary), 5));
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        stickyNavLayout.setOnStickStateChangeListener(this);
        presenter = new GameDetailPresenter(this, this);
        presenter.initialized();
    }

    @Override
    public void showTabViewPager(String[] names) {
        adapter = new VPGameDetailAdapter(this, names, getSupportFragmentManager());
        indicatorViewPager.setAdapter(adapter);
    }

    private boolean lastIsTopHidden;//记录上次是否悬浮
    @Override
    public void isStick(boolean isStick) {
        if (lastIsTopHidden != isStick) {
            lastIsTopHidden = isStick;
            if (isStick) {
                    Toast.makeText(this, "本宝宝悬浮了", Toast.LENGTH_LONG).show();
            } else {
                    Toast.makeText(this, "本宝宝又不悬浮了", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void scrollPercent(float percent) {
    }
}
