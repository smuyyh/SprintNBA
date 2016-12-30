package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;

import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.MatchPlayerDataView;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/5.
 */
public class MatchPlayerDataPresenter implements Presenter {

    private Context context;
    private MatchPlayerDataView dataView;
    private String mid;

    public MatchPlayerDataPresenter(Context context, MatchPlayerDataView dataView, String mid) {
        this.context = context;
        this.dataView = dataView;
        this.mid = mid;
    }

    @Override
    public void initialized() {
        dataView.showLoading("");
        TencentService.getMatchStat(mid, "2", new RequestCallback<MatchStat>() {
                    @Override
                    public void onSuccess(MatchStat matchStat) {
                        boolean hasData = false;
                        MatchStat.MatchStatInfo data = matchStat.data;
                        List<MatchStat.StatsBean> stats = data.stats;
                        for (MatchStat.StatsBean bean : stats) {
                            if (bean.type.equals("15")) {
                                if (bean.playerStats != null && !bean.playerStats.isEmpty()) {
                                    dataView.showPlayerData(bean.playerStats);
                                    hasData = true;
                                }
                            }
                        }
                        if (!hasData) {
                            dataView.showError("暂无数据");
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        dataView.hideLoading();
                        dataView.showError(message);
                    }
                }

        );
    }
}
