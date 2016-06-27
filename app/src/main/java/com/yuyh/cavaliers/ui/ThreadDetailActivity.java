package com.yuyh.cavaliers.ui;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.api.hupu.forum.HupuForumService;
import com.yuyh.cavaliers.presenter.impl.ThreadDetailPresenterImpl;
import com.yuyh.cavaliers.ui.adapter.VPThreadAdapter;
import com.yuyh.cavaliers.ui.view.ThreadDetailView;
import com.yuyh.cavaliers.widget.VerticalViewPager;
import com.yuyh.library.utils.log.LogUtils;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ThreadDetailActivity extends BaseSwipeBackCompatActivity implements ThreadDetailView, ViewPager.OnPageChangeListener {

    public static final String INTENT_THREAD_INFO = "threadinfo";

    private String fid;
    private String tid;
    private int page;
    private String pid;
    private int totalPage;

    @InjectView(R.id.vvpComment)
    VerticalViewPager viewPager;
    @InjectView(R.id.floatingComment)
    FloatingActionButton floatingComment;
    @InjectView(R.id.floatingReport)
    FloatingActionButton floatingReport;
    @InjectView(R.id.floatingCollect)
    FloatingActionButton floatingCollect;
    @InjectView(R.id.floatingShare)
    FloatingActionButton floatingShare;
    @InjectView(R.id.floatingMenu)
    FloatingActionMenu floatingMenu;
    @InjectView(R.id.tvPre)
    TextView tvPre;
    @InjectView(R.id.tvPageNum)
    TextView tvPageNum;
    @InjectView(R.id.tvNext)
    TextView tvNext;

    private ThreadDetailPresenterImpl presenter;
    private VPThreadAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_thread_detail;
    }

    @Override
    protected void initViewsAndEvents() {
        fid = getIntent().getStringExtra("fid");
        tid = getIntent().getStringExtra("tid");
        page = getIntent().getIntExtra("page", 1);
        pid = getIntent().getStringExtra("pid");
        setTitle("帖子详细");

        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(this);

        presenter = new ThreadDetailPresenterImpl(this, this);
        presenter.setParams(tid, fid, 1, "");
        presenter.initialized();

        LogUtils.i("--add start--");
        HupuForumService.addReplyByApp(tid, fid, "", "不错，顶一个哈~");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        onUpdatePager(position + 1, totalPage);
        presenter.updatePage(position + 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onUpdatePager(int page, int totalPage) {
        tvPageNum.setText(page + "/" + totalPage);
        if (page == 1) {
            tvPre.setTextColor(getResources().getColor(R.color.secondary_text));
            tvPre.setClickable(false);
        } else {
            tvPre.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvPre.setClickable(true);
        }

        if (page == totalPage) {
            tvNext.setTextColor(getResources().getColor(R.color.secondary_text));
            tvNext.setClickable(false);
        } else {
            tvNext.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvNext.setClickable(true);
        }

        if (viewPager.getCurrentItem() > 0) {
            mToolbar.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
        }
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

    }

    @Override
    public void loadContent(int position, List<String> urls) {
        totalPage = urls.size();
        viewPager.setCurrentItem(position - 1);

        if (mAdapter == null) {
            mAdapter = new VPThreadAdapter(getSupportFragmentManager(), urls);
            viewPager.setAdapter(mAdapter);
        }
        viewPager.setCurrentItem(position - 1);
        onUpdatePager(viewPager.getCurrentItem() + 1, totalPage);
    }

    @Override
    public void isCollected(boolean isCollected) {
        floatingCollect.setImageResource(isCollected ? R.drawable.ic_menu_star : R.drawable.ic_menu_star_outline);
        floatingCollect.setLabelText(isCollected ? "取消收藏" : "收藏");
    }

    @Override
    public void onToggleFloatingMenu() {
        floatingMenu.toggle(true);
    }

    @OnClick(R.id.tvPre)
    public void prePage() {
        presenter.onPagePre();
    }

    @OnClick(R.id.tvNext)
    public void nextpage() {
        presenter.onPageNext();
    }

    @OnClick(R.id.floatingComment)
    void floatingComment() {
        presenter.onCommendClick();
    }

    @OnClick(R.id.floatingShare)
    void floatingShare() {
        presenter.onShareClick();
    }

    @OnClick(R.id.floatingReport)
    void floatingReport() {
        presenter.onReportClick();
    }

    @OnClick(R.id.floatingCollect)
    void floatingCollect() {
        presenter.onCollectClick();
    }
}
