package com.yuyh.cavaliers.ui.Interactor.impl;

import com.yuyh.cavaliers.ui.Interactor.NBANewsInteractor;

public class NBANewsInteractorImpl implements NBANewsInteractor {

    @Override
    public String[] getTabs() {
        return new String[]{"今日头条", "新闻资讯", "视频集锦", "最佳进球", "赛场花絮"};
    }
}
