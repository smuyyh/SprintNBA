
package com.yuyh.cavaliers.Interactor;

import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;

public interface AllTeamsInteractor {

    void getAllTeams(GetBeanCallback<Teams> callback);
}
