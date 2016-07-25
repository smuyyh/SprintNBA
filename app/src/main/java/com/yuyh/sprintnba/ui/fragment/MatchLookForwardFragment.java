package com.yuyh.sprintnba.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.event.RefreshEvent;
import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.sprintnba.ui.adapter.MatchHistoryAdapter;
import com.yuyh.sprintnba.ui.adapter.MatchLMaxPlayerdapter;
import com.yuyh.sprintnba.ui.adapter.MatchRecentAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.MatchLookForwardPresenter;
import com.yuyh.sprintnba.ui.view.MatchLookForwardView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class MatchLookForwardFragment extends BaseLazyFragment implements MatchLookForwardView {


    @InjectView(R.id.llMaxPlayer)
    LinearLayout llMaxPlayer;
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

    @InjectView(R.id.llHistoryMatchs)
    LinearLayout llHistoryMatchs;
    @InjectView(R.id.tvHistoryMatchs)
    TextView tvHistoryMatchs;
    @InjectView(R.id.lvHistoryMatchs)
    ListView lvHistoryMatchs;

    @InjectView(R.id.llRecentMatchs)
    LinearLayout llRecentMatchs;
    @InjectView(R.id.tvRecentMatchs)
    TextView tvRecentMatchs;
    @InjectView(R.id.tvRecentTitleLeft)
    TextView tvRecentTitleLeft;
    @InjectView(R.id.tvRecentTitleRight)
    TextView tvRecentTitleRight;
    @InjectView(R.id.lvRecentMatchs)
    ListView lvRecentMatchs;

    @InjectView(R.id.llFutureMatchs)
    LinearLayout llFutureMatchs;
    @InjectView(R.id.tvFutureMatchs)
    TextView tvFutureMatchs;
    @InjectView(R.id.tvFutureTitleLeft)
    TextView tvFutureTitleLeft;
    @InjectView(R.id.tvFutureTitleRight)
    TextView tvFutureTitleRight;
    @InjectView(R.id.lvFutureMatchs)
    ListView lvFutureMatchs;

    private MatchLookForwardPresenter presenter;
    private List<MatchStat.MatchStatInfo.StatsBean.TeamMatchs.TeamMatchsTeam> recentList = new ArrayList<>();
    private MatchRecentAdapter recentAdapter;
    private List<MatchStat.MatchStatInfo.StatsBean.TeamMatchs.TeamMatchsTeam> futureList = new ArrayList<>();
    private MatchRecentAdapter futureAdapter;
    private List<MatchStat.MatchStatInfo.StatsBean.VS> vs = new ArrayList<>();
    private MatchHistoryAdapter hisAdapter;
    private List<MatchStat.MatchStatInfo.StatsBean.MaxPlayers> maxPlayers = new ArrayList<>();
    private MatchLMaxPlayerdapter playerdapter;

    private int recentCurrent = 0;
    private int futureCurrent = 0;

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
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        showLoadingDialog();
        lvMaxPlayer.setFocusable(false);
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
        tvLeftTeamName.setText(info.leftName);
        tvRightTeamName.setText(info.rightName);
        tvRecentTitleLeft.setText(info.leftName);
        tvRecentTitleRight.setText(info.rightName);
        tvFutureTitleLeft.setText(info.leftName);
        tvFutureTitleRight.setText(info.rightName);
        rlMatchTeam.setVisibility(View.VISIBLE);
        hideLoadingDialog();
    }

    @Override
    public void showMaxPlayer(List<MatchStat.MatchStatInfo.StatsBean.MaxPlayers> maxPlayers) {
        this.maxPlayers.clear();
        this.maxPlayers.addAll(maxPlayers);
        if(playerdapter == null) {
            playerdapter = new MatchLMaxPlayerdapter(this.maxPlayers, mActivity, R.layout.item_list_maxplayer);
            lvMaxPlayer.setAdapter(playerdapter);
        }
        playerdapter.notifyDataSetChanged();
        llMaxPlayer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHistoryMatchs(List<MatchStat.MatchStatInfo.StatsBean.VS> vs) {
        this.vs.clear();
        this.vs.addAll(vs);
        if(hisAdapter == null) {
            hisAdapter = new MatchHistoryAdapter(this.vs, mActivity, R.layout.item_list_match_recent);
            lvHistoryMatchs.setAdapter(hisAdapter);
        }
        hisAdapter.notifyDataSetChanged();
        llHistoryMatchs.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecentMatchs(final MatchStat.MatchStatInfo.StatsBean.TeamMatchs teamMatches) {
        recentList.clear();
        recentList.addAll(teamMatches.left);
        if (recentAdapter == null)
            recentAdapter = new MatchRecentAdapter(true, recentList, mActivity, R.layout.item_list_match_recent);
        lvRecentMatchs.setAdapter(recentAdapter);
        tvRecentTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recentCurrent != 0) {
                    recentList.clear();
                    recentList.addAll(teamMatches.left);
                    recentAdapter.notifyDataSetChanged();
                    tvRecentTitleRight.setBackgroundColor(getResources().getColor(R.color.entity_layout));
                    tvRecentTitleLeft.setBackgroundColor(getResources().getColor(R.color.white));
                    recentCurrent = 0;
                }
            }
        });
        tvRecentTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recentCurrent == 0) {
                    recentList.clear();
                    recentList.addAll(teamMatches.right);
                    recentAdapter.notifyDataSetChanged();
                    tvRecentTitleRight.setBackgroundColor(getResources().getColor(R.color.white));
                    tvRecentTitleLeft.setBackgroundColor(getResources().getColor(R.color.entity_layout));
                    recentCurrent = 1;
                }
            }
        });
        llRecentMatchs.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFutureMatchs(final MatchStat.MatchStatInfo.StatsBean.TeamMatchs teamMatches) {
        futureList.clear();
        futureList.addAll(teamMatches.left);
        if (futureAdapter == null)
            futureAdapter = new MatchRecentAdapter(false, futureList, mActivity, R.layout.item_list_match_recent);
        lvFutureMatchs.setAdapter(futureAdapter);
        tvFutureTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (futureCurrent != 0) {
                    futureList.clear();
                    futureList.addAll(teamMatches.left);
                    futureAdapter.notifyDataSetChanged();
                    tvFutureTitleRight.setBackgroundColor(getResources().getColor(R.color.entity_layout));
                    tvFutureTitleLeft.setBackgroundColor(getResources().getColor(R.color.white));
                    futureCurrent = 0;
                }
            }
        });
        tvFutureTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (futureCurrent == 0) {
                    futureList.clear();
                    futureList.addAll(teamMatches.right);
                    futureAdapter.notifyDataSetChanged();
                    tvFutureTitleRight.setBackgroundColor(getResources().getColor(R.color.white));
                    tvFutureTitleLeft.setBackgroundColor(getResources().getColor(R.color.entity_layout));
                    futureCurrent = 1;
                }
            }
        });
        llFutureMatchs.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        hideLoadingDialog();
    }

    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        presenter.getMatchStat(getArguments().getString("mid"), "3");
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        EventBus.getDefault().unregister(this);
    }
}
