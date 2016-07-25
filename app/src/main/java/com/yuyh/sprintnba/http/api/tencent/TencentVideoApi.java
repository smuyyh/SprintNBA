package com.yuyh.sprintnba.http.api.tencent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public interface TencentVideoApi {

    @GET("/getinfo?otype=xml&platform=1&ran=0%2E9652906153351068")
    Call<String> getVideoRealUrl(@Query("vid") String vid);
}
