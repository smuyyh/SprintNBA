package com.yuyh.cavaliers.http.api.hupu.login;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface HupuLoginApi {
    @FormUrlEncoded
    @POST("/pc/login/member.action")
    void login(@Field("username") String username, @Field("password") String password, Callback<String> resp);
}
