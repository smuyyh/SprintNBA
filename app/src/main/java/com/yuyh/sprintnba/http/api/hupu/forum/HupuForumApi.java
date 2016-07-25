package com.yuyh.sprintnba.http.api.hupu.forum;

import com.yuyh.sprintnba.http.bean.base.BaseData;
import com.yuyh.sprintnba.http.bean.forum.AddReplyData;
import com.yuyh.sprintnba.http.bean.forum.AttendStatusData;
import com.yuyh.sprintnba.http.bean.forum.ForumsData;
import com.yuyh.sprintnba.http.bean.forum.PermissionData;
import com.yuyh.sprintnba.http.bean.forum.ThreadListData;
import com.yuyh.sprintnba.http.bean.forum.ThreadsSchemaInfoData;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 虎扑论坛API
 *
 * @author yuyh.
 * @date 16/6/25.
 */
public interface HupuForumApi {

    @GET("forums/getForums")
    Call<ForumsData> getForums(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("forums/getUserForumsList")
    void getMyForums(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("forums/getForumsInfoList")
    Call<ThreadListData> getForumInfosList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @POST("forums/attentionForumAdd")
    @FormUrlEncoded
    void addAttention(@Query("sign") String sign, @FieldMap Map<String, String> params);

    @POST("forums/attentionForumRemove")
    @FormUrlEncoded
    void delAttention(@Query("sign") String sign, @FieldMap Map<String, String> params);

    @GET("forums/getForumsAttendStatus")
    Call<AttendStatusData> getAttentionStatus(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("threads/getThreadsSchemaInfo")
    Call<ThreadsSchemaInfoData> getThreadInfo(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @POST("threads/threadPublish")
    @FormUrlEncoded
    Call<BaseData> addThread(@FieldMap Map<String, String> params);

    @POST("threads/threadReply")
    @FormUrlEncoded
    Call<AddReplyData> addReplyByApp(@FieldMap Map<String, String> params);

    @POST("threads/threadCollectAdd")
    @FormUrlEncoded
    void addCollect(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @POST("threads/threadCollectRemove")
    @FormUrlEncoded
    void delCollect(@Field("sign") String sign, @FieldMap Map<String, String> params);

    @POST("threads/threadReport")
    @FormUrlEncoded
    Call<BaseData> submitReports(@Field("sign") String sign, @FieldMap Map<String, String> params);

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
    Call<PermissionData> checkPermission(@Query("sign") String sign, @QueryMap Map<String, String> params);
}
