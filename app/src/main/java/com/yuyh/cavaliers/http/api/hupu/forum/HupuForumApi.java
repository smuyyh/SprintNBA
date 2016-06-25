package com.yuyh.cavaliers.http.api.hupu.forum;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * 虎扑论坛API
 *
 * @author yuyh.
 * @date 16/6/25.
 */
public interface HupuForumApi {

    @GET("forums/getForums")
    void getForums(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("forums/getUserForumsList")
    void getMyForums(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("forums/getForumsInfoList")
    void getThreadsList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @POST("forums/attentionForumAdd")
    @FormUrlEncoded
    void addAttention(@Query("sign") String sign, @FieldMap Map<String, String> params);

    @POST("forums/attentionForumRemove")
    @FormUrlEncoded
    void delAttention(@Query("sign") String sign, @FieldMap Map<String, String> params);

    @GET("forums/getForumsAttendStatus")
    void getAttentionStatus(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("threads/getThreadsSchemaInfo")
    void getThreadInfo(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @POST("threads/threadPublish")
    @FormUrlEncoded
    void addThread(@FieldMap Map<String, String> params);

    @POST("threads/threadReply")
    @FormUrlEncoded
    void addReplyByApp(@FieldMap Map<String, String> params);

    @POST("threads/threadCollectAdd")
    @FormUrlEncoded
    void addCollect(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @POST("threads/threadCollectRemove")
    @FormUrlEncoded
    void delCollect(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @POST("threads/threadReport")
    @FormUrlEncoded
    void submitReports(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @GET("recommend/getThreadsList")
    void getRecommendThreadList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("user/getUserMessageList")
    void getMessageList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @POST("user/delUserMessage")
    @FormUrlEncoded
    void delMessage(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @POST("img/Imgup")
    @Multipart
    void upload(@Part("file") MultipartBody.Part file, @PartMap Map<String, RequestBody> params);

    @GET("permission/check")
    void checkPermission(@Query("sign") String sign, @QueryMap Map<String, String> params);
}
