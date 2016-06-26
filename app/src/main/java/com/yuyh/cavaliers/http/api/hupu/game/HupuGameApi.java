package com.yuyh.cavaliers.http.api.hupu.game;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public interface HupuGameApi {
    @FormUrlEncoded
    @POST("/user/loginUsernameEmail")
    void login(@FieldMap Map<String, String> params, @Query("client") String client, Callback<String> cbk);
}
