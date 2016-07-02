package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;

import butterknife.ButterKnife;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class MatchLookForwardFragment extends BaseLazyFragment {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_game_look_forward);
        ButterKnife.inject(this, getContentView());
        initData();
    }

    private void initData() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }
}
