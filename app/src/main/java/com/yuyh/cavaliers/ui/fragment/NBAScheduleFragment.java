package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.event.CalendarEvent;
import com.yuyh.library.utils.log.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class NBAScheduleFragment extends BaseLazyFragment {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_nba_schedule);

        EventBus.getDefault().register(this);

        mActivity.invalidateOptionsMenu();
    }

    @Subscribe
    public void onEventMainThread(CalendarEvent msg){
        LogUtils.i(msg.getDate());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
