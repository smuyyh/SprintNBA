package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class NBAScheduleFragment extends BaseLazyFragment {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_nba_schedule);
        mActivity.invalidateOptionsMenu();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }
}
