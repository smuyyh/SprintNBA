package com.yuyh.cavaliers.ui.Interactor.impl;

import android.content.Context;

import com.yuyh.cavaliers.ui.Interactor.HomeInteractor;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.utils.NavigationEntity;
import com.yuyh.cavaliers.ui.fragment.ForumListFragment;
import com.yuyh.cavaliers.ui.fragment.NewsFragment;
import com.yuyh.cavaliers.ui.fragment.ScheduleFragment;
import com.yuyh.cavaliers.ui.fragment.StatsRankFragment;
import com.yuyh.cavaliers.ui.fragment.TeamSortFragment;
import com.yuyh.cavaliers.ui.fragment.OtherFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeInteractorImpl implements HomeInteractor {

    @Override
    public List<BaseLazyFragment> getPagerFragments() {
        List<BaseLazyFragment> fragments = new ArrayList<BaseLazyFragment>() {{
            add(new NewsFragment());
            add(new ScheduleFragment());
            add(new TeamSortFragment());
            add(new StatsRankFragment());
            add(new ForumListFragment());
            add(new OtherFragment());
        }};
        return fragments;
    }

    @Override
    public List<NavigationEntity> getNavigationList(Context context) {
        List<NavigationEntity> navigationEntities = new ArrayList<NavigationEntity>() {{
            add(new NavigationEntity(R.drawable.ic_news, "头条新闻"));
            add(new NavigationEntity(R.drawable.ic_video, "赛事直播"));
            add(new NavigationEntity(R.drawable.ic_format, "球队战绩"));
            add(new NavigationEntity(R.drawable.ic_format, "数据排行"));
            add(new NavigationEntity(R.drawable.ic_favorite, "虎扑专区"));
            add(new NavigationEntity(R.drawable.ic_other, "其他"));
        }};
        return navigationEntities;
    }
}
