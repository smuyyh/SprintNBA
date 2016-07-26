package com.yuyh.sprintnba.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.event.RefreshEvent;
import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.sprintnba.ui.adapter.MatchStatisticsAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.MatchDataPresenter;
import com.yuyh.sprintnba.ui.view.MatchDataView;
import com.yuyh.sprintnba.utils.FrescoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 比赛数据
 *
 * @author yuyh.
 * @date 16/6/5.
 */
public class MatchDataFragment extends BaseLazyFragment implements MatchDataView {

    @InjectView(R.id.snlScrollView)
    ScrollView snlScrollView;

    @InjectView(R.id.llMatchPoint)
    LinearLayout llMatchPoint;
    @InjectView(R.id.llMatchTeamStatistics)
    LinearLayout llMatchTeamStatistics;

    @InjectView(R.id.tvMatchPoint)
    TextView tvMatchPoint;
    @InjectView(R.id.tvMatchTeamStatistics)
    TextView tvMatchTeamStatistics;
    @InjectView(R.id.lvMatchTeamStatistics)
    ListView lvMatchTeamStatistics;

    @InjectView(R.id.llMatchPointHead)
    LinearLayout llMatchPointHead;
    @InjectView(R.id.llMatchPointLeft)
    LinearLayout llMatchPointLeft;
    @InjectView(R.id.llMatchPointRight)
    LinearLayout llMatchPointRight;

    @InjectView(R.id.ivMatchPointLeft)
    SimpleDraweeView ivMatchPointLeft;
    @InjectView(R.id.ivMatchPointRight)
    SimpleDraweeView ivMatchPointRight;

    private MatchDataPresenter presenter;
    private MatchStatisticsAdapter adapter;
    private List<MatchStat.MatchStatInfo.StatsBean.TeamStats> teamStats = new ArrayList<>();

    public static MatchDataFragment newInstance(String mid) {
        Bundle args = new Bundle();
        args.putString("mid", mid);
        MatchDataFragment fragment = new MatchDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_match_data);
        ButterKnife.inject(this, getContentView());
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        showLoadingDialog();
        lvMatchTeamStatistics.setFocusable(false);
        presenter = new MatchDataPresenter(mActivity, this);
        presenter.initialized();
        presenter.getMatchStats(getArguments().getString("mid"), "1");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
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
        ToastUtils.showSingleToast(msg);
    }

    @Override
    public void showMatchPoint(List<MatchStat.MatchStatInfo.StatsBean.Goals> list, MatchStat.MatchStatInfo.MatchTeamInfo teamInfo) {
        ivMatchPointLeft.setController(FrescoUtils.getController(teamInfo.leftBadge, ivMatchPointLeft));
        ivMatchPointRight.setController(FrescoUtils.getController(teamInfo.rightBadge, ivMatchPointRight));
        MatchStat.MatchStatInfo.StatsBean.Goals goals = list.get(0);
        List<String> head = goals.head;
        List<String> left = goals.rows.get(0);
        List<String> right = goals.rows.get(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        for (int i = 0; i < head.size() && i < left.size() && i < right.size(); i++) {
            if (llMatchPointRight.getChildAt(i + 1) != null) {
                TextView tv = (TextView) llMatchPointHead.getChildAt(i + 1);
                tv.setText(head.get(i));
            } else {
                TextView tv = (TextView) inflater.inflate(R.layout.tab_match_point, null);
                tv.setText(head.get(i));
                tv.setLayoutParams(params);
                llMatchPointHead.addView(tv, i + 1);
            }
            if (left != null) {
                if (llMatchPointLeft.getChildAt(i + 1) != null) {
                    TextView tv1 = (TextView) llMatchPointLeft.getChildAt(i + 1);
                    tv1.setText(left.get(i));
                } else {
                    TextView tv1 = (TextView) inflater.inflate(R.layout.tab_match_point, null);
                    tv1.setText(left.get(i));
                    tv1.setLayoutParams(params);
                    llMatchPointLeft.addView(tv1, i + 1);
                }
            }
            if (right != null) {
                if (llMatchPointRight.getChildAt(i + 1) != null) {
                    TextView tv2 = (TextView) llMatchPointRight.getChildAt(i + 1);
                    tv2.setText(right.get(i));
                } else {
                    TextView tv2 = (TextView) inflater.inflate(R.layout.tab_match_point, null);
                    tv2.setText(right.get(i));
                    tv2.setLayoutParams(params);
                    llMatchPointRight.addView(tv2, i + 1);
                }
            }
        }
        llMatchPoint.setVisibility(View.VISIBLE);
        complete();
    }

    @Override
    public void showTeamStatistics(List<MatchStat.MatchStatInfo.StatsBean.TeamStats> teamStats) {
        this.teamStats.clear();
        this.teamStats.addAll(teamStats);
        if(adapter == null) {
            adapter = new MatchStatisticsAdapter(this.teamStats, mActivity, R.layout.item_list_match_team_statistics);
            lvMatchTeamStatistics.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
        llMatchTeamStatistics.setVisibility(View.VISIBLE);
        complete();
        snlScrollView.scrollTo(0, 0);
    }

    public void complete() {
        snlScrollView.smoothScrollTo(0, 20);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1000);
    }

    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        presenter.getMatchStats(getArguments().getString("mid"), "1");
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        EventBus.getDefault().unregister(this);
    }
}
