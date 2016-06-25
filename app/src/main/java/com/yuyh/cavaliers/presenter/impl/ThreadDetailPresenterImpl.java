package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.yuyh.cavaliers.http.api.hupu.forum.HupuForumService;
import com.yuyh.cavaliers.http.bean.forum.ThreadsSchemaInfoData;
import com.yuyh.cavaliers.http.util.GetBeanCallback;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.ThreadDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ThreadDetailPresenterImpl implements Presenter {

    private Context context;
    private ThreadDetailView detailView;

    private String tid;
    private String fid;
    private String pid;
    private int page;

    private int totalPage;
    private int currentPage = 1;
    private List<String> urls = new ArrayList<>();
    private boolean isCollected;
    private String title;
    private String shareText;

    public ThreadDetailPresenterImpl(Context context, ThreadDetailView detailView) {
        this.context = context;
        this.detailView = detailView;
    }

    public void setParams(String tid, String fid, int page, String pid) {
        this.tid = tid;
        this.fid = fid;
        this.pid = pid;
        this.page = page;
        detailView.showLoading("");

    }


    @Override
    public void initialized() {
        HupuForumService.getThreadInfo(tid, fid, page, pid, new GetBeanCallback<ThreadsSchemaInfoData>() {
            @Override
            public void onSuccess(ThreadsSchemaInfoData threadSchemaInfo) {
                if (threadSchemaInfo != null) {
                    totalPage = threadSchemaInfo.pageSize;
                    currentPage = threadSchemaInfo.page;
                    urls = createPageList(threadSchemaInfo.url, threadSchemaInfo.page, threadSchemaInfo.pageSize);
                    shareText = threadSchemaInfo.share.weibo;
                    title = threadSchemaInfo.share.wechat_moments;
                    detailView.loadContent(page, urls);
                    isCollected = threadSchemaInfo.isCollected == 1;
                    detailView.isCollected(isCollected);
                    detailView.hideLoading();
                } else {
                    detailView.showError("加载失败");
                    detailView.hideLoading();
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private List<String> createPageList(String url, int page, int pageSize) {
        List<String> urls = new ArrayList<>();
        for (int i = 1; i <= pageSize; i++) {
            String newUrl = url.replace("page=" + page, "page=" + i);
            urls.add(newUrl);
        }
        return urls;
    }

    public void onPageNext() {
        currentPage++;
        if (currentPage >= totalPage) {
            currentPage = totalPage;
        }
        detailView.loadContent(currentPage - 1, urls);
    }

    public void onPagePre() {
        currentPage--;
        if (currentPage <= 1) {
            currentPage = 1;
        }
        detailView.loadContent(currentPage - 1, urls);
    }

    public void onCommendClick() {
        detailView.onToggleFloatingMenu();
    }

    public void onShareClick() {
        if (!TextUtils.isEmpty(shareText)) {
            //ShareUtils.share(mContext, shareText);
        }
        detailView.onToggleFloatingMenu();
    }

    public void onReportClick() {
        detailView.onToggleFloatingMenu();
    }

    public void onCollectClick() {
        detailView.onToggleFloatingMenu();
    }
}
