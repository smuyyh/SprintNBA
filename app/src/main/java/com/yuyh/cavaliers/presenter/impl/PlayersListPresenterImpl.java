package com.yuyh.cavaliers.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yuyh.cavaliers.Interactor.PlayersListInteractor;
import com.yuyh.cavaliers.Interactor.impl.PlayersListListInteractorImpl;
import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.PlayersView;

/**
 * @author yuyh.
 * @date 16/6/7.
 */
public class PlayersListPresenterImpl implements Presenter {

    private Context mContext = null;
    private PlayersView mPlayersView = null;
    private PlayersListInteractor mPlayersListInteractor = null;

    public PlayersListPresenterImpl(Context context, @NonNull PlayersView PlayersView) {
        mContext = context;
        this.mPlayersView = PlayersView;
        mPlayersListInteractor = new PlayersListListInteractorImpl();
    }

    @Override
    public void initialized() {
        // 数据量大，解析慢。故放在子线程执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPlayersListInteractor.getAllPlayers(new GetBeanCallback<Players>() {
                    @Override
                    public void onSuccess(final Players Players) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPlayersView.showAllPlayers(Players.data);
                            }
                        });
                    }

                    @Override
                    public void onFailure(final String message) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPlayersView.failure(message);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
