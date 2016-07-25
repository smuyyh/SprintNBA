package com.yuyh.sprintnba.http.api.hupu.forum;

import android.text.TextUtils;

import com.yuyh.sprintnba.BuildConfig;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.bean.base.BaseData;
import com.yuyh.sprintnba.http.bean.forum.AddReplyData;
import com.yuyh.sprintnba.http.bean.forum.AttendStatusData;
import com.yuyh.sprintnba.http.bean.forum.ForumsData;
import com.yuyh.sprintnba.http.bean.forum.PermissionData;
import com.yuyh.sprintnba.http.bean.forum.ThreadListData;
import com.yuyh.sprintnba.http.bean.forum.ThreadsSchemaInfoData;
import com.yuyh.sprintnba.http.okhttp.OkHttpHelper;
import com.yuyh.sprintnba.http.utils.RequestHelper;
import com.yuyh.sprintnba.utils.SettingPrefUtils;
import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.data.ACache;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class HupuForumService {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.HUPU_FORUM_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpHelper.getHupuClient())
            .build();

    public static HupuForumApi apiStr = retrofit.create(HupuForumApi.class);

    /**
     * 获取论坛板块列表
     *
     * @param cbk
     */
    public static void getAllForums(final RequestCallback<ForumsData> cbk) {
        final String key = "getAllForums";
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null) {
            ForumsData match = (ForumsData) obj;
            cbk.onSuccess(match);
            return;
        }
        Map<String, String> params = RequestHelper.getRequsetMap();
        String sign = RequestHelper.getRequestSign(params);

        Call<ForumsData> call = apiStr.getForums(sign, params);
        call.enqueue(new retrofit2.Callback<ForumsData>() {
            @Override
            public void onResponse(Call<ForumsData> call, retrofit2.Response<ForumsData> response) {
                ForumsData data = response.body();
                cbk.onSuccess(data);
                cache.put(key, data);
            }

            @Override
            public void onFailure(Call<ForumsData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
                cache.remove(key);
            }
        });
    }

    /**
     * 获取论坛帖子列表
     *
     * @param fid      论坛id，通过getForums接口获取
     * @param lastTid  最后一篇帖子的id
     * @param limit    分页大小
     * @param lastTamp 时间戳
     * @param type     加载类型  1 按发帖时间排序  2 按回帖时间排序
     */
    public static void getForumPosts(String fid, String lastTid, int limit, String lastTamp, String type, final RequestCallback<ThreadListData> cbk) {
        Map<String, String> params = RequestHelper.getRequsetMap();
        params.put("fid", fid);
        params.put("lastTid", lastTid);
        params.put("limit", String.valueOf(limit));
        params.put("isHome", "1");
        params.put("stamp", lastTamp);
        params.put("password", "0");
        params.put("special", "0");
        params.put("type", type);
        String sign = RequestHelper.getRequestSign(params);

        Call<ThreadListData> call = apiStr.getForumInfosList(sign, params);
        call.enqueue(new retrofit2.Callback<ThreadListData>() {
            @Override
            public void onResponse(Call<ThreadListData> call, retrofit2.Response<ThreadListData> response) {
                ThreadListData data = response.body();
                cbk.onSuccess(data);
            }

            @Override
            public void onFailure(Call<ThreadListData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }

    /**
     * 发新帖 (params 须带token信息)
     *
     * @param title   标题
     * @param content 内容
     * @param fid     论坛id
     */
    public static void addThread(String title, String content, String fid, final RequestCallback<BaseData> cbk) {
        Map<String, String> params = RequestHelper.getRequsetMap();
        params.put("title", title);
        params.put("content", content);
        params.put("fid", fid);
        String sign = RequestHelper.getRequestSign(params);
        params.put("sign", sign);

        Call<BaseData> call = apiStr.addThread(params);
        call.enqueue(new retrofit2.Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call, retrofit2.Response<BaseData> response) {
                cbk.onSuccess(response != null ? response.body() : null);
            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }

    /**
     * 评论或者回复
     *
     * @param tid     帖子id
     * @param fid     论坛id
     * @param pid     回复id（评论时pid为空，回复某条回复pid为回复的id）
     * @param content 内容
     */
    public static void addReplyByApp(String tid, String fid, String pid, String content, final RequestCallback<AddReplyData> cbk) {
        Map<String, String> params = RequestHelper.getRequsetMap();
        params.put("tid", tid);
        params.put("content", content);
        params.put("fid", fid);
        if (!TextUtils.isEmpty(pid)) {
            params.put("quotepid", pid);
            params.put("boardpw", "");
        }
        String sign = RequestHelper.getRequestSign(params);
        params.put("sign", sign);

        Call<AddReplyData> call = apiStr.addReplyByApp(params);
        call.enqueue(new Callback<AddReplyData>() {
            @Override
            public void onResponse(Call<AddReplyData> call, Response<AddReplyData> response) {
                cbk.onSuccess(response != null ? response.body() : null);
            }

            @Override
            public void onFailure(Call<AddReplyData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }

    /**
     * 获取帖子详情
     *
     * @param tid  帖子id
     * @param fid  论坛id
     * @param page 页数
     * @param pid  回复id
     */
    public static void getThreadInfo(String tid, String fid, int page, String pid, final RequestCallback<ThreadsSchemaInfoData> cbk) {
        Map<String, String> params = RequestHelper.getRequsetMap();
        if (!TextUtils.isEmpty(tid)) {
            params.put("tid", tid);
        }
        if (!TextUtils.isEmpty(fid)) {
            params.put("fid", fid);
        }
        params.put("page", page + "");
        if (!TextUtils.isEmpty(pid)) {
            params.put("pid", pid);
        }
        params.put("nopic", "0");
        String sign = RequestHelper.getRequestSign(params);

        Call<ThreadsSchemaInfoData> call = apiStr.getThreadInfo(sign, params);
        call.enqueue(new retrofit2.Callback<ThreadsSchemaInfoData>() {
            @Override
            public void onResponse(Call<ThreadsSchemaInfoData> call, retrofit2.Response<ThreadsSchemaInfoData> response) {
                ThreadsSchemaInfoData data = response.body();
                cbk.onSuccess(data);
            }

            @Override
            public void onFailure(Call<ThreadsSchemaInfoData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }

    /**
     * 获取论坛关注状态
     *
     * @param fid 论坛id
     */
    public static void getAttentionStatus(String fid, final RequestCallback<AttendStatusData> cbk) {
        Map<String, String> params = RequestHelper.getRequsetMap();
        params.put("fid", fid);
        params.put("uid", SettingPrefUtils.getUid());
        String sign = RequestHelper.getRequestSign(params);

        Call<AttendStatusData> call = apiStr.getAttentionStatus(sign, params);
        call.enqueue(new retrofit2.Callback<AttendStatusData>() {
            @Override
            public void onResponse(Call<AttendStatusData> call, retrofit2.Response<AttendStatusData> response) {
                AttendStatusData data = response.body();
                cbk.onSuccess(data);
            }

            @Override
            public void onFailure(Call<AttendStatusData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }

    /**
     * 检查权限
     *
     * @param fid    论坛id
     * @param tid    帖子id
     * @param action threadPublish  threadReply
     */
    public static void checkPermission(String fid, String tid, String action, final RequestCallback<PermissionData> cbk) {
        Map<String, String> params = RequestHelper.getRequsetMap();
        if (!TextUtils.isEmpty(fid)) {
            params.put("fid", fid);
        }
        if (!TextUtils.isEmpty(tid)) {
            params.put("tid", tid);
        }
        if (!TextUtils.isEmpty(action)) {
            params.put("action", action);
        }
        String sign = RequestHelper.getRequestSign(params);

        Call<PermissionData> call = apiStr.checkPermission(sign, params);
        call.enqueue(new Callback<PermissionData>() {
            @Override
            public void onResponse(Call<PermissionData> call, Response<PermissionData> response) {
                cbk.onSuccess(response != null ? response.body() : null);
            }

            @Override
            public void onFailure(Call<PermissionData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }

    /**
     * (1, "广告或垃圾内容");
     * (2, "色情暴露内容");
     * (3, "政治敏感话题");
     * (4, "人身攻击等恶意行为");
     */
    public static void submitReports(String tid, String pid, String type, String content, final RequestCallback<BaseData> cbk) {
        Map<String, String> params = RequestHelper.getRequsetMap();
        if (!TextUtils.isEmpty(tid)) {
            params.put("tid", tid);
        }
        if (!TextUtils.isEmpty(pid)) {
            params.put("pid", pid);
        }
        params.put("type", type);
        params.put("content", content);
        String sign = RequestHelper.getRequestSign(params);

        Call<BaseData> call = apiStr.submitReports(sign, params);
        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                cbk.onSuccess(response != null ? response.body() : null);
            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }

}
