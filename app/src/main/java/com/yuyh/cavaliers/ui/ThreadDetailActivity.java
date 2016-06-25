package com.yuyh.cavaliers.ui;


import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.presenter.impl.ThreadDetailPresenterImpl;
import com.yuyh.cavaliers.ui.view.ThreadDetailView;
import com.yuyh.cavaliers.widget.VerticalViewPager;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ThreadDetailActivity extends BaseSwipeBackCompatActivity implements ThreadDetailView, ViewPager.OnPageChangeListener {

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
        presenter.setParams("16603856", "85", 1, "1");
        presenter.initialized();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
    public void loadContent(int page, List<String> urls) {
        totalPage = urls.size();
        viewPager.setCurrentItem(page);
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
