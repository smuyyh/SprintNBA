package com.yuyh.cavaliers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.ui.PlayerListActivity;
import com.yuyh.cavaliers.ui.TeamsListActivity;
import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.data.ACache;
import com.yuyh.library.utils.data.PrefsUtils;
import com.yuyh.library.utils.toast.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class OtherFragment extends BaseLazyFragment {

    @InjectView(R.id.rlPlayer)
    RelativeLayout rlPlayer;
    @InjectView(R.id.rlTeam)
    RelativeLayout rlTeam;
    @InjectView(R.id.rlTeamSchedule)
    RelativeLayout rlTeamSchedule;
    @InjectView(R.id.rlNBACal)
    RelativeLayout rlNBACal;
    @InjectView(R.id.rlClearCache)
    RelativeLayout rlClearCache;
    @InjectView(R.id.rlFeedback)
    RelativeLayout rlFeedback;
    @InjectView(R.id.rlAbout)
    RelativeLayout rlAbout;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_other);
        ButterKnife.inject(this, getContentView());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @OnClick(R.id.rlClearCache)
    public void clearCache() {
        PrefsUtils prefs = new PrefsUtils();
        prefs.clearAll();
        ACache cache = ACache.get(AppUtils.getAppContext());
        cache.clear();
        ToastUtils.showSingleLongToast("缓存清理成功");
    }

    @OnClick(R.id.rlTeam)
    public void allTeams() {
        Intent intent = new Intent(mActivity, TeamsListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rlPlayer)
    public void allPlayers() {
        Intent intent = new Intent(mActivity, PlayerListActivity.class);
        startActivity(intent);
    }
}
