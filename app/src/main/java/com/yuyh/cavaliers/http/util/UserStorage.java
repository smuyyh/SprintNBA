package com.yuyh.cavaliers.http.util;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.yuyh.cavaliers.http.bean.cookie.User;
import com.yuyh.library.AppUtils;

public class UserStorage {

    private static UserStorage userStorage;

    private Context mContext;
    private String cookie;
    private String token;

    private User user;

    public UserStorage(Context mContext) {
        this.mContext = mContext;
    }

    public UserStorage(){

    }

    public static UserStorage getInstance() {
        if (userStorage == null)
            userStorage = new UserStorage(AppUtils.getAppContext());
        return userStorage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void login(User user) {
        this.user = user;
    }

    public void logout() {
        user = null;
        cookie = "";
        token = "";
        removeCookie();
    }

    private void removeCookie() {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    public boolean isLogin() {
        return user != null;
    }

    public String getToken() {
        if (!isLogin()) {
            return token;
        }
        return user.getToken();
    }

    public String getUid() {
        if (!isLogin()) {
            return "";
        }
        return user.getUid();
    }

    public String getCookie() {
        if (isLogin()) {
            return user.getCookie();
        }
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
