package com.yuyh.sprintnba.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.yuyh.sprintnba.event.CalendarEvent;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.match.Matchs;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.support.SpaceItemDecoration;
import com.yuyh.sprintnba.support.SupportRecyclerView;
import com.yuyh.sprintnba.ui.MatchDetailActivity;
import com.yuyh.sprintnba.ui.adapter.ScheduleAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class ScheduleFragment extends BaseLazyFragment {

    private String date = "";

    @InjectView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @InjectView(R.id.recyclerview)
    SupportRecyclerView recyclerView;
    @InjectView(R.id.emptyView)
    View emptyView;

    private ScheduleAdapter adapter;
    private List<Matchs.MatchsDataBean.MatchesBean> list = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_normal_recyclerview);
        ButterKnife.inject(this, getContentView());
        date = DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        LogUtils.i(date);
        EventBus.getDefault().register(this);

        mActivity.invalidateOptionsMenu();

        initView();
        requestMatchs(date, true);
    }

    private void initView() {
        adapter = new ScheduleAdapter(list, mActivity, R.layout.item_list_match);
        adapter.setOnItemClickListener(new OnListItemClickListener<Matchs.MatchsDataBean.MatchesBean>() {
            @Override
            public void onItemClick(View view, int position, Matchs.MatchsDataBean.MatchesBean data) {
                Intent intent = new Intent(mActivity, MatchDetailActivity.class);
                intent.putExtra(MatchDetailActivity.INTENT_MID, data.matchInfo.mid);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(3)));
        materialRefreshLayout.setMaterialRefreshListener(new RefreshListener());
        materialRefreshLayout.setLoadMore(false);
    }

    private void requestMatchs(String date, boolean isRefresh) {
        showLoadingDialog();
        TencentService.getMatchsByDate(date, isRefresh, new RequestCallback<Matchs>() {
            @Override
            public void onSuccess(Matchs matchs) {
                list.clear();
                List<Matchs.MatchsDataBean.MatchesBean> mList = matchs.getData().matches;
                if (!mList.isEmpty()) {
                    for (Matchs.MatchsDataBean.MatchesBean bean : mList) {
                        list.add(bean);
                    }
                }
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
            requestMatchs(date, true);
        }
    }

    private void complete() {
        recyclerView.setEmptyView(emptyView);
        adapter.notifyDataSetChanged();
        materialRefreshLayout.finishRefresh();
        materialRefreshLayout.finishRefreshLoadMore();
        hideLoadingDialog();
    }

    @Subscribe
    public void onEventMainThread(CalendarEvent msg) {
        date = msg.getDate();
        LogUtils.i(msg.getDate());
        requestMatchs(date, true);
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
        EventBus.getDefault().unregister(this);
    }
}
