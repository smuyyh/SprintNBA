package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.bean.match.MatchStat;
import com.yuyh.cavaliers.ui.presenter.impl.MatchDataPresenter;
import com.yuyh.cavaliers.ui.view.MatchDataView;
import com.yuyh.library.utils.toast.ToastUtils;

import butterknife.ButterKnife;

/**
 * 比赛数据
 *
 * @author yuyh.
 * @date 16/6/5.
 */
public class MatchDataFragment extends BaseLazyFragment implements MatchDataView {

    private MatchDataPresenter presenter;
    private MatchStat.MatchStatInfo.MatchTeamInfo info;

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
}
