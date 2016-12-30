package com.yuyh.sprintnba.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.http.bean.forum.ForumsData;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.support.SpaceItemDecoration;
import com.yuyh.sprintnba.support.SupportRecyclerView;
import com.yuyh.sprintnba.ui.ThreadListActivity;
import com.yuyh.sprintnba.ui.adapter.ForumListAdapter;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.presenter.impl.ForumListPresenterImpl;
import com.yuyh.sprintnba.ui.view.ForumListView;

import java.util.ArrayList;
import java.util.List;

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
    @InjectView(R.id.emptyView)
    View emptyView;

    private ForumListAdapter adapter;
    private List<ForumsData.Forum> list = new ArrayList<>();

    private Presenter presenter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_normal_recyclerview);

        initView();
        presenter = new ForumListPresenterImpl(mActivity, this);
        presenter.initialized();
    }

    private void initView() {
        adapter = new ForumListAdapter(list, mActivity, R.layout.item_list_teams, R.layout.item_fragment_forum_title);
        adapter.setOnListItemClickListener(new OnListItemClickListener<ForumsData.Forum>() {
            @Override
            public void onItemClick(View view, int position, ForumsData.Forum data) {
                ThreadListActivity.start(mActivity, data);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(2)));
        recyclerView.setHasFixedSize(true);
        materialRefreshLayout.setLoadMore(false);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.initialized();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mActivity != null) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void showForumList(List<ForumsData.Forum> forumList) {
        list.clear();
        list.addAll(forumList);
        adapter.notifyDataSetChanged();
        materialRefreshLayout.finishRefresh();
        hideLoading();
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
    public void showError(String message) {
        recyclerView.setEmptyView(emptyView);
        hideLoading();
    }
}
