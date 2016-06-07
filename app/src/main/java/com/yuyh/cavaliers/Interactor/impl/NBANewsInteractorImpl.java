package com.yuyh.cavaliers.Interactor.impl;

import com.yuyh.cavaliers.Interactor.NBANewsInteractor;

public class NBANewsInteractorImpl implements NBANewsInteractor {

    @Override
    public String[] getTabs() {
        return new String[]{"今日头条", "新闻资讯"};
    }
}
