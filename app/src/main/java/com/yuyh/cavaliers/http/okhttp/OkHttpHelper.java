package com.yuyh.cavaliers.http.okhttp;

import com.yuyh.cavaliers.http.util.UserStorage;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author yuyh.
 * @date 16/6/26.
 */
public class OkHttpHelper {

    public static OkHttpClient getAppClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        CookieInterceptor mCookieInterceptor = new CookieInterceptor(new UserStorage());
        builder.addInterceptor(mCookieInterceptor);
        return builder.build();
    }
}
