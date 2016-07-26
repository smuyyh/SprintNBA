package com.yuyh.sprintnba.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.library.utils.DateUtils;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.bean.player.TeamsRank;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.support.SpaceItemDecoration;
import com.yuyh.sprintnba.support.SupportRecyclerView;
import com.yuyh.sprintnba.ui.adapter.TeamsRankAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.TeamSortPresenter;
import com.yuyh.sprintnba.ui.view.TeamSortView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class TeamSortFragment extends BaseLazyFragment implements TeamSortView {

    private String date = "";

    @InjectView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @InjectView(R.id.recyclerview)
    SupportRecyclerView recyclerView;
    @InjectView(R.id.emptyView)
    View emptyView;

    private TeamsRankAdapter adapter;
    private List<TeamsRank.TeamBean> list = new ArrayList<>();

    private TeamSortPresenter presenter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_normal_recyclerview);
        ButterKnife.inject(this, getContentView());
        date = DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        LogUtils.i(date);

        mActivity.invalidateOptionsMenu();

        initView();
        presenter = new TeamSortPresenter(mActivity, this);
        presenter.requestTeamsRank(false);
    }

    private void initView() {
        adapter = new TeamsRankAdapter(list, mActivity, R.layout.item_fragment_teamsort_entity, R.layout.item_fragment_teamsort_title);
        adapter.setOnItemClickListener(new OnListItemClickListener<TeamsRank.TeamBean>() {
            @Override
            public void onItemClick(View view, int position, TeamsRank.TeamBean data) {
                Intent intent = new Intent(mActivity, BaseWebActivity.class);
                intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, data.name);
                intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, data.detailUrl);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(1)));
        materialRefreshLayout.setMaterialRefreshListener(new RefreshListener());
        materialRefreshLayout.setLoadMore(false);
    }

    @Override
    public void showTeamSort(List<TeamsRank.TeamBean> teamlist) {
        list.clear();
        list.addAll(teamlist);
        complete();
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
        hideLoading();
        complete();
    }

    private class RefreshListener extends MaterialRefreshListener {
        @Override
        public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
            presenter.requestTeamsRank(true);
        }
    }

    private void complete() {
        recyclerView.setEmptyView(emptyView);
        adapter.notifyDataSetChanged();
        materialRefreshLayout.finishRefresh();
        materialRefreshLayout.finishRefreshLoadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 800);
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
