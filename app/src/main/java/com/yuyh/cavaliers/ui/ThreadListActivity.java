package com.yuyh.cavaliers.ui;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.recycleview.SupportRecyclerView;
import com.yuyh.library.utils.DimenUtils;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class ThreadListActivity extends BaseSwipeBackCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @InjectView(R.id.recyclerview)
    SupportRecyclerView recyclerView;

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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_thread_list;
    }

    @Override
    protected void initViewsAndEvents() {

        appbar.addOnOffsetChangedListener(this);
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
        recyclerView.setAdapter(null);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(5)));
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0 || verticalOffset == -appBarLayout.getTotalScrollRange()) {
            refreshLayout.setEnabled(true);
        } else {
            refreshLayout.setEnabled(false);
        }
    }
}
