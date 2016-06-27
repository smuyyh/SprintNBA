package com.yuyh.cavaliers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.base.BaseWebActivity;
import com.yuyh.cavaliers.http.api.nba.TencentService;
import com.yuyh.cavaliers.http.bean.news.NewsIndex;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.recycleview.SupportRecyclerView;
import com.yuyh.cavaliers.ui.NewsDetailActivity;
import com.yuyh.cavaliers.ui.adapter.BannerAdapter;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class NBANewsBannerFragment extends BaseLazyFragment {

    public static final String INTENT_INT_INDEX = "intent_int_index";

    private MaterialRefreshLayout materialRefreshLayout;
    private SupportRecyclerView recyclerView;
    private View emptyView;
    private BannerAdapter adapter;
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
        recyclerView = (SupportRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        emptyView = findViewById(R.id.tvEmptyView);
        emptyView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                requestIndex(true);
            }
        });
        adapter = new BannerAdapter(list, mActivity, R.layout.list_item_banner);
        adapter.setOnItemClickListener(new OnListItemClickListener<NewsItem.NewsItemBean>() {
            @Override
            public void onItemClick(View view, int position, NewsItem.NewsItemBean data) {
                Intent intent = null;
                switch (newsType) {
                    case VIDEO:
                    case DEPTH:
                    case HIGHLIGHT:
                        intent = new Intent(mActivity, BaseWebActivity.class);
                        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, data.getUrl());
                        intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, data.getTitle());
                        startActivity(intent);
                        break;
                    case BANNER:
                    case NEWS:
                    default:
                        intent = new Intent(mActivity, NewsDetailActivity.class);
                        intent.putExtra(NewsDetailActivity.TITLE, data.getTitle());
                        intent.putExtra(NewsDetailActivity.ARTICLE_ID, data.getIndex());
                        startActivity(intent);
                        break;

                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(5)));
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new RefreshListener());
    }

    private void requestIndex(final boolean isRefresh) {
        TencentService.getNewsIndex(newsType, isRefresh, new RequestCallback<NewsIndex>() {
            @Override
            public void onSuccess(NewsIndex newsIndex) {
                recyclerView.setEmptyView(emptyView);
                indexs.clear();
                start = 0;
                for (NewsIndex.IndexBean bean : newsIndex.data) {
                    indexs.add(bean.getId());
                }
                String arcIds = parseIds();
                requestNews(arcIds, isRefresh, false);
                complete();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setEmptyView(emptyView);
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
                list.addAll(newsItem.getData());
                adapter.notifyDataSetChanged();
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
        public void onfinish() {

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
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}
