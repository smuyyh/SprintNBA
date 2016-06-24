package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.Request;
import com.yuyh.cavaliers.http.bean.player.StatsRank;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.presenter.impl.StatsRankPresenterImpl;
import com.yuyh.cavaliers.ui.view.StatsRankView;
import com.yuyh.cavaliers.widget.ToggleLayout;
import com.yuyh.library.utils.log.LogUtils;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class NBAStatsRankFragment extends BaseLazyFragment implements StatsRankView, ToggleLayout.OnToggleListener {

    @InjectView(R.id.tlTab)
    ToggleLayout tlTab;
    @InjectView(R.id.tlStat)
    ToggleLayout tlStat;

    private Presenter presenter;

    private Map<String, Constant.TabType> tab;
    private Map<String, Constant.StatType> stat;
    private Constant.TabType curTab;
    private Constant.StatType curStat;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.activity_stats_rank);
        ButterKnife.inject(this, getContentView());
        presenter = new StatsRankPresenterImpl(mActivity, this);
        presenter.initialized();

    }

    @Override
    public void showStatsRank(Map<String, Constant.TabType> tab, Map<String, Constant.StatType> stat) {
        this.tab = tab;
        this.stat = stat;
        String[] tabKey = tab.keySet().toArray(new String[]{});
        tlTab.setItem(tabKey);
        String[] statKey = stat.keySet().toArray(new String[]{});
        tlStat.setItem(statKey);
        toggle(0);
    }

    @Override
    public void toggle(int position) {
        curStat = stat.get(tlStat.getCurrentItem());
        curTab = tab.get(tlTab.getCurrentItem());
        requestStatsRank();
    }

    private void requestStatsRank() {
        Request.getStatsRank(curStat, 20, curTab, "2015", true, new GetBeanCallback<StatsRank>() {
            @Override
            public void onSuccess(StatsRank statsRank) {
                List<StatsRank.RankItem> list = statsRank.rankList;
                if (!list.isEmpty()) {
                    for (StatsRank.RankItem item : list) {
                        LogUtils.i(item.playerName);
                    }
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}
