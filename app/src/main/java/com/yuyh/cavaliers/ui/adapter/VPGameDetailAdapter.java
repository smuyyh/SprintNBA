package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.ui.fragment.MatchLookForwardFragment;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.view.viewpager.indicator.FragmentListPageAdapter;
import com.yuyh.library.view.viewpager.indicator.IndicatorViewPager;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class VPGameDetailAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private LayoutInflater inflate;
    private String[] names;

    public VPGameDetailAdapter(Context context, String[] names, FragmentManager fragmentManager) {
        super(fragmentManager);
        inflate = LayoutInflater.from(context);
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.tab_game_detail, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(names[position % names.length]);
        int padding = DimenUtils.dpToPxInt(15);
        textView.setPadding(padding, 0, padding, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        Fragment fragment = new MatchLookForwardFragment();
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return FragmentListPageAdapter.POSITION_NONE;
    }
}
