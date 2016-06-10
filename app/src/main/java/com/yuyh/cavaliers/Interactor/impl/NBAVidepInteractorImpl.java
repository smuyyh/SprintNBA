package com.yuyh.cavaliers.Interactor.impl;

import com.yuyh.cavaliers.Interactor.NBANewsInteractor;

public class NBAVidepInteractorImpl implements NBANewsInteractor {

    @Override
    public String[] getTabs() {
        return new String[]{"视频集锦", "最佳进球", "赛场花絮"};
    }
}
