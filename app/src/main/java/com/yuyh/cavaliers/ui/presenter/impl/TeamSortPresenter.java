package com.yuyh.cavaliers.ui.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.api.tencent.TencentService;
import com.yuyh.cavaliers.http.bean.player.TeamsRank;
import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.TeamSortView;

/**
 * @author yuyh.
 * @date 2016/7/5.
 */
public class TeamSortPresenter implements Presenter {

    private Context context;
    private TeamSortView sortView;

    public TeamSortPresenter(Context context, TeamSortView sortView) {
        this.context = context;
        this.sortView = sortView;
    }

    @Override
    public void initialized() {

    }

    public void requestTeamsRank(boolean isRefresh) {
        sortView.showLoading("");
        TencentService.getTeamsRank(isRefresh, new RequestCallback<TeamsRank>() {
            @Override
            public void onSuccess(TeamsRank teamsRank) {
                if (teamsRank != null && teamsRank.all != null) {
                    sortView.showTeamSort(teamsRank.all);
                } else {
                    sortView.showError("暂无数据");
                }
            }

            @Override
            public void onFailure(String message) {
                sortView.showError(message);
            }
        });
    }

}
