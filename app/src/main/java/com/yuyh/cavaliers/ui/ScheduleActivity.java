package com.yuyh.cavaliers.ui;

import android.view.Menu;
import android.view.MenuItem;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;

/**
 * 赛程
 *
 * @author yuyh.
 * @date 16/6/11.
 */
public class ScheduleActivity extends BaseSwipeBackCompatActivity {
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_nba_schedule;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about_us:

                break;
            case R.id.action_setting:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
