package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.view.viewpager.indicator.FragmentListPageAdapter;
import com.yuyh.library.view.viewpager.indicator.IndicatorViewPager;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.match.MatchBaseInfo;
import com.yuyh.sprintnba.ui.fragment.MatchDataFragment;
import com.yuyh.sprintnba.ui.fragment.MatchLiveFragment;
import com.yuyh.sprintnba.ui.fragment.MatchLookForwardFragment;
import com.yuyh.sprintnba.ui.fragment.MatchPlayerDataFragment;
import com.yuyh.sprintnba.ui.fragment.MatchVideoFragment;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class VPGameDetailAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private LayoutInflater inflate;
    private String[] names;
    private String mid;
    private boolean isStart;

    private MatchBaseInfo.BaseInfo info;

    public VPGameDetailAdapter(Context context, String[] names, FragmentManager fragmentManager,
                               String mid, boolean isStart, MatchBaseInfo.BaseInfo info) {
        super(fragmentManager);
        inflate = LayoutInflater.from(context);
        this.names = names;
        this.mid = mid;
        this.isStart = isStart;
        this.info = info;
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
        int padding = DimenUtils.dpToPxInt(14);
        textView.setPadding(padding, 0, padding, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        Fragment fragment = null;
        if (isStart) {
            switch (position) {
                case 0:
                    fragment = MatchDataFragment.newInstance(mid, info);
                    break;
                case 1:
                    fragment = MatchPlayerDataFragment.newInstance(mid);
                    break;
                case 2:
                    fragment = MatchLiveFragment.newInstance(mid);
                    break;
                case 3:
                default:
                    fragment = MatchVideoFragment.newInstance(mid);
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    fragment = MatchLookForwardFragment.newInstance(mid);
                    break;
                case 1:
                default:
                    fragment = MatchLiveFragment.newInstance(mid);
                    break;
            }
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return FragmentListPageAdapter.POSITION_NONE;
    }
}
