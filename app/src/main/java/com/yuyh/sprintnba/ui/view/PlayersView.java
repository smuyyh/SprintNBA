package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.player.Players;

import java.util.List;

public interface PlayersView {

    void showAllPlayers(List<Players.Player> bean);
    void failure(String msg);

}
