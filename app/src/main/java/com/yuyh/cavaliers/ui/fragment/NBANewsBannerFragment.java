package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.ui.adapter.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NBANewsBannerFragment extends BaseLazyFragment {
    private int tabIndex;
    public static final String INTENT_INT_INDEX = "intent_int_index";

    private MaterialRefreshLayout materialRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_nba_news_banner);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        setupRecyclerView();
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    }
                }, 3000);
            }

            @Override
            public void onfinish() {

            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 3000);
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        List<NewsItem.NewsItemBean> list = new ArrayList<NewsItem.NewsItemBean>(){{
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
            add(new NewsItem.NewsItemBean("http://inews.gtimg.com/newsapp_ls/0/335191175_640470/0","詹姆斯","2014-03-07"));
        }};
        recyclerView.setAdapter(new BannerAdapter(list, mActivity, R.layout.list_item_banner));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}
