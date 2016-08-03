package com.yuyh.sprintnba.ui.Interactor.impl;

import com.yuyh.sprintnba.ui.Interactor.NBANewsDetailInteractor;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.news.NewsDetail;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.app.Constant;

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
