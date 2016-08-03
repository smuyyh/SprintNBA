package com.yuyh.sprintnba.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.http.bean.forum.ForumsData;
import com.yuyh.sprintnba.http.bean.forum.ThreadListData;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.support.SpaceItemDecoration;
import com.yuyh.sprintnba.support.SupportRecyclerView;
import com.yuyh.sprintnba.ui.adapter.ThreadListAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.ThreadListPresenterImpl;
import com.yuyh.sprintnba.ui.view.ThreadListView;
import com.yuyh.sprintnba.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

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
    SupportRecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @InjectView(R.id.backdrop)
    SimpleDraweeView backdrop;
    @InjectView(R.id.tvSubTitle)
    TextView tvSubTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
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

    private ThreadListAdapter adapter;
    private List<ThreadListData.ThreadInfo> list = new ArrayList<>();

    private ThreadListPresenterImpl presenter;

    private String last = "";
    private int pageIndex = 1;
    private String key;
    private String type = Constant.THREAD_TYPE_NEW;

    private boolean isLoading;
    private Handler handler = new Handler();
    private SearchView searchView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_thread_list;
    }

    @Override
    protected void initViewsAndEvents() {
        forum = (ForumsData.Forum) getIntent().getSerializableExtra(INTENT_FORUM);
        boardId = getIntent().getStringExtra(INTENT_FORUM_ID);
        appbar.addOnOffsetChangedListener(this);
        if (forum == null) {
            presenter = new ThreadListPresenterImpl(boardId, this, this);
            presenter.getForumInfo();
        } else {
            presenter = new ThreadListPresenterImpl(forum.fid, this, this);
            boardId = forum.fid;
            showThreadInfo(forum);
        }
        //backdrop.setImageDrawable(getResources().getDrawable(R.drawable.nba_default_large));
        presenter.initialized();
        presenter.onThreadReceive(type, "", true);
        initToolbar(toolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(3)));
        recyclerView.setHasFixedSize(true);

        refreshLayout.setOnRefreshListener(new RefreshListener());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.material_red));
        adapter = new ThreadListAdapter(list, this, R.layout.item_list_threads);
        adapter.setOnItemClickListener(new OnListItemClickListener<ThreadListData.ThreadInfo>() {
            @Override
            public void onItemClick(View view, int position, ThreadListData.ThreadInfo data) {
                Intent intent = new Intent(ThreadListActivity.this, ThreadDetailActivity.class);
                intent.putExtra("tid", data.tid);
                intent.putExtra("fid", data.fid);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RefreshListener());
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
        last = forumInfoList.get(forumInfoList.size() - 1).tid;
        if (isRefresh) {
            list.clear();
            last = "";
        }
        list.addAll(forumInfoList);
        adapter.notifyDataSetChanged();
        if (searchView != null)
            searchView.clearFocus();
    }

    @Override
    public void showThreadInfo(ForumsData.Forum forum) {
        if (forum != null) {
            setTitle(forum.name);
            backdrop.setController(FrescoUtils.getController(forum.backImg, backdrop));
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
        ToastUtils.showSingleToast(msg);
    }

    @Override
    public void onLoadCompleted(boolean hasMore) {
        isLoading = false;
    }

    @Override
    public void onRefreshCompleted() {
        refreshLayout.setRefreshing(false);
    }

    private class RefreshListener extends RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            pageIndex = 0;
            presenter.onThreadReceive(type, "", true);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition + 1 == adapter.getItemCount()) { // 滑到倒数第二项就加载更多

                boolean isRefreshing = refreshLayout.isRefreshing();
                if (isRefreshing) {
                    adapter.notifyItemRemoved(adapter.getItemCount());
                    return;
                }
                if (!isLoading) {
                    isLoading = true;
                    if (presenter.loadType == ThreadListPresenterImpl.TYPE_LIST)
                        presenter.onThreadReceive(type, last, false);
                    else {
                        pageIndex++;
                        presenter.onStartSearch(key, pageIndex, false);
                    }
                }
            }
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

    @Override
    public void onBackPressed() {
        if (floatingMenu.isOpened()) {
            floatingMenu.close(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onScrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    @OnClick(R.id.floatingAttention)
    void floatingAttention() {
        presenter.onAttentionClick();
        floatingMenu.toggle(true);
    }

    @OnClick(R.id.floatingPost)
    void floatingPost() {
        floatingMenu.toggle(true);
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.INTENT_TITLE, "");
        intent.putExtra(PostActivity.INTENT_TYPE, Constant.TYPE_POST);
        intent.putExtra(PostActivity.INTENT_FID, boardId);
        intent.putExtra(PostActivity.INTENT_TID, "");
        intent.putExtra(PostActivity.INTENT_PID, "");
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.floatingRefresh)
    void floatingRefresh() {
        presenter.onRefresh();
        floatingMenu.toggle(true);
    }

    @OnClick(R.id.floatingSwitch)
    void floatingSwitch() {
        if (floatingSwitch.getLabelText().equals("按回帖时间排序")) {
            presenter.onThreadReceive(Constant.THREAD_TYPE_HOT, "", true);
            type = Constant.THREAD_TYPE_HOT;
            floatingSwitch.setLabelText("按发帖时间排序");
        } else {
            presenter.onThreadReceive(Constant.THREAD_TYPE_NEW, "", true);
            type = Constant.THREAD_TYPE_NEW;
            floatingSwitch.setLabelText("按回帖时间排序");
        }
        floatingMenu.toggleMenuButton(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thread, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onStartSearch(query, 1, true);
                key = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        presenter.onThreadReceive(Constant.THREAD_TYPE_HOT, "", true);
                        return true;
                    }
                });
        return true;
    }
}
