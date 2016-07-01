package com.yuyh.cavaliers.http.api.tencent;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public interface TencentVideoApi {

    @GET("/getinfo?otype=xml&platform=1&ran=0%2E9652906153351068")
    void getVideoRealUrl(@Query("vid") String vid, Callback<String> resp);
}
