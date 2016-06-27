package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

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
    android.support.v7.widget.Toolbar mToolbar = null;

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
    }

    @Override
    public void onFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 500);

        if (mToolbar.getVisibility() == View.GONE) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) hupuWebView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            hupuWebView.setLayoutParams(params);
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
}
