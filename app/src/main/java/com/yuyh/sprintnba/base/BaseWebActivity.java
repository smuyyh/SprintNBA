
package com.yuyh.sprintnba.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.widget.BrowserLayout;

import butterknife.ButterKnife;

public class BaseWebActivity extends BaseSwipeBackCompatActivity {

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    public static final String BUNDLE_KEY_SHOW_BOTTOM_BAR = "BUNDLE_KEY_SHOW_BOTTOM_BAR";
    public static final String BUNDLE_OVERRIDE = "BUNDLE_OVERRIDE";

    private String mWebUrl = null;
    private String mWebTitle = null;
    private boolean isShowBottomBar = true;
    private boolean isOverrideUrlLoading = true;

    private BrowserLayout mBrowserLayout = null;

    public static void start(Context context, String url, String title,
                             boolean isShowBottomBar, boolean isOverrideUrlLoading) {
        Intent intent = new Intent(context, BaseWebActivity.class)
                .putExtra(BUNDLE_KEY_URL, url)
                .putExtra(BUNDLE_KEY_TITLE, title)
                .putExtra(BUNDLE_KEY_SHOW_BOTTOM_BAR, isShowBottomBar)
                .putExtra(BUNDLE_OVERRIDE, isOverrideUrlLoading);
        LogUtils.i("url = " + url);
        LogUtils.i("title = " + title);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initViewsAndEvents() {
        showLoadingDialog();
        Intent intent = getIntent();
        mWebTitle = intent.getStringExtra(BUNDLE_KEY_TITLE);
        mWebUrl = intent.getStringExtra(BUNDLE_KEY_URL);
        isShowBottomBar = intent.getBooleanExtra(BUNDLE_KEY_SHOW_BOTTOM_BAR, true);
        isOverrideUrlLoading = intent.getBooleanExtra(BUNDLE_OVERRIDE, true);
        if (!TextUtils.isEmpty(mWebTitle)) {
            setTitle(mWebTitle);
        } else {
            setTitle("详细内容");
        }

        mBrowserLayout = ButterKnife.findById(this, R.id.common_web_browser_layout);
        if (!isShowBottomBar) {
            mBrowserLayout.hideBrowserController();
        } else {
            mBrowserLayout.showBrowserController();
        }

        mBrowserLayout.setOverrideUrlLoading(isOverrideUrlLoading);

        mBrowserLayout.setOnReceiveTitleListener(new BrowserLayout.OnReceiveTitleListener() {
            @Override
            public void onReceive(String title) {
                if (TextUtils.isEmpty(mWebTitle)) {
                    setTitle(title);
                }
            }

            @Override
            public void onPageFinished() {
                hideLoadingDialog();
            }
        });
        if (!TextUtils.isEmpty(mWebUrl)) {
            mBrowserLayout.loadUrl(mWebUrl);
        } else {
            ToastUtils.showToast("获取URL地址失败");
        }
    }

    @Override
    protected void onPause() {
        if (mBrowserLayout.getWebView() != null) {
            mBrowserLayout.getWebView().onPause();
            mBrowserLayout.getWebView().reload();
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBrowserLayout.getWebView() != null) {
            mBrowserLayout.getWebView().removeAllViews();
            mBrowserLayout.getWebView().destroy();
        }
    }
}
