
package com.yuyh.cavaliers.Interactor;

import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;

public interface TeamsListInteractor {

    void getAllTeams(GetBeanCallback<Teams> callback);
}
