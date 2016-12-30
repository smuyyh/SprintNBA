package com.yuyh.sprintnba.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.news.NewsIndex;
import com.yuyh.sprintnba.http.bean.news.NewsItem;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.support.SpaceItemDecoration;
import com.yuyh.sprintnba.support.SupportRecyclerView;
import com.yuyh.sprintnba.ui.NewsDetailActivity;
import com.yuyh.sprintnba.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class NewsListFragment extends BaseLazyFragment {

    public static final String INTENT_INT_INDEX = "intent_int_index";
    @InjectView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @InjectView(R.id.recyclerview)
    SupportRecyclerView recyclerView;
    @InjectView(R.id.emptyView)
    View emptyView;
    private NewsAdapter adapter;
    private List<NewsItem.NewsItemBean> list = new ArrayList<>();
    private List<String> indexs = new ArrayList<>();
    private int start = 0; // 查询数据起始位置
    private int num = 10;

    Constant.NewsType newsType = Constant.NewsType.BANNER;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_normal_recyclerview);

        showLoadingDialog();
        newsType = (Constant.NewsType) getArguments().getSerializable(INTENT_INT_INDEX);
        initView();
        requestIndex(false);
    }

    private void initView() {
        adapter = new NewsAdapter(list, mActivity);
        adapter.setOnItemClickListener(new OnListItemClickListener<NewsItem.NewsItemBean>() {
            @Override
            public void onItemClick(View view, int position, NewsItem.NewsItemBean data) {
                Intent intent;
                switch (newsType) {
                    case VIDEO:
                    case DEPTH:
                    case HIGHLIGHT:
                        BaseWebActivity.start(mActivity, data.url, data.title, true, true);
                        break;
                    case BANNER:
                    case NEWS:
                    default:
                        NewsDetailActivity.start(mActivity, data.title, data.index);
                        break;

                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(5)));
        materialRefreshLayout.setMaterialRefreshListener(new RefreshListener());
    }

    private void requestIndex(final boolean isRefresh) {
        TencentService.getNewsIndex(newsType, isRefresh, new RequestCallback<NewsIndex>() {
            @Override
            public void onSuccess(NewsIndex newsIndex) {
                indexs.clear();
                start = 0;
                for (NewsIndex.IndexBean bean : newsIndex.data) {
                    indexs.add(bean.id);
                }
                String arcIds = parseIds();
                requestNews(arcIds, isRefresh, false);
            }

            @Override
            public void onFailure(String message) {
                complete();
                LogUtils.i(message);
            }
        });
    }

    private void requestNews(String arcIds, final boolean isRefresh, final boolean isLoadMore) {
        TencentService.getNewsItem(newsType, arcIds, isRefresh, new RequestCallback<NewsItem>() {
            @Override
            public void onSuccess(NewsItem newsItem) {
                if (isRefresh)
                    list.clear();
                list.addAll(newsItem.data);
                complete();
            }

            @Override
            public void onFailure(String message) {
                complete();
            }
        });

    }

    private String parseIds() {
        int size = indexs.size();
        String articleIds = "";
        for (int i = start, j = 0; i < size && j < num; i++, j++, start++) {
            articleIds += indexs.get(i) + ",";
        }
        if (!TextUtils.isEmpty(articleIds))
            articleIds = articleIds.substring(0, articleIds.length() - 1);
        LogUtils.i("articleIds = " + articleIds);
        return articleIds;
    }

    private class RefreshListener extends MaterialRefreshListener {
        @Override
        public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
            requestIndex(true);
        }

        @Override
        public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
            LogUtils.i("load more: start=" + start);
            String arcIds = parseIds();
            if (!TextUtils.isEmpty(arcIds)) {
                requestNews(arcIds, false, true);
            } else {
                ToastUtils.showToast("已经到底啦");
                complete();
            }
        }
    }

    private void complete() {
        recyclerView.setEmptyView(emptyView);
        adapter.notifyDataSetChanged();
        materialRefreshLayout.finishRefresh();
        materialRefreshLayout.finishRefreshLoadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1000);
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}
