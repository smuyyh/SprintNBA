package com.yuyh.cavaliers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.bean.forum.ForumsData;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.presenter.impl.ForumListPresenterImpl;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.recycleview.SupportRecyclerView;
import com.yuyh.cavaliers.ui.ThreadListActivity;
import com.yuyh.cavaliers.ui.adapter.ForumListAdapter;
import com.yuyh.cavaliers.ui.view.ForumListView;
import com.yuyh.library.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class ForumListFragment extends BaseLazyFragment implements ForumListView {

    @InjectView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @InjectView(R.id.recyclerview)
    SupportRecyclerView recyclerView;
    @InjectView(R.id.tvEmptyView)
    View emptyView;

    private ForumListAdapter adapter;
    private List<ForumsData.Forum> list = new ArrayList<>();

    private Presenter presenter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_normal_recyclerview);
        ButterKnife.inject(this, getContentView());
        initView();
        presenter = new ForumListPresenterImpl(mActivity, this);
        presenter.initialized();
    }

    private void initView() {
        ((TextView) emptyView).setText("暂无论坛版块数据\n");
        emptyView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                //requestMatchs(date, true);
            }
        });

        adapter = new ForumListAdapter(list, mActivity, R.layout.list_item_teams, R.layout.item_fragment_forum_title);
        adapter.setOnListItemClickListener(new OnListItemClickListener<ForumsData.Forum>() {
            @Override
            public void onItemClick(View view, int position, ForumsData.Forum data) {
                Intent intent = new Intent(mActivity, ThreadListActivity.class);
                intent.putExtra(ThreadListActivity.INTENT_FORUM, data);
                startActivity(intent);

            }
        });
        materialRefreshLayout.setLoadMore(false);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.initialized();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(2)));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void showForumList(List<ForumsData.Forum> forumList) {
        list.clear();
        list.addAll(forumList);
        adapter.notifyDataSetChanged();
        materialRefreshLayout.finishRefresh();
    }
}
