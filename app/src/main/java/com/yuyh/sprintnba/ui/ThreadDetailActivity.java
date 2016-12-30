package com.yuyh.sprintnba.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.event.ThreadContentEvent;
import com.yuyh.sprintnba.ui.adapter.VPThreadAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.ThreadDetailPresenterImpl;
import com.yuyh.sprintnba.ui.view.ThreadDetailView;
import com.yuyh.sprintnba.widget.VerticalViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ThreadDetailActivity extends BaseSwipeBackCompatActivity implements ThreadDetailView, ViewPager.OnPageChangeListener {

    public static final String INTENT_PID = "pid";
    public static final String INTENT_TID = "tid";
    public static final String INTENT_PAGE = "page";
    public static final String INTENT_FID = "fid";

    private String fid;
    private String tid;
    private int page;
    private String pid;
    private int totalPage;

    public static void start(Context context, String pid, String tid, int page, String fid) {
        Intent intent = new Intent(context, ThreadDetailActivity.class);
        intent.putExtra(ThreadDetailActivity.INTENT_PID, pid);
        intent.putExtra(ThreadDetailActivity.INTENT_TID, tid);
        intent.putExtra(ThreadDetailActivity.INTENT_PAGE, page);
        intent.putExtra(ThreadDetailActivity.INTENT_FID, fid);
        context.startActivity(intent);
    }

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
        fid = getIntent().getStringExtra(INTENT_FID);
        tid = getIntent().getStringExtra(INTENT_TID);
        page = getIntent().getIntExtra(INTENT_PAGE, 1);
        pid = getIntent().getStringExtra(INTENT_PID);
        setTitle("帖子详细");

        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(this);

        presenter = new ThreadDetailPresenterImpl(this, this);
        presenter.setParams(tid, fid, 1, "");
        presenter.initialized();
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
            tvPre.setTextColor(ContextCompat.getColor(this, R.color.secondary_text));
            tvPre.setClickable(false);
        } else {
            tvPre.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tvPre.setClickable(true);
        }

        if (page == totalPage) {
            tvNext.setTextColor(ContextCompat.getColor(this, R.color.secondary_text));
            tvNext.setClickable(false);
        } else {
            tvNext.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tvNext.setClickable(true);
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showSingleToast(msg);
    }

    @Override
    public void loadContent(int position, List<String> urls) {
        totalPage = urls.size();

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

    @Override
    public void goPost(String title) {
        PostActivity.startForResult(this, title, Constant.TYPE_COMMENT, fid, tid, "", 1);
    }

    @Override
    public void goReport() {
        ReportActivity.start(this, "", tid);
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

    @Override
    public void onBackPressed() {
        if (floatingMenu.isOpened()) {
            floatingMenu.close(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            viewPager.setCurrentItem(totalPage - 1);
            onUpdatePager(viewPager.getCurrentItem() + 1, totalPage);
            EventBus.getDefault().post(new ThreadContentEvent());
        }
    }
}
