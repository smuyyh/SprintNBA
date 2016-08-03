package com.yuyh.sprintnba.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yuyh.library.utils.StringUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.base.BaseAppManager;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.utils.RequestHelper;
import com.yuyh.sprintnba.ui.ImagePreViewActivity;
import com.yuyh.sprintnba.ui.LoginActivity;
import com.yuyh.sprintnba.ui.PostActivity;
import com.yuyh.sprintnba.ui.ReportActivity;
import com.yuyh.sprintnba.ui.ThreadDetailActivity;
import com.yuyh.sprintnba.ui.ThreadListActivity;
import com.yuyh.sprintnba.utils.SettingPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 虎扑帖子详情页
 */
public class HuPuWebView extends WebView {

    private String basicUA;
    private Map<String, String> header;

    RequestHelper mRequestHelper;

    public HuPuWebView(Context context) {
        this(context, null);
    }

    public HuPuWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuPuWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WebSettings settings = getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSupportMultipleWindows(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT > 6) {
            settings.setAppCacheEnabled(true);
            settings.setLoadWithOverviewMode(true);
        }
        String path = getContext().getFilesDir().getPath();
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(path);
        settings.setDomStorageEnabled(true);
        this.basicUA = settings.getUserAgentString() + " kanqiu/7.05.6303/7059";
        setBackgroundColor(0);
        initWebViewClient();
        setWebChromeClient(new HuPuChromeClient());
        try {
            if (SettingPrefUtils.isLogin()) {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com", "u=" + SettingPrefUtils.getCookies());
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com", "_gamesu=" + URLEncoder.encode(SettingPrefUtils.getToken(), "utf-8"));
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com", "_inKanqiuApp=1");
                cookieManager.setCookie("http://bbs.mobileapi.hupu.com", "_kanqiu=1");
                CookieSyncManager.getInstance().sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initWebViewClient() {
        CookieManager.getInstance().setAcceptCookie(true);
        setWebViewClient(new HupuWebClient());
    }


    public void setCallBack(HuPuWebViewCallBack callBack) {
        this.callBack = callBack;
    }

    public class HuPuChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            LogUtils.d("onConsoleMessage:" + consoleMessage.message() + ":" + consoleMessage.lineNumber());
            return true;
        }
    }

    private class HupuWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // 超链接监听
            LogUtils.d(Uri.decode(url));
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (scheme != null) {
                handleScheme(scheme, url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (callBack != null) {
                callBack.onFinish();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (callBack != null) {
                callBack.onError();
            }
        }
    }

    /**
     * 解析网页超链接
     *
     * @param scheme
     * @param url
     */
    private void handleScheme(String scheme, String url) {
        if (scheme != null) {
            if (scheme.equalsIgnoreCase("kanqiu")) {
                handleKanQiu(url);
            } else if (scheme.equalsIgnoreCase("browser")
                    || scheme.equalsIgnoreCase("http")
                    || scheme.equalsIgnoreCase("https")) {
                handleUrl(url);
            } else if (scheme.equalsIgnoreCase("hupu")) {
                try {
                    JSONObject object = new JSONObject(Uri.decode(url.substring("hupu".length() + 3)));
                    String method = object.optString("method");
                    String successcb = object.optString("successcb");
                    handleHuPu(method, object.getJSONObject("data"), successcb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleKanQiu(String url) {
        if (url.contains("topic")) {
            Uri uri = Uri.parse(url);
            String tid = uri.getLastPathSegment();
            LogUtils.d("tid:" + tid);
            String page = uri.getQueryParameter("page");
            LogUtils.d("page:" + page);
            String pid = uri.getQueryParameter("pid");
            LogUtils.d("pid:" + pid);
            Intent intent = new Intent(getContext(), ThreadDetailActivity.class);
            intent.putExtra(ThreadDetailActivity.INTENT_PID, pid);
            intent.putExtra(ThreadDetailActivity.INTENT_TID, tid);
            intent.putExtra(ThreadDetailActivity.INTENT_PAGE, TextUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
            intent.putExtra(ThreadDetailActivity.INTENT_FID, "");
            getContext().startActivity(intent);
        } else if (url.contains("board")) {
            String boardId = url.substring(url.lastIndexOf("/") + 1);
            Intent intent = new Intent(getContext(), ThreadListActivity.class);
            intent.putExtra(ThreadListActivity.INTENT_FORUM_ID, boardId);
            getContext().startActivity(intent);
        } else if (url.contains("people")) {
            String uid = url.substring(url.lastIndexOf("/") + 1);
            // TODO UserProfileActivity.startActivity(getContext(), uid);
        }
    }

    /**
     * 跳转
     *
     * @param url
     */
    private void handleUrl(String url) {
        Intent intent = new Intent(getContext(), BaseWebActivity.class);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, url);
        getContext().startActivity(intent);
    }

    private void handleHuPu(String method, JSONObject data, String successcb) throws Exception {
        switch (method) {
            case "bridgeReady":
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("hybridVer", "1.0");
                    jSONObject.put("supportAjax", true);
                    jSONObject.put("appVer", "7.0.5.6303");
                    jSONObject.put("appName", "com.hupu.games");
                    jSONObject.put("lowDevice", false);
                    jSONObject.put("scheme", "hupu");
                    jSONObject.put("did", Constant.deviceId);
                    jSONObject.put("platform", "Android");
                    jSONObject.put("device", Build.PRODUCT);
                    jSONObject.put("osVer", Build.VERSION.RELEASE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String js = "javascript:HupuBridge._handle_('"
                        + successcb
                        + "','"
                        + jSONObject.toString()
                        + "','null','null');";
                loadUrl(js);
                break;
            case "hupu.ui.updatebbspager":
                int page = data.getInt("page");
                int total = data.getInt("total");
                if (callBack != null) {
                    callBack.onUpdatePager(page, total);
                }
                break;
            case "hupu.ui.bbsreply":
                boolean open = data.getBoolean("open");
                JSONObject extra = data.getJSONObject("extra");
                String tid = extra.getString("tid");
                long pid = extra.getLong("pid");
                String userName = extra.getString("username");
                String content = extra.getString("content");
                if (open) {
                    Intent intent = new Intent(getContext(), PostActivity.class);
                    intent.putExtra(PostActivity.INTENT_TITLE, content);
                    intent.putExtra(PostActivity.INTENT_TYPE, Constant.TYPE_REPLY);
                    intent.putExtra(PostActivity.INTENT_FID, "");
                    intent.putExtra(PostActivity.INTENT_TID, tid);
                    intent.putExtra(PostActivity.INTENT_PID, String.valueOf(pid));
                    getContext().startActivity(intent);
                }
                break;
            case "hupu.album.view":
                int index = data.getInt("index");
                JSONArray images = data.getJSONArray("images");
                ArrayList<String> extraPics = new ArrayList<>();
                for (int i = 0; i < images.length(); i++) {
                    JSONObject image = images.getJSONObject(i);
                    extraPics.add(image.getString("url"));
                }
                Intent intent = new Intent(getContext(), ImagePreViewActivity.class);
                intent.putExtra(ImagePreViewActivity.INTENT_URLS, extraPics);
                intent.putExtra(ImagePreViewActivity.INTENT_URL, extraPics.get(index));
                getContext().startActivity(intent);
                break;
            case "hupu.ui.copy":
                String content1 = data.getString("content");
                StringUtils.copy(getContext(), content1);
                break;
            case "hupu.ui.report":
                JSONObject reportExtra = data.getJSONObject("extra");
                String reportTid = reportExtra.getString("tid");
                long reportPid = reportExtra.getLong("pid");
                Intent intent1 = new Intent(getContext(), ReportActivity.class);
                intent1.putExtra(ReportActivity.INTENT_TID, reportTid);
                intent1.putExtra(ReportActivity.INTENT_PID, String.valueOf(reportPid));
                getContext().startActivity(intent1);
                break;
            case "hupu.user.login":
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                ToastUtils.showToast("请先登录哦~");
                break;
            case "hupu.ui.pageclose":
                BaseAppManager.getInstance().getForwardActivity().finish();
                break;
        }
    }

    private void setUA(int i) {
        if (this.basicUA != null) {
            getSettings().setUserAgentString(this.basicUA + " isp/" + i + " network/" + i);
        }
    }

    public void loadUrl(String url) {
        LogUtils.d("loadUrl:" + url);
        setUA(-1);
        if (header == null) {
            header = new HashMap<>();
            header.put("Accept-Encoding", "gzip");
        }
        super.loadUrl(url, header);
    }

    private HuPuWebViewCallBack callBack;

    public interface HuPuWebViewCallBack {

        void onFinish();

        void onUpdatePager(int page, int total);

        void onError();
    }

    private OnScrollChangedCallback mOnScrollChangedCallback;

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt, t, oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public interface OnScrollChangedCallback {
        void onScroll(int dx, int dy, int y, int oldy);
    }
}
