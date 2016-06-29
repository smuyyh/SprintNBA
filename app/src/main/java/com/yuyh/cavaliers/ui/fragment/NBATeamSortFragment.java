package com.yuyh.cavaliers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.base.BaseWebActivity;
import com.yuyh.cavaliers.http.api.tencent.TencentService;
import com.yuyh.cavaliers.http.bean.player.TeamsRank;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.recycleview.SupportRecyclerView;
import com.yuyh.cavaliers.ui.adapter.TeamsRankAdapter;
import com.yuyh.library.utils.DateUtils;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class NBATeamSortFragment extends BaseLazyFragment {

    private String date = "";

    private MaterialRefreshLayout materialRefreshLayout;
    private SupportRecyclerView recyclerView;
    private View emptyView;

    private TeamsRankAdapter adapter;
    private List<TeamsRank.TeamBean> list = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_normal_recyclerview);
        date = DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        LogUtils.i(date);

        mActivity.invalidateOptionsMenu();

        initView();
        requestTeamsRank(false);
    }

    private void initView() {
        recyclerView = (SupportRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        emptyView = findViewById(R.id.tvEmptyView);
        ((TextView) emptyView).setText("暂无球队排名数据\n");
        emptyView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                //requestMatchs(date, true);
            }
        });

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

        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(1)));

        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new RefreshListener());
        materialRefreshLayout.setLoadMore(false);
    }

    private void requestTeamsRank(boolean isRefresh) {
        showLoadingDialog();
        TencentService.getTeamsRank(isRefresh, new RequestCallback<TeamsRank>() {
            @Override
            public void onSuccess(TeamsRank teamsRank) {
                list.clear();
                list.addAll(teamsRank.all);
                adapter.notifyDataSetChanged();
                complete();
            }

            @Override
            public void onFailure(String message) {
                complete();
            }
        });
    }

    private class RefreshListener extends MaterialRefreshListener {
        @Override
        public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
            requestTeamsRank(true);
        }
    }

    private void complete() {
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
