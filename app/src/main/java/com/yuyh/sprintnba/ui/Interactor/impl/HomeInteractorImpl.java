package com.yuyh.sprintnba.ui.Interactor.impl;

import android.content.Context;

import com.yuyh.sprintnba.ui.Interactor.HomeInteractor;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.utils.NavigationEntity;
import com.yuyh.sprintnba.ui.fragment.ForumListFragment;
import com.yuyh.sprintnba.ui.fragment.NewsFragment;
import com.yuyh.sprintnba.ui.fragment.ScheduleFragment;
import com.yuyh.sprintnba.ui.fragment.StatsRankFragment;
import com.yuyh.sprintnba.ui.fragment.TeamSortFragment;
import com.yuyh.sprintnba.ui.fragment.OtherFragment;

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
            add(new NavigationEntity(R.drawable.ic_news, "NBA头条"));
            add(new NavigationEntity(R.drawable.ic_video, "赛事直播"));
            add(new NavigationEntity(R.drawable.ic_format, "球队战绩"));
            add(new NavigationEntity(R.drawable.ic_format, "数据排行"));
            add(new NavigationEntity(R.drawable.ic_favorite, "虎扑专区"));
            add(new NavigationEntity(R.drawable.ic_other, "其他"));
        }};
        return navigationEntities;
    }
}
