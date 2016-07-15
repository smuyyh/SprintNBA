package com.yuyh.sprintnba.utils;

import android.text.TextUtils;

import com.yuyh.library.utils.data.PrefsUtils;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public class SettingPrefUtils {

    public static String getUid() {
        return new PrefsUtils().get("uid", "");
    }

    public static void saveUid(String uid) {
        new PrefsUtils().put("uid", uid);
    }

    public static String getToken() {
        return new PrefsUtils().get("token", "");
    }

    public static void saveToken(String token) {
        new PrefsUtils().put("token", token);
    }

    public static String getCookies() {
        return new PrefsUtils().get("cookies", "");
    }

    public static void saveCookies(String cookies) {
        new PrefsUtils().put("cookies", cookies);
    }

    public static String getUsername() {
        return new PrefsUtils().get("username", "");
    }

    public static void saveUsername(String username) {
        new PrefsUtils().put("username", username);
    }

    public static String getPassword() {
        return new PrefsUtils().get("password", "");
    }

    public static void savePassword(String password) {
        new PrefsUtils().put("password", password);
    }

    public static String getNickname() {
        return new PrefsUtils().get("nickname", "");
    }

    public static void saveNickname(String nickname) {
        new PrefsUtils().put("nickname", nickname);
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getCookies()) && !TextUtils.isEmpty(getToken());
    }

    public static void logout() {
        saveCookies("");
        saveNickname("");
        saveToken("");
        saveUid("");
        saveUsername("");
    }
}
