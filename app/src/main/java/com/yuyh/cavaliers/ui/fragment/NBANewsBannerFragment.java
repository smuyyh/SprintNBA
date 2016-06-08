package com.yuyh.cavaliers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.recycleview.SpaceItemDecoration;
import com.yuyh.cavaliers.http.Request;
import com.yuyh.cavaliers.http.bean.news.NewsIndex;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.ui.NewsDetailActivity;
import com.yuyh.cavaliers.ui.adapter.BannerAdapter;
import com.yuyh.cavaliers.recycleview.OnRecyclerViewItemClickListener;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class NBANewsBannerFragment extends BaseLazyFragment {
    private int tabIndex;
    public static final String INTENT_INT_INDEX = "intent_int_index";

    private MaterialRefreshLayout materialRefreshLayout;
    private RecyclerView recyclerView;
    private BannerAdapter adapter;
    private List<NewsItem.NewsItemBean> list = new ArrayList<>();
    private List<String> indexs = new ArrayList<>();
    private int start = 0; // 查询数据起始位置
    private int num = 10;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_nba_news_banner);
        initView();
        requestIndex(false);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new BannerAdapter(list, mActivity, R.layout.list_item_banner);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<NewsItem.NewsItemBean>() {
            @Override
            public void onItemClick(View view, int position, NewsItem.NewsItemBean data) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.TITLE, data.getTitle());
                intent.putExtra(NewsDetailActivity.ARTICLE_ID, data.getIndex());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(5)));
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new RefreshListener());
    }

    private void requestIndex(final boolean isRefresh) {
        Request.getNewsIndex(Constant.NewsType.BANNER, true, new GetBeanCallback<NewsIndex>() {
            @Override
            public void onSuccess(NewsIndex newsIndex) {
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
                complete();
                LogUtils.i(message);
            }
        });
    }

    private void requestNews(String arcIds, final boolean isRefresh, final boolean isLoadMore) {
        Request.getNewsItem(Constant.NewsType.BANNER, arcIds, isRefresh, new GetBeanCallback<NewsItem>() {
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
            requestNews(arcIds, false, true);
        }
    }

    private void complete() {
        materialRefreshLayout.finishRefresh();
        materialRefreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}
