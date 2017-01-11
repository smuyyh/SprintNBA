package com.yuyh.sprintnba.ui;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.library.permission.Acp;
import com.yuyh.library.permission.AcpListener;
import com.yuyh.library.permission.AcpOptions;
import com.yuyh.library.utils.DeviceUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.library.view.viewpager.XViewPager;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.base.BaseAppCompatActivity;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.event.CalendarEvent;
import com.yuyh.sprintnba.ui.adapter.VPHomeAdapter;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.presenter.impl.HomePresenterImpl;
import com.yuyh.sprintnba.ui.view.HomeView;
import com.yuyh.sprintnba.utils.NavigationEntity;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.InjectView;
import cn.bmob.v3.update.BmobUpdateAgent;

public class HomeActivity extends BaseAppCompatActivity implements HomeView {

    @InjectView(R.id.home_container)
    XViewPager mViewPager;
    @InjectView(R.id.home_navigation_list)
    ListView mNavListView;
    @InjectView(R.id.home_drawer)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mActionBarDrawerToggle = null;
    private EasyLVAdapter<NavigationEntity> mNavListAdapter = null;

    private static long DOUBLE_CLICK_TIME = 0L;
    private static int REQUEST_DATE_CODE = 1;
    private int mCurrentMenuCheckedPos = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        Presenter presenter = new HomePresenterImpl(this, this);
        presenter.initialized();
        BmobUpdateAgent.setUpdateOnlyWifi(true); // Wifi下面才提示APP更新
        BmobUpdateAgent.update(this);
        // Android6.0 权限申请
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                        .setRationalMessage("以下权限需要您授权，否则将不能正常使用App。\n" +
                                "1、读取SD卡权限\n" +
                                "2、读取手机IMEI")
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        Constant.deviceId = DeviceUtils.getIMEI(HomeActivity.this);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        String str = "";
                        for (String permission : permissions) {
                            str += permission + "\n";
                        }
                        ToastUtils.showSingleLongToast(str + "权限拒绝，可能会引起APP异常退出");
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU && mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        } else if(mViewPager.getCurrentItem() != 0){
            mCurrentMenuCheckedPos = 0;
            mNavListAdapter.notifyDataSetChanged();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            mViewPager.setCurrentItem(mCurrentMenuCheckedPos, true);
            setTitle(((NavigationEntity) mNavListAdapter.getItem(mCurrentMenuCheckedPos)).getName());
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                    ToastUtils.showSingleToast("再按一次退出");
                    DOUBLE_CLICK_TIME = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments, List<NavigationEntity> navigationList) {

        if (null != fragments && !fragments.isEmpty()) {
            mViewPager.setEnableScroll(false);
            mViewPager.setOffscreenPageLimit(fragments.size());
            mViewPager.setAdapter(new VPHomeAdapter(getSupportFragmentManager(), fragments));
        }

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (null != mNavListAdapter) {
                    setTitle(((NavigationEntity) mNavListAdapter.getItem(mCurrentMenuCheckedPos)).getName());
                }
            }
        };
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mNavListAdapter = new EasyLVAdapter<NavigationEntity>(HomeActivity.this, navigationList, R.layout.item_list_navigation) {
            @Override
            public void convert(EasyLVHolder viewHolder, int position, NavigationEntity item) {
                viewHolder.setImageResource(R.id.list_item_navigation_icon, item.getIconResId())
                        .setText(R.id.list_item_navigation_name, item.getName());
            }
        };

        mNavListView.setAdapter(mNavListAdapter);
        mNavListAdapter.notifyDataSetChanged();

        mNavListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentMenuCheckedPos = position;
                mNavListAdapter.notifyDataSetChanged();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mViewPager.setCurrentItem(mCurrentMenuCheckedPos, false);
            }
        });
    }

    /**
     * 显示overflower菜单图标
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = this.getMenuInflater();
        switch (mCurrentMenuCheckedPos) {
            case 1:
                inflater.inflate(R.menu.menu_schedule, menu);
                break;
            default:
                //inflater.inflate(R.menu.menu_home, menu);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle != null && mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_calendar:
                CalendarActivity.start(this, REQUEST_DATE_CODE);
                break;
            case R.id.action_live:
                MatchVideoLiveListActivity.start(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DATE_CODE && resultCode == RESULT_OK) {
            String date = data.getStringExtra(CalendarActivity.CALENDAR_DATE);
            if (!TextUtils.isEmpty(date))
                EventBus.getDefault().post(new CalendarEvent(date));
        }
    }
}
