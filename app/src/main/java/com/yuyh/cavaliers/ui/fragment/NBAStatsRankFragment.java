package com.yuyh.cavaliers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.base.BaseWebActivity;
import com.yuyh.cavaliers.http.api.tecent.TencentService;
import com.yuyh.cavaliers.http.bean.player.StatsRank;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.presenter.impl.StatsRankPresenterImpl;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.recycleview.SupportRecyclerView;
import com.yuyh.cavaliers.ui.adapter.StatsRankAdapter;
import com.yuyh.cavaliers.ui.view.StatsRankView;
import com.yuyh.cavaliers.widget.ToggleLayout;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;

import java.util.ArrayList;
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
    @InjectView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @InjectView(R.id.recyclerview)
    SupportRecyclerView recyclerView;
    @InjectView(R.id.tvEmptyView)
    View emptyView;

    private Presenter presenter;

    private Map<String, Constant.TabType> tab;
    private Map<String, Constant.StatType> stat;
    private Constant.TabType curTab;
    private Constant.StatType curStat;

    private List<StatsRank.RankItem> mList = new ArrayList<>();
    private StatsRankAdapter adapter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_stats_rank);
        ButterKnife.inject(this, getContentView());
        initView();
        presenter = new StatsRankPresenterImpl(mActivity, this);
        presenter.initialized();

    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        emptyView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
            }
        });
        adapter = new StatsRankAdapter(mList, mActivity, R.layout.list_item_stats_rank);
        adapter.setOnItemClickListener(new OnListItemClickListener<StatsRank.RankItem>() {
            @Override
            public void onItemClick(View view, int position, StatsRank.RankItem data) {
                Intent intent = new Intent(mActivity, BaseWebActivity.class);
                intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, data.playerName);
                intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, data.playerUrl);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(5)));
        tlStat.setOnToggleListener(this);
        tlTab.setOnToggleListener(this);
        materialRefreshLayout.setLoadMore(false);
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
        showLoadingDialog();
        TencentService.getStatsRank(curStat, 20, curTab, "2015", true, new RequestCallback<StatsRank>() {
            @Override
            public void onSuccess(StatsRank statsRank) {
                List<StatsRank.RankItem> list = statsRank.rankList;
                if (list != null && !list.isEmpty()) {
                    for (StatsRank.RankItem item : list) {
                        LogUtils.i(item.playerName);
                    }
                    mList.clear();
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                    complete();
                }
            }

            @Override
            public void onFailure(String message) {
                complete();
            }
        });
    }

    private void complete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1000);
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
