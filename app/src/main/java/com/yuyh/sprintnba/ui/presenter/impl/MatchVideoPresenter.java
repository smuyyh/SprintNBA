package com.yuyh.sprintnba.ui.presenter.impl;

import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.video.MatchVideo;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.MatchVideoView;

/**
 * @author yuyh.
 * @date 2016/12/30.
 */

public class MatchVideoPresenter implements Presenter {

    private MatchVideoView view;
    private String mid;

    public MatchVideoPresenter(MatchVideoView view, String mid) {
        this.view = view;
        this.mid = mid;
    }

    @Override
    public void initialized() {
        TencentService.getMatchVideo(mid, new RequestCallback<MatchVideo>() {
            @Override
            public void onSuccess(MatchVideo matchVideo) {
                if (matchVideo.data != null && !matchVideo.data.isEmpty()) {
                    view.showMatchVideo(matchVideo.data);
                } else {
                    view.showError("暂无数据");
                }
            }

            @Override
            public void onFailure(String message) {
                view.showError(message);
            }
        });
    }
}
