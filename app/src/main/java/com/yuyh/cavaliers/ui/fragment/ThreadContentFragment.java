package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseAppCompatActivity;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.widget.HuPuWebView;

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
        showLoadingDialog();
        setContentView(R.layout.fragment_thread_detail);
        ButterKnife.inject(this, getContentView());
        url = getArguments().getString("url");
        hupuWebView.loadUrl(url);
        hupuWebView.setCallBack(this);
        hupuWebView.setOnScrollChangedCallback(this);
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
    }

    @Override
    public void onFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 500);
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
        }, 500);
    }

    @Override
    public void onScroll(int dx, int dy, int y, int oldy) {
        android.support.v7.widget.Toolbar mToolbar = ((BaseAppCompatActivity) mActivity).getToolbar();
        int height = mToolbar.getHeight();
        if (mToolbar != null) {
            if (y >= 0 && y <= 1000) {
                mToolbar.setAlpha(((float) (1000 - y)) / 1000);
                SwipeBackHelper.getCurrentPage(mActivity).setTintAlpha(((float) (1000 - y)) / 1000);
            } else {
                mToolbar.setAlpha(0);
                SwipeBackHelper.getCurrentPage(mActivity).setTintAlpha(0);
            }
        }
        if (y / 5 <= height) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) hupuWebView.getLayoutParams();
            params.setMargins(0, mToolbar.getHeight() - y / 2, 0, 0);
            hupuWebView.setLayoutParams(params);
            mToolbar.setTranslationY(-y / 5);
        }
    }
}
