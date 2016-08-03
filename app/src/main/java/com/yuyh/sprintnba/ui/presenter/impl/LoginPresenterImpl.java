package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;

import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.hupu.game.HupuGamesService;
import com.yuyh.sprintnba.http.bean.cookie.User;
import com.yuyh.sprintnba.http.bean.cookie.UserData;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.LoginView;
import com.yuyh.sprintnba.utils.SettingPrefUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public class LoginPresenterImpl implements Presenter {

    private Context context;
    private LoginView loginView;

    private User user = new User();

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
    }

    @Override
    public void initialized() {

    }

    public void login(final String username, final String password) {
        loginView.showLoading();
        HupuGamesService.login(username, password, new RequestCallback<UserData>() {
            @Override
            public void onSuccess(UserData userData) {
                if (userData != null) {
                    if (userData != null && userData.is_login == 1) { // 登录成功
                        UserData.LoginResult data = userData.result;
                        String cookie = "";
                        try {
                            cookie = URLDecoder.decode(Constant.Cookie, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        LogUtils.d("cookie:" + cookie);
                        String uid = cookie.split("\\|")[0];
                        LogUtils.d("uid:" + uid);
                        user.uid = uid;
                        user.nickName = data.nickname;
                        user.token = data.token;
                        user.cookie = cookie;
                        user.userName = data.username;

                        SettingPrefUtils.saveNickname(data.nickname);
                        SettingPrefUtils.saveUid(data.uid);
                        SettingPrefUtils.saveToken(data.token);
                        SettingPrefUtils.saveUsername(username);
                        SettingPrefUtils.savePassword(password);
                        loginView.loginSuccess();
                        loginView.hideLoading();
                    } else {
                        loginView.hideLoading();
                        if (userData.error != null)
                            ToastUtils.showSingleLongToast(userData.error.msg);
                        else
                            ToastUtils.showSingleLongToast("登录失败");
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                loginView.hideLoading();
                ToastUtils.showSingleLongToast("登录失败");
            }
        });
    }
}
