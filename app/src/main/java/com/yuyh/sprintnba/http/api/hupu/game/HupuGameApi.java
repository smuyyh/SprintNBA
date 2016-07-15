package com.yuyh.sprintnba.http.api.hupu.game;

import com.yuyh.sprintnba.http.bean.cookie.UserData;
import com.yuyh.sprintnba.http.bean.forum.SearchListData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public interface HupuGameApi {
    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Call<UserData> login(@FieldMap Map<String, String> params, @Query("client") String client);

    @GET("search/list")
    Call<SearchListData> search(@QueryMap Map<String, String> params, @Query("client") String client);

}
