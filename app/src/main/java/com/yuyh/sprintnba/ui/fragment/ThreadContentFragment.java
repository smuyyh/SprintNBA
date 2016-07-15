package com.yuyh.sprintnba.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseAppCompatActivity;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.event.ThreadContentEvent;
import com.yuyh.sprintnba.widget.HuPuWebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ThreadContentFragment extends BaseLazyFragment implements HuPuWebView.HuPuWebViewCallBack, HuPuWebView.OnScrollChangedCallback {

    @InjectView(R.id.hupuWebView)
    HuPuWebView hupuWebView;
    private String url;
    android.support.v7.widget.Toolbar mToolbar = null;

    private boolean isNeedScrollToBottom = false;

    public static ThreadContentFragment newInstance(String url) {
        ThreadContentFragment mFragment = new ThreadContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setHasOptionsMenu(true);
        showLoadingDialog();
        setContentView(R.layout.fragment_thread_detail);
        ButterKnife.inject(this, getContentView());
        url = getArguments().getString("url");
        hupuWebView.loadUrl(url);
        hupuWebView.setCallBack(this);
        hupuWebView.setOnScrollChangedCallback(this);
        EventBus.getDefault().register(this);
        mToolbar = ((BaseAppCompatActivity) mActivity).getToolbar();
    }

    @Override
    protected void onPauseLazy() {
        if (hupuWebView != null) {
            hupuWebView.onPause();
            hupuWebView.reload();
        }
        super.onPauseLazy();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (hupuWebView != null) {
            hupuWebView.removeAllViews();
            hupuWebView.destroy();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 500);

        if (isNeedScrollToBottom) {
            isNeedScrollToBottom = false;
            hupuWebView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //hupuWebView.scrollTo(0, (int) (hupuWebView.getContentHeight()* hupuWebView.getScale()));
                    hupuWebView.setScrollY((int) (hupuWebView.getContentHeight() * hupuWebView.getScale() - hupuWebView.getHeight()));
                }
            }, 500);
        }

    }

    @Override
    public void onUpdatePager(int page, int total) {
    }

    @Override
    public void onError() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1000);
    }

    @Override
    public void onScroll(int dx, int dy, int y, int oldy) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_thread_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                hupuWebView.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEventMainThread(ThreadContentEvent event) {
        isNeedScrollToBottom = true;
        hupuWebView.reload();
    }
}
