package com.yuyh.cavaliers.http.okhttp;

import android.text.TextUtils;

import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.http.util.UserStorage;
import com.yuyh.cavaliers.utils.SettingPrefUtils;
import com.yuyh.library.utils.log.LogUtils;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {

    private UserStorage mUserStorage;

    public CookieInterceptor(UserStorage mUserStorage) {
        this.mUserStorage = mUserStorage;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        if (!TextUtils.isEmpty(mUserStorage.getCookie()) && !original.url()
                .toString()
                .contains("loginUsernameEmail")) {
            Request request = original.newBuilder()
                    .addHeader("Cookie", "u=" + URLEncoder.encode(mUserStorage.getCookie()) + ";")
                    .build();
            LogUtils.d("----cookie:" + mUserStorage.getCookie());
            return chain.proceed(request);
        } else {
            for (String header : chain.proceed(original).headers("Set-Cookie")) {
                if (header.startsWith("u=")) {
                    String cookie = header.split(";")[0].substring(2);
                    LogUtils.d("----cookie:" + cookie);
                    if (!TextUtils.isEmpty(cookie)) {
                        Constant.Cookie = cookie;
                        SettingPrefUtils.saveCookies(cookie);
                    }
                }
            }
        }
        return chain.proceed(original);
    }
}
