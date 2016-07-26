package com.yuyh.sprintnba.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.yuyh.sprintnba.ui.fragment.ImagePreFragment;

import java.util.HashMap;
import java.util.List;

public class VPImagePreViewAdapter extends FragmentStatePagerAdapter {

    private HashMap<Integer, ImagePreFragment> fragmentMap = new HashMap<>();
    private List<String> extraPics;

    public VPImagePreViewAdapter(FragmentManager fm, List<String> extraPics) {
        super(fm);
        this.extraPics = extraPics;
    }

    @Override
    public Fragment getItem(int position) {
        ImagePreFragment fragment = fragmentMap.get(position);
        if (fragment == null) {
            fragment = ImagePreFragment.newInstance(extraPics.get(position));
            fragmentMap.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return extraPics.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (object instanceof Fragment) {
            fragmentMap.put(position, (ImagePreFragment) object);
        }
    }
}