package com.yuyh.sprintnba.http.okhttp;

import android.text.TextUtils;

import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.utils.SettingPrefUtils;
import com.yuyh.library.utils.log.LogUtils;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrofit2 Cookie拦截器。用于保存和设置Cookies
 */
public class CookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        if (!TextUtils.isEmpty(SettingPrefUtils.getCookies())
                && !original.url().toString().contains("loginUsernameEmail")) {
            Request request = original.newBuilder()
                    .addHeader("Cookie", "u=" + SettingPrefUtils.getCookies() + ";") // 不能转UTF-8
                    .build();
            LogUtils.d("okhttplog: set header cookie:" + SettingPrefUtils.getCookies());
            LogUtils.d("okhttplog: set header cookie:" + URLEncoder.encode(SettingPrefUtils.getCookies()));
            return chain.proceed(request);
        } else {
            for (String header : chain.proceed(original).headers("Set-Cookie")) {
                if (header.startsWith("u=")) {
                    String cookie = header.split(";")[0].substring(2);
                    LogUtils.d("okhttplog: add cookie:" + cookie);
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
