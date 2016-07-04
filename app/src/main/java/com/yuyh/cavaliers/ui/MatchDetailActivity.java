package com.yuyh.cavaliers.ui;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.bean.match.MatchBaseInfo;
import com.yuyh.cavaliers.ui.adapter.VPGameDetailAdapter;
import com.yuyh.cavaliers.ui.presenter.impl.MatchDetailPresenter;
import com.yuyh.cavaliers.ui.view.MatchDetailView;
import com.yuyh.cavaliers.utils.FrescoUtils;
import com.yuyh.cavaliers.widget.GameDetailScrollBar;
import com.yuyh.cavaliers.widget.StickyNavLayout;
import com.yuyh.library.view.viewpager.indicator.IndicatorViewPager;
import com.yuyh.library.view.viewpager.indicator.ScrollIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class MatchDetailActivity extends BaseSwipeBackCompatActivity implements MatchDetailView, StickyNavLayout.OnStickStateChangeListener {

    public static final String INTENT_MID = "mid";
    private String mid;

    @InjectView(R.id.snlViewPager)
    ViewPager viewPager;
    @InjectView(R.id.snlIindicator)
    ScrollIndicatorView indicator;
    @InjectView(R.id.stickyNavLayout)
    StickyNavLayout stickyNavLayout;

    @InjectView(R.id.rlMatchToolbar)
    RelativeLayout rlMatchToolbar;
    @InjectView(R.id.tvMatchTitle)
    TextView tvMatchTitle;
    @InjectView(R.id.tvLeftRate)
    TextView tvLeftRate;
    @InjectView(R.id.tvMatchState)
    TextView tvMatchState;
    @InjectView(R.id.tvRightRate)
    TextView tvRightRate;
    @InjectView(R.id.tvMatchLeftScore)
    TextView tvMatchLeftScore;
    @InjectView(R.id.tvMatchType)
    TextView tvMatchType;
    @InjectView(R.id.tvMatchRightScore)
    TextView tvMatchRightScore;
    @InjectView(R.id.tvMatchStartTime)
    TextView tvMatchStartTime;

    @InjectView(R.id.ivMatchLeftTeam)
    SimpleDraweeView ivMatchLeftTeam;
    @InjectView(R.id.ivMatchRightTeam)
    SimpleDraweeView ivMatchRightTeam;


    private IndicatorViewPager indicatorViewPager;
    private VPGameDetailAdapter adapter;

    private MatchDetailPresenter presenter;

    private LayoutInflater inflate;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_game_detail;
    }

    @Override
    protected void initViewsAndEvents() {
        mid = getIntent().getStringExtra(INTENT_MID);
        rlMatchToolbar.getBackground().setAlpha(0);
        indicator.setScrollBar(new GameDetailScrollBar(getApplicationContext(), getResources().getColor(R.color.colorPrimary), 8));
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        stickyNavLayout.setOnStickStateChangeListener(this);
        presenter = new MatchDetailPresenter(this, this);
        presenter.getMatchBaseInfo(mid);
    }

    @Override
    public void showTabViewPager(String[] names, boolean isStart) {
        hideLoadingDialog();
        adapter = new VPGameDetailAdapter(this, names, getSupportFragmentManager(), mid, isStart);
        indicatorViewPager.setAdapter(adapter);
    }

    @Override
    public void showMatchInfo(MatchBaseInfo.BaseInfo info) {
        tvMatchTitle.setText(info.leftName + "vs" + info.rightName);
        if (!TextUtils.isEmpty(info.leftWins) && !TextUtils.isEmpty(info.leftLosses))
            tvLeftRate.setText(info.leftWins + "胜" + info.leftLosses + "负");
        if (!TextUtils.isEmpty(info.rightWins) && !TextUtils.isEmpty(info.rightLosses))
            tvRightRate.setText(info.rightWins + "胜" + info.rightLosses + "负");
        String startTime = info.startDate + info.startHour;
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日HH:mm");
        String state = "未开始";
        try {
            Date date = format.parse(startTime);
            if (date.getTime() > System.currentTimeMillis()) { // 未开始
                presenter.getTab(false);
            } else {
                state = info.quarterDesc;
                if (state.contains("第4节") && state.contains("00:00")) {
                    state = "已结束";
                }
                presenter.getTab(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvMatchState.setText(state);
        tvMatchType.setText(info.desc);
        tvMatchStartTime.setText(info.startDate + "   " + info.startHour + "   " + info.venue);
        tvMatchLeftScore.setText(info.leftGoal);
        tvMatchRightScore.setText(info.rightGoal);
        ivMatchLeftTeam.setController(FrescoUtils.getController(Uri.parse(info.leftBadge), ivMatchLeftTeam));
        ivMatchRightTeam.setController(FrescoUtils.getController(Uri.parse(info.rightBadge), ivMatchRightTeam));
    }

    private boolean lastIsTopHidden;//记录上次是否悬浮

    @Override
    public void isStick(boolean isStick) {
        if (lastIsTopHidden != isStick) {
            lastIsTopHidden = isStick;
        }
    }

    @Override
    public void scrollPercent(float percent) {
        rlMatchToolbar.getBackground().setAlpha((int) ((float) 255 * percent));
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showError(String msg) {

    }
}
