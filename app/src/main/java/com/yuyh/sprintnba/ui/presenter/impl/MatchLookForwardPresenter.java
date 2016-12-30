package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;

import com.yuyh.sprintnba.event.RefreshCompleteEvent;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.MatchLookForwardView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchLookForwardPresenter implements Presenter {

    private Context context;
    private MatchLookForwardView forwardView;

    public MatchLookForwardPresenter(Context context, MatchLookForwardView forwardView) {
        this.context = context;
        this.forwardView = forwardView;
    }

    @Override
    public void initialized() {
    }

    public void getMatchStat(String mid, String tabType) {
        TencentService.getMatchStat(mid, tabType, new RequestCallback<MatchStat>() {
            @Override
            public void onSuccess(MatchStat matchStat) {
                MatchStat.MatchStatInfo data = matchStat.data;
                forwardView.showTeamInfo(data.teamInfo);
                List<MatchStat.StatsBean> stats = data.stats;
                for (MatchStat.StatsBean bean : stats) {
                    if (bean.type.equals("1")) {
                        if (bean.vs != null && !bean.vs.isEmpty()) {
                            forwardView.showHistoryMatchs(bean.vs);
                        }
                    } else if (bean.type.equals("2")) {
                        if (bean.teamMatches != null && bean.teamMatches.left != null && bean.teamMatches.right != null) {
                            forwardView.showRecentMatchs(bean.teamMatches);
                        }
                    } else if (bean.type.equals("3")) {
                        if (bean.teamMatches != null && bean.teamMatches.left != null && bean.teamMatches.right != null) {
                            forwardView.showFutureMatchs(bean.teamMatches);
                        }
                    } else if (bean.type.equals("13")) {
                        if (bean.maxPlayers != null && !bean.maxPlayers.isEmpty()) {
                            forwardView.showMaxPlayer(bean.maxPlayers);
                        }
                    }
                }
                EventBus.getDefault().post(new RefreshCompleteEvent());
            }

            @Override
            public void onFailure(String message) {
                forwardView.showError(message);
                EventBus.getDefault().post(new RefreshCompleteEvent());
            }
        });
    }
}
