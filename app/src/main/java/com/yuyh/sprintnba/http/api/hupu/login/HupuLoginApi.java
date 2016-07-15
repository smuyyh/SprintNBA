package com.yuyh.sprintnba.http.api.hupu.login;

import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface HupuLoginApi {
    @FormUrlEncoded
    @POST("/pc/login/member.action")
    void login(@Field("username") String username, @Field("password") String password, Callback<String> resp);
}
