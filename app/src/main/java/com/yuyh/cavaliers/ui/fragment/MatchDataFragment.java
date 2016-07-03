package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.bean.match.MatchStat;
import com.yuyh.cavaliers.ui.presenter.impl.MatchDataPresenter;
import com.yuyh.cavaliers.ui.view.MatchDataView;
import com.yuyh.library.utils.toast.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 比赛数据
 *
 * @author yuyh.
 * @date 16/6/5.
 */
public class MatchDataFragment extends BaseLazyFragment implements MatchDataView {

    @InjectView(R.id.tvMatchPoint)
    TextView tvMatchPoint;
    @InjectView(R.id.lvMatchPoint)
    ListView lvMatchPoint;
    @InjectView(R.id.tvMatchTeamStatistics)
    TextView tvMatchTeamStatistics;
    @InjectView(R.id.lvMatchTeamStatistics)
    ListView lvMatchTeamStatistics;

    private MatchDataPresenter presenter;

    public static MatchDataFragment newInstance(String mid) {
        Bundle args = new Bundle();
        args.putString("mid", mid);
        MatchDataFragment fragment = new MatchDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_match_data);
        ButterKnife.inject(this, getContentView());
        initData();
    }

    private void initData() {
        presenter = new MatchDataPresenter(mActivity, this);
        presenter.initialized();
        presenter.getMatchStats(getArguments().getString("mid"), "1");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showSingleToast(msg);
    }

    @Override
    public void showMatchPoint(List<MatchStat.MatchStatInfo.StatsBean.Goals> list) {

    }

    @Override
    public void showTeamStatistics(List<MatchStat.MatchStatInfo.StatsBean.TeamStats> teamStats) {

    }
}
