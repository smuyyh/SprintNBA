package com.yuyh.cavaliers.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yuyh.cavaliers.base.BaseFragment;

import java.util.List;

/**
 * @author 顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * @Title: ListViewFragment
 * @Package com.gxz.stickynavlayout.fragments
 * @Description:
 * @date 15/12/29
 * @time 上午11:50
 */
public class FragmentsViewPagerAdapter extends FragmentPagerAdapter {

    private List<? extends BaseFragment> fragments;

    public FragmentsViewPagerAdapter(FragmentManager fm,
                                     List<? extends BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments != null && position < fragments.size() ? "---" : "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        return super.instantiateItem(container, position);
    }
}
