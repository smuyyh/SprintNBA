package com.yuyh.cavaliers.ui.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.api.tencent.TencentService;
import com.yuyh.cavaliers.http.bean.match.MatchBaseInfo;
import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.MatchDetailView;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchDetailPresenter implements Presenter {

    private Context context;
    private MatchDetailView detailView;

    public MatchDetailPresenter(Context context, MatchDetailView detailView) {
        this.context = context;
        this.detailView = detailView;
    }

    @Override
    public void initialized() {
    }

    public void getTab(boolean isStart) {
        String names[];
        if (isStart) {
            names = new String[]{"比赛数据", "技术统计", "图文直播", "精彩视频"};
        } else {
            names = new String[]{"比赛前瞻", "图文直播"};
        }
        detailView.showTabViewPager(names, isStart);
    }

    public void getMatchBaseInfo(String mid) {
        detailView.showLoading("");
        TencentService.getMatchBaseInfo(mid, new RequestCallback<MatchBaseInfo.BaseInfo>() {
            @Override
            public void onSuccess(MatchBaseInfo.BaseInfo matchBaseInfo) {
                detailView.showMatchInfo(matchBaseInfo);
                detailView.hideLoading();
            }

            @Override
            public void onFailure(String message) {
                detailView.hideLoading();
            }
        });
    }
}
