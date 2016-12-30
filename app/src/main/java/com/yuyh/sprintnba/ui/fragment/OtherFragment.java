package com.yuyh.sprintnba.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.ui.AboutActivity;
import com.yuyh.sprintnba.ui.LoginActivity;
import com.yuyh.sprintnba.ui.PlayerListActivity;
import com.yuyh.sprintnba.ui.PostActivity;
import com.yuyh.sprintnba.ui.TeamsListActivity;
import com.yuyh.sprintnba.utils.CacheUtils;
import com.yuyh.sprintnba.utils.SettingPrefUtils;
import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.data.ACache;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.library.view.image.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class OtherFragment extends BaseLazyFragment {

    private static final int REQ_LOGIN = 1;

    @InjectView(R.id.rlLogin)
    RelativeLayout rlLogin;
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
    @InjectView(R.id.tvCacheSize)
    TextView tvCacheSize;

    @InjectView(R.id.tvUserName)
    TextView tvUserName;
    @InjectView(R.id.ivUserHead)
    CircleImageView ivHead;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_other);
        initData();
    }

    private void initData() {
        String nickname = SettingPrefUtils.getNickname();
        if (!TextUtils.isEmpty(nickname)) {
            tvUserName.setText(nickname);
        }
        tvCacheSize.setText(CacheUtils.getCacheSize(mActivity));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mActivity != null) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @OnClick(R.id.rlLogin)
    public void login() {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        startActivityForResult(intent, REQ_LOGIN);
    }

    @OnClick(R.id.rlClearCache)
    public void clearCache() {
        // PrefsUtils prefs = new PrefsUtils();
        // prefs.clearAll();
        ACache cache = ACache.get(AppUtils.getAppContext());
        cache.clear();
        CacheUtils.cleanApplicationCache(mActivity);
        ToastUtils.showSingleLongToast("缓存清理成功");
        tvCacheSize.setText(CacheUtils.getCacheSize(mActivity));
        ACache.get(AppUtils.getAppContext());
    }

    @OnClick(R.id.rlTeam)
    public void allTeams() {
        TeamsListActivity.start(mActivity);
    }

    @OnClick(R.id.rlPlayer)
    public void allPlayers() {
        PlayerListActivity.start(mActivity);
    }

    @OnClick(R.id.rlTeamSchedule)
    public void teamSchedule() {
        TeamsListActivity.start(mActivity);
    }

    @OnClick(R.id.rlNBACal)
    public void nbaCal() {
        BaseWebActivity.start(mActivity, "http://m.china.nba.com/importantdatetoapp/wap.htm", "NBA日历", false, true);
    }

    @OnClick(R.id.rlFeedback)
    public void feedback() {
        PostActivity.start(mActivity, Constant.TYPE_FEEDBACK);
    }

    @OnClick(R.id.rlAbout)
    public void about() {
        AboutActivity.start(mActivity);
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        tvCacheSize.setText(CacheUtils.getCacheSize(mActivity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQ_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    tvUserName.setText(SettingPrefUtils.getNickname());
                }
                break;
            default:
                break;
        }
    }
}
