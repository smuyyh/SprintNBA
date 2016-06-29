package com.yuyh.cavaliers.ui.fragment;

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
import com.yuyh.cavaliers.event.CalendarEvent;
import com.yuyh.cavaliers.http.api.tencent.TencentService;
import com.yuyh.cavaliers.http.bean.match.Matchs;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.recycleview.SupportRecyclerView;
import com.yuyh.cavaliers.ui.adapter.MatchAdapter;
import com.yuyh.library.utils.DateUtils;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class NBAScheduleFragment extends BaseLazyFragment {

    private String date = "";

    private MaterialRefreshLayout materialRefreshLayout;
    private SupportRecyclerView recyclerView;
    private View emptyView;

    private MatchAdapter adapter;
    private List<Matchs.MatchsDataBean.MatchesBean> list = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_normal_recyclerview);
        date = DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        LogUtils.i(date);
        EventBus.getDefault().register(this);

        mActivity.invalidateOptionsMenu();

        initView();
        requestMatchs(date, true);
    }

    private void initView() {
        recyclerView = (SupportRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        emptyView = findViewById(R.id.tvEmptyView);
        ((TextView) emptyView).setText("今日暂无比赛\n");
        emptyView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                //requestMatchs(date, true);
            }
        });

        adapter = new MatchAdapter(list, mActivity, R.layout.list_item_match);
        adapter.setOnItemClickListener(new OnListItemClickListener<Matchs.MatchsDataBean.MatchesBean>() {
            @Override
            public void onItemClick(View view, int position, Matchs.MatchsDataBean.MatchesBean data) {

            }
        });

        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(5)));

        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new RefreshListener());
        materialRefreshLayout.setLoadMore(false);
    }

    private void requestMatchs(String date, boolean isRefresh) {
        showLoadingDialog();
        TencentService.getMatchsByDate(date, isRefresh, new RequestCallback<Matchs>() {
            @Override
            public void onSuccess(Matchs matchs) {
                list.clear();
                List<Matchs.MatchsDataBean.MatchesBean> mList = matchs.getData().getMatches();
                if (!mList.isEmpty()) {
                    for (Matchs.MatchsDataBean.MatchesBean bean : mList) {
                        list.add(bean);
                    }
                }
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
            requestMatchs(date, true);
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
        }, 500);
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
