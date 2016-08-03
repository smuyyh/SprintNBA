package com.yuyh.sprintnba.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.bean.player.StatsRank;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.support.SpaceItemDecoration;
import com.yuyh.sprintnba.support.SupportRecyclerView;
import com.yuyh.sprintnba.ui.adapter.StatsRankAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.StatsRankPresenterImpl;
import com.yuyh.sprintnba.ui.view.StatsRankView;
import com.yuyh.sprintnba.widget.ToggleLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class StatsRankFragment extends BaseLazyFragment implements StatsRankView, ToggleLayout.OnToggleListener {

    @InjectView(R.id.tlTab)
    ToggleLayout tlTab;
    @InjectView(R.id.tlStat)
    ToggleLayout tlStat;
    @InjectView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @InjectView(R.id.recyclerview)
    SupportRecyclerView recyclerView;
    @InjectView(R.id.emptyView)
    View emptyView;

    private StatsRankPresenterImpl presenter;

    private Map<String, Constant.TabType> tab;
    private Map<String, Constant.StatType> stat;
    private Constant.TabType curTab = Constant.TabType.EVERYDAY;
    private Constant.StatType curStat = Constant.StatType.POINT;

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
        adapter = new StatsRankAdapter(mList, mActivity, R.layout.item_list_stats_rank);
        adapter.setOnItemClickListener(new OnListItemClickListener<StatsRank.RankItem>() {
            @Override
            public void onItemClick(View view, int position, StatsRank.RankItem data) {
                Intent intent = new Intent(mActivity, BaseWebActivity.class);
                intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, data.playerName);
                intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, data.playerUrl);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(2)));
        tlStat.setOnToggleListener(this);
        tlTab.setOnToggleListener(this);
        materialRefreshLayout.setLoadMore(false);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.requestStatsRank(curStat, curTab);
            }
        });
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
    public void showStatList(List<StatsRank.RankItem> list) {
        mList.clear();
        mList.addAll(list);
        complete();
    }

    @Override
    public void toggle(int position) {
        curStat = stat.get(tlStat.getCurrentItem());
        curTab = tab.get(tlTab.getCurrentItem());
        presenter.requestStatsRank(curStat, curTab);
    }

    private void complete() {
        recyclerView.setEmptyView(emptyView);
        adapter.notifyDataSetChanged();
        materialRefreshLayout.finishRefresh();
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

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoading();
    }

    @Override
    public void showError(String msg) {
        hideLoading();
        complete();
    }
}
