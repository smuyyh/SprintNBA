package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.ui.fragment.NewsListFragment;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.view.viewpager.indicator.FragmentListPageAdapter;
import com.yuyh.library.view.viewpager.indicator.IndicatorViewPager;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class VPNewsAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private LayoutInflater inflate;
    private String[] names;

    public VPNewsAdapter(Context context, String[] names, FragmentManager fragmentManager) {
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
            convertView = inflate.inflate(R.layout.tab_nba_news, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(names[position % names.length]);
        int padding = DimenUtils.dpToPxInt(15);
        textView.setPadding(padding, 0, padding, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NewsListFragment.INTENT_INT_INDEX, position);
        Constant.NewsType newsTypeBundle;
        switch (position) {
            case 0:
                newsTypeBundle = Constant.NewsType.BANNER;
                break;
            case 1:
                newsTypeBundle = Constant.NewsType.NEWS;
                break;
            case 2:
                newsTypeBundle = Constant.NewsType.VIDEO;
                break;
            case 3:
                newsTypeBundle = Constant.NewsType.DEPTH;
                break;
            case 4:
            default:
                newsTypeBundle = Constant.NewsType.HIGHLIGHT;
                break;
        }
        bundle.putSerializable(NewsListFragment.INTENT_INT_INDEX, newsTypeBundle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return FragmentListPageAdapter.POSITION_NONE;
    }
}
