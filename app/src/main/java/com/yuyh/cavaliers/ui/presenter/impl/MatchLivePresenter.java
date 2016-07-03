package com.yuyh.cavaliers.ui.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.api.tencent.TencentService;
import com.yuyh.cavaliers.http.bean.match.LiveDetail;
import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.MatchLiveView;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchLivePresenter implements Presenter {

    private Context context;
    private MatchLiveView liveView;

    public MatchLivePresenter(Context context, MatchLiveView liveView) {
        this.context = context;
        this.liveView = liveView;
    }

    @Override
    public void initialized() {

    }

    public void getLiveContent(String mid) {
        TencentService.getMatchLiveDetail(mid, "2114250_3512017050,2114245_3049673407,2114240_316335712,2114239_2708956373", new RequestCallback<LiveDetail>() {
            @Override
            public void onSuccess(LiveDetail liveDetail) {
                liveView.addList(liveDetail.data.detail);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
