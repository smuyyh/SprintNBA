package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.bean.match.MatchStat;
import com.yuyh.cavaliers.ui.adapter.MatchLFAdapter;
import com.yuyh.cavaliers.ui.presenter.impl.MatchLookForwardPresenter;
import com.yuyh.cavaliers.ui.view.MatchLookForwardView;
import com.yuyh.library.utils.toast.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class MatchLookForwardFragment extends BaseLazyFragment implements MatchLookForwardView {


    @InjectView(R.id.tvMaxPlayer)
    TextView tvMaxPlayer;
    @InjectView(R.id.lvMaxPlayer)
    ListView lvMaxPlayer;
    @InjectView(R.id.rlMatchTeam)
    RelativeLayout rlMatchTeam;
    @InjectView(R.id.tvLeftTeamName)
    TextView tvLeftTeamName;
    @InjectView(R.id.tvRightTeamName)
    TextView tvRightTeamName;

    @InjectView(R.id.tvHistoryMatchs)
    TextView tvHistoryMatchs;
    @InjectView(R.id.lvHistoryMatchs)
    ListView lvHistoryMatchs;

    @InjectView(R.id.tvRecentMatchs)
    TextView tvRecentMatchs;
    @InjectView(R.id.lvRecentMatchs)
    ListView lvRecentMatchs;

    @InjectView(R.id.tvFutureMatchs)
    TextView tvFutureMatchs;
    @InjectView(R.id.lvFutureMatchs)
    ListView lvFutureMatchs;

    private MatchLookForwardPresenter presenter;
    private MatchStat.MatchStatInfo.MatchTeamInfo info;

    public static MatchLookForwardFragment newInstance(String mid) {
        Bundle args = new Bundle();
        args.putString("mid", mid);
        MatchLookForwardFragment fragment = new MatchLookForwardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_match_look_forward);
        ButterKnife.inject(this, getContentView());
        initData();
    }

    private void initData() {
        presenter = new MatchLookForwardPresenter(mActivity, this);
        presenter.initialized();
        presenter.getMatchStat(getArguments().getString("mid"), "3");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void showTeamInfo(MatchStat.MatchStatInfo.MatchTeamInfo info) {
        this.info = info;
        tvLeftTeamName.setText(info.leftName);
        tvRightTeamName.setText(info.rightName);
        rlMatchTeam.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMaxPlayer(List<MatchStat.MatchStatInfo.StatsBean.MaxPlayers> maxPlayers) {
        MatchLFAdapter adapter = new MatchLFAdapter(maxPlayers, mActivity, R.layout.item_list_maxplayer);
        lvMaxPlayer.setAdapter(adapter);
        lvMaxPlayer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHistoryMatchs(List<MatchStat.MatchStatInfo.StatsBean.VS> vs) {

    }

    @Override
    public void showRecentMatchs(MatchStat.MatchStatInfo.StatsBean.TeamMatchs teamMatches) {

    }

    @Override
    public void showFutureMatchs(MatchStat.MatchStatInfo.StatsBean.TeamMatchs teamMatches) {

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
}
