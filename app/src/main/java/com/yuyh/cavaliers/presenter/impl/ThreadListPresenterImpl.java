package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;
import android.view.View;

import com.yuyh.cavaliers.http.api.hupu.forum.HupuForumService;
import com.yuyh.cavaliers.http.bean.forum.AttendStatusData;
import com.yuyh.cavaliers.http.bean.forum.ThreadListData;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.ThreadListView;
import com.yuyh.library.utils.log.LogUtils;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public class ThreadListPresenterImpl implements Presenter {

    private Context context;
    private ThreadListView mThreadListView;

    private String fid;
    private String lastTid = "";
    private String lastTamp = "";
    private String type;
    private int pageIndex;

    private int loadType = TYPE_LIST;
    private static final int TYPE_LIST = 1;
    private static final int TYPE_SEARCH = 2;

    public ThreadListPresenterImpl(String fid, Context context, ThreadListView threadListView) {
        this.fid = fid;
        this.context = context;
        this.mThreadListView = threadListView;
    }

    public void onThreadReceive(String type) {
        mThreadListView.showLoading("");
        mThreadListView.onFloatingVisibility(View.VISIBLE);
        this.type = type;
        loadType = TYPE_LIST;
        loadThreadList("", true);
        // TODO 获取版块关注状态
        getAttendStatus();
    }

    private void loadThreadList(String last, final boolean isRefresh) {
        HupuForumService.getForumPosts(fid, last, 20, lastTamp, type, new RequestCallback<ThreadListData>() {
            @Override
            public void onSuccess(ThreadListData threadListData) {
                if (threadListData!=null && threadListData.result != null && threadListData.result.data != null) {
                    mThreadListView.showThreadList(threadListData.result.data, isRefresh);
                } else {
                    mThreadListView.showError("没有更多啦");
                }
                mThreadListView.hideLoading();
                mThreadListView.onRefreshCompleted();
                //mThreadListView.onLoadCompleted(threadListData.result.nextPage);
            }

            @Override
            public void onFailure(String message) {
                LogUtils.e(message);
                mThreadListView.showError("数据加载失败");
                mThreadListView.hideLoading();
                mThreadListView.onRefreshCompleted();
                mThreadListView.onLoadCompleted(false);
            }
        });
    }


    @Override
    public void initialized() {

    }

    public void getAttendStatus() {
        HupuForumService.getAttentionStatus(fid, new RequestCallback<AttendStatusData>() {
            @Override
            public void onSuccess(AttendStatusData attendStatusData) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
