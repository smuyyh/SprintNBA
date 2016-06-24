package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.bean.player.Players;

import java.util.List;

public interface PlayersView {

    void showAllPlayers(List<Players.Player> bean);
    void failure(String msg);

}
