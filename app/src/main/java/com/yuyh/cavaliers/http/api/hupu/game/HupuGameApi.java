package com.yuyh.cavaliers.http.api.hupu.game;

import com.yuyh.cavaliers.http.bean.cookie.UserData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public interface HupuGameApi {
    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Call<UserData> login(@FieldMap Map<String, String> params, @Query("client") String client);
}
