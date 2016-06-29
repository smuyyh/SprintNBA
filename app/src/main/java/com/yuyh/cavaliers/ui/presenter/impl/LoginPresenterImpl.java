package com.yuyh.cavaliers.ui.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.http.api.hupu.game.HupugameService;
import com.yuyh.cavaliers.http.bean.cookie.User;
import com.yuyh.cavaliers.http.bean.cookie.UserData;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.utils.UserStorage;
import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.LoginView;
import com.yuyh.cavaliers.utils.SettingPrefUtils;
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
    private UserStorage storage;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;

        storage = UserStorage.getInstance();
    }

    @Override
    public void initialized() {

    }

    public void login(String userName, String passWord) {
        loginView.showLoading();
        HupugameService.login(userName, passWord, new RequestCallback<UserData>() {
            @Override
            public void onSuccess(UserData userData) {
                if(userData!= null) {
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
                        user.setUid(uid);
                        user.setNickName(data.nickname);
                        user.setToken(data.token);
                        user.setCookie(cookie);
                        user.setUserName(data.username);

                        storage.setToken(data.token);
                        storage.setUser(user);

                        SettingPrefUtils.saveNickname(data.nickname);
                        SettingPrefUtils.saveUid(data.uid);
                        SettingPrefUtils.saveToken(data.token);
                        SettingPrefUtils.saveUsername(data.username);
                        loginView.loginSuccess();
                        loginView.hideLoading();
                    } else {
                        loginView.hideLoading();
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
