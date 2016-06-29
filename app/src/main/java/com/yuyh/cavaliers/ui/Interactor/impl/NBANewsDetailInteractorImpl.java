package com.yuyh.cavaliers.ui.Interactor.impl;

import com.yuyh.cavaliers.ui.Interactor.NBANewsDetailInteractor;
import com.yuyh.cavaliers.http.api.tencent.TencentService;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.constant.Constant;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class NBANewsDetailInteractorImpl implements NBANewsDetailInteractor {
    @Override
    public void getNewsDetail(String arcId, RequestCallback<NewsDetail> callback) {
        TencentService.getNewsDetail(Constant.NewsType.BANNER, arcId, false, callback);
    }
}
