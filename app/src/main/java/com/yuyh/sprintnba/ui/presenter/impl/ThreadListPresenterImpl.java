package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.data.ACache;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.hupu.forum.HupuForumService;
import com.yuyh.sprintnba.http.api.hupu.game.HupuGamesService;
import com.yuyh.sprintnba.http.bean.forum.AttendStatusData;
import com.yuyh.sprintnba.http.bean.forum.ForumsData;
import com.yuyh.sprintnba.http.bean.forum.SearchListData;
import com.yuyh.sprintnba.http.bean.forum.ThreadListData;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.ThreadListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String key;

    public int loadType = TYPE_LIST;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_SEARCH = 2;

    List<ThreadListData.ThreadInfo> list = new ArrayList<>();

    public ThreadListPresenterImpl(String fid, Context context, ThreadListView threadListView) {
        this.fid = fid;
        this.context = context;
        this.mThreadListView = threadListView;
    }

    public void onThreadReceive(String type, String last, boolean isRefresh) {
        mThreadListView.showLoading("");
        mThreadListView.onFloatingVisibility(View.VISIBLE);
        this.type = type;
        loadType = TYPE_LIST;
        loadThreadList(last, isRefresh);
        // TODO 获取版块关注状态
        getAttendStatus();
    }

    public void onStartSearch(String key, int page, boolean isRefresh) {
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showSingleToast("搜索词不能为空");
            return;
        }
        mThreadListView.showLoading("");
        mThreadListView.onFloatingVisibility(View.GONE);
        pageIndex = page;
        this.key = key;
        loadType = TYPE_SEARCH;
        loadSearchList(isRefresh);
    }

    private void loadThreadList(String last, final boolean isRefresh) {
        LogUtils.i("last=" + last + " isRefresh=" + isRefresh);
        HupuForumService.getForumPosts(fid, last, 20, lastTamp, type, new RequestCallback<ThreadListData>() {
            @Override
            public void onSuccess(ThreadListData threadListData) {
                if (threadListData != null && threadListData.result != null && threadListData.result.data != null) {
                    mThreadListView.showThreadList(threadListData.result.data, isRefresh);
                    mThreadListView.onLoadCompleted(threadListData.result.nextPage);
                } else {
                    mThreadListView.showError("没有更多啦");
                    mThreadListView.onLoadCompleted(false);
                }
                mThreadListView.hideLoading();
                mThreadListView.onRefreshCompleted();
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


    private void loadSearchList(final boolean isRefresh) {
        HupuGamesService.search(key, fid, pageIndex, new RequestCallback<SearchListData>() {
            @Override
            public void onSuccess(SearchListData searchData) {
                if (searchData != null) {
                    if (searchData.error != null) {
                        ToastUtils.showSingleToast(searchData.error.msg);
                    } else if (searchData.result != null) {
                        List<ThreadListData.ThreadInfo> list = new ArrayList<>();
                        SearchListData.SearchResult result = searchData.result;
                        for (SearchListData.Search search : result.data) {
                            ThreadListData.ThreadInfo thread = new ThreadListData.ThreadInfo();
                            thread.fid = search.fid;
                            thread.tid = search.id;
                            thread.lightReply = Integer.valueOf(search.lights);
                            thread.replies = search.replies;
                            thread.userName = search.username;
                            thread.title = search.title;
                            long time = Long.valueOf(search.addtime);
                            Date date = new Date(time * 1000);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            thread.time = format.format(date);
                            list.add(thread);
                        }
                        mThreadListView.showThreadList(list, isRefresh);
                        mThreadListView.onLoadCompleted(result.hasNextPage == 1 ? true : false);
                    } else {
                        ToastUtils.showSingleToast("找不到相应内容哦");
                    }
                } else {
                    ToastUtils.showSingleToast("找不到相应内容哦");
                }
                mThreadListView.hideLoading();
            }

            @Override
            public void onFailure(String message) {
                ToastUtils.showSingleToast("查询失败");
                mThreadListView.hideLoading();
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

    public void onAttentionClick() {
        // TODO attention
        ToastUtils.showSingleToast("暂不支持哦~");
    }

    public void onRefresh() {
        mThreadListView.onScrollToTop();
        if (loadType == TYPE_LIST) {
            loadThreadList("", true);
        } else {
            pageIndex = 1;
            loadSearchList(true);
        }
    }

    public void getForumInfo() {

        final String key = "getAllForums";
        final ACache cache = ACache.get(AppUtils.getAppContext());
        ForumsData data = (ForumsData) cache.getAsObject(key);
        boolean complete = false;
        if (data != null) {
            ArrayList<ForumsData.ForumsResult> list = data.data; // 所有大版块-> NBA/CBA/...
            for (ForumsData.ForumsResult result : list) {
                if (complete)
                    break;
                ArrayList<ForumsData.Forums> forumsList = result.sub; // 小版块类别 主版/球队分区/其他/...
                for (ForumsData.Forums forums : forumsList) {
                    if (complete)
                        break;
                    ArrayList<ForumsData.Forum> forumList = forums.data; // 版块列表 骑士/火箭/...
                    for (ForumsData.Forum forum : forumList) {
                        if (forum.fid.equals(fid)) {
                            mThreadListView.showThreadInfo(forum);
                            complete = true;
                            break;
                        }
                    }
                }
            }
        }
    }
}
