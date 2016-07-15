package com.yuyh.sprintnba.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yuyh.sprintnba.ui.fragment.ThreadContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class VPThreadAdapter extends FragmentPagerAdapter {
    private List<String> urls = new ArrayList<>();

    public VPThreadAdapter(FragmentManager fm, List<String> urls) {
        super(fm);
        this.urls = urls;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return ThreadContentFragment.newInstance(urls.get(position));
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    public void bind(List<String> urls){
        this.urls.clear();
        this.urls.addAll(urls);
        notifyDataSetChanged();
    }
}
