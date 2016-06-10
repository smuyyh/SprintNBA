package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.ui.fragment.NBANewsBannerFragment;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.view.viewpager.indicator.FragmentListPageAdapter;
import com.yuyh.library.view.viewpager.indicator.IndicatorViewPager;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class NewsVPAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private LayoutInflater inflate;
    private String[] names;
    private Constant.NewsType newsType;

    public NewsVPAdapter(Context context, String[] names, FragmentManager fragmentManager, Constant.NewsType newsType) {
        super(fragmentManager);
        inflate = LayoutInflater.from(context);
        this.names = names;
        this.newsType = newsType;
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
        int padding = DimenUtils.dpToPxInt(10);
        textView.setPadding(padding, 0, padding, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        NBANewsBannerFragment fragment = new NBANewsBannerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NBANewsBannerFragment.INTENT_INT_INDEX, position);
        Constant.NewsType newsTypeBundle;
        switch (newsType) {
            case NEWS:
                if (position == 0)
                    newsTypeBundle = Constant.NewsType.BANNER;
                else
                    newsTypeBundle = Constant.NewsType.NEWS;
                break;
            case VIDEO:
            default:
                if (position == 0)
                    newsTypeBundle = Constant.NewsType.VIDEO;
                else if (position == 1)
                    newsTypeBundle = Constant.NewsType.DEPTH;
                else
                    newsTypeBundle = Constant.NewsType.HIGHLIGHT;
                break;
        }
        bundle.putSerializable(NBANewsBannerFragment.INTENT_INT_INDEX, newsTypeBundle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return FragmentListPageAdapter.POSITION_NONE;
    }
}
