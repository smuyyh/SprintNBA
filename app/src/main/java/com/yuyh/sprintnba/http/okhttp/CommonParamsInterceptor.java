package com.yuyh.sprintnba.http.okhttp;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yuyh.
 * @date 2016/8/2.
 */
public class CommonParamsInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();

        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("appver", "1.0.2.2")
                .addQueryParameter("appvid", "1.0.2.2")
                .addQueryParameter("network", "wifi");


        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
