
package com.yuyh.sprintnba.ui.Interactor;

import com.yuyh.sprintnba.http.bean.player.Players;
import com.yuyh.sprintnba.http.api.RequestCallback;

public interface PlayersListInteractor {

    void getAllPlayers(RequestCallback<Players> callback);
}
