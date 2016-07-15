
package com.yuyh.sprintnba.ui.Interactor;

import com.yuyh.sprintnba.http.bean.player.Teams;
import com.yuyh.sprintnba.http.api.RequestCallback;

public interface TeamsListInteractor {

    void getAllTeams(RequestCallback<Teams> callback);
}
