package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;

import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.player.TeamsRank;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.TeamSortView;

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
