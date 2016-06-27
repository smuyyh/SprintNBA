package com.yuyh.cavaliers.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.bean.forum.ForumsData;
import com.yuyh.cavaliers.http.bean.forum.ThreadListData;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.presenter.impl.ThreadListPresenterImpl;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.ui.adapter.ThreadInfoListAdapter;
import com.yuyh.cavaliers.ui.view.ThreadListView;
import com.yuyh.cavaliers.widget.LoadMoreRecyclerView;
import com.yuyh.library.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class ThreadListActivity extends BaseSwipeBackCompatActivity implements ThreadListView, AppBarLayout.OnOffsetChangedListener {

    public static final String INTENT_FORUM = "forum";
    public static final String INTENT_FORUM_ID = "boardID";

    public ForumsData.Forum forum;
    public String boardId;

    @InjectView(R.id.lmrvLoadMore)
    LoadMoreRecyclerView recyclerView;

    @InjectView(R.id.backdrop)
    ImageView backdrop;
    @InjectView(R.id.tvSubTitle)
    TextView tvSubTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @InjectView(R.id.floatingAttention)
    FloatingActionButton floatingAttention;
    @InjectView(R.id.floatingPost)
    FloatingActionButton floatingPost;
    @InjectView(R.id.floatingSwitch)
    FloatingActionButton floatingSwitch;
    @InjectView(R.id.floatingRefresh)
    FloatingActionButton floatingRefresh;
    @InjectView(R.id.floatingMenu)
    FloatingActionMenu floatingMenu;
    @InjectView(R.id.frameLayout)
    FrameLayout frameLayout;

    private ThreadInfoListAdapter adapter;
    private List<ThreadListData.ThreadInfo> list = new ArrayList<>();

    private ThreadListPresenterImpl presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_thread_list;
    }

    @Override
    protected void initViewsAndEvents() {
        forum = (ForumsData.Forum) getIntent().getSerializableExtra(INTENT_FORUM);
        boardId = getIntent().getStringExtra(INTENT_FORUM_ID);
        appbar.addOnOffsetChangedListener(this);
        if(forum == null) {
            presenter = new ThreadListPresenterImpl(boardId, this, this);
            // TODO getForum
        } else {
            presenter = new ThreadListPresenterImpl(forum.fid, this, this);
            showThreadInfo(forum);
        }
        presenter.initialized();
        presenter.onThreadReceive(Constant.SortType.NEW.getType());
        initToolbar(toolbar);
        initFloatingMenu();
        initRecyclerView();
        attachPostButtonToRecycle();
    }

    private void initFloatingMenu() {
    }

    private void attachPostButtonToRecycle() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) > 4) {
                    if (dy > 0) {
                        floatingMenu.hideMenuButton(true);
                    } else {
                        floatingMenu.showMenuButton(true);
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(3)));
        recyclerView.setHasFixedSize(true);

        refreshLayout.setOnRefreshListener(new RefreshListener());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.material_red));
        recyclerView.setLoadMoreListener(new RefreshListener());
        adapter = new ThreadInfoListAdapter();
        adapter.setOnItemClickListener(new OnListItemClickListener<ThreadListData.ThreadInfo>() {
            @Override
            public void onItemClick(View view, int position, ThreadListData.ThreadInfo data) {
                Intent intent = new Intent(ThreadListActivity.this, ThreadDetailActivity.class);
                intent.putExtra("tid", data.tid);
                intent.putExtra("fid", data.fid);
                startActivity(intent);
            }
        });
        recyclerView.setLoadMoreEnable(false);
        backdrop.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0 || verticalOffset == -appBarLayout.getTotalScrollRange()) {
            refreshLayout.setEnabled(true);
        } else {
            refreshLayout.setEnabled(false);
        }
    }

    @Override
    public void showThreadList(List<ThreadListData.ThreadInfo> forumInfoList, boolean isRefresh) {
        if (isRefresh)
            list.clear();
        list.addAll(forumInfoList);
        adapter.bind(forumInfoList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showThreadInfo(ForumsData.Forum forum) {
        if (forum != null) {
            setTitle(forum.name);
            Picasso.with(this).load(forum.backImg).into(backdrop);
            tvSubTitle.setText(forum.description);
        }
    }

    @Override
    public void onFloatingVisibility(int visibility) {
        floatingMenu.setVisibility(visibility);
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
        complete();
    }

    @Override
    public void showError(String msg) {
        hideLoadingDialog();
    }

    @Override
    public void onLoadCompleted(boolean hasMore) {
        recyclerView.onLoadCompleted(hasMore);
    }

    @Override
    public void onRefreshCompleted() {
        refreshLayout.setRefreshing(false);
    }

    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

        @Override
        public void onRefresh() {
            presenter.onThreadReceive(Constant.SortType.NEW.getType());
        }

        @Override
        public void onLoadMore() {

        }
    }

    private void complete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1000);
    }
}
