package com.yuyh.cavaliers.Interactor.impl;

import com.yuyh.cavaliers.Interactor.NBANewsDetailInteractor;
import com.yuyh.cavaliers.http.Request;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.http.constant.Constant;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class NBANewsDetailInteractorImpl implements NBANewsDetailInteractor {
    @Override
    public void getNewsDetail(String arcId, GetBeanCallback<NewsDetail> callback) {
        Request.getNewsDetail(Constant.NewsType.BANNER, arcId, false, callback);
    }
}
