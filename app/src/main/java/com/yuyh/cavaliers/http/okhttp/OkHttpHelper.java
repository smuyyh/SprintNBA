package com.yuyh.cavaliers.http.okhttp;

import com.yuyh.cavaliers.http.util.UserStorage;
import com.yuyh.library.utils.log.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author yuyh.
 * @date 16/6/26.
 */
public class OkHttpHelper {

    static class MyLog implements HttpLoggingInterceptor.Logger{
        @Override
        public void log(String message) {
            LogUtils.i("okhttplog:"+message);
        }
    }

    public static OkHttpClient getAppClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        CookieInterceptor mCookieInterceptor = new CookieInterceptor(new UserStorage());
        builder.addInterceptor(mCookieInterceptor);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new MyLog());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        return builder.build();
    }
}
