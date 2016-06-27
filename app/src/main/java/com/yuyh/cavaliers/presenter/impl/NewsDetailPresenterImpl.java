package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yuyh.cavaliers.Interactor.NBANewsDetailInteractor;
import com.yuyh.cavaliers.Interactor.impl.NBANewsDetailInteractorImpl;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.presenter.NewsDetailPresenter;
import com.yuyh.cavaliers.ui.view.NewsDetailView;

/**
 * @author yuyh.
 * @date 16/6/7.
 */
public class NewsDetailPresenterImpl implements NewsDetailPresenter {

    private Context mContext = null;
    private NewsDetailView mNewsView = null;
    private NBANewsDetailInteractor mNewsDetailInteractor = null;

    public NewsDetailPresenterImpl(Context context, @NonNull NewsDetailView mNewsView) {

        mContext = context;
        this.mNewsView = mNewsView;
        mNewsDetailInteractor = new NBANewsDetailInteractorImpl();
    }

    @Override
    public void initialized(String arcId) {
        mNewsDetailInteractor.getNewsDetail(arcId, new RequestCallback<NewsDetail>() {
            @Override
            public void onSuccess(NewsDetail newsDetail) {
                mNewsView.showNewsDetail(newsDetail);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
