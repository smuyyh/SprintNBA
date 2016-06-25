package com.yuyh.cavaliers.http.api.hupu.forum;

import android.text.TextUtils;

import com.yuyh.cavaliers.BuildConfig;
import com.yuyh.cavaliers.http.bean.forum.ForumInfoListData;
import com.yuyh.cavaliers.http.bean.forum.ForumsData;
import com.yuyh.cavaliers.http.bean.forum.ThreadsSchemaInfoData;
import com.yuyh.cavaliers.http.util.GetBeanCallback;
import com.yuyh.cavaliers.http.util.HupuReqHelper;
import com.yuyh.cavaliers.http.util.JsonParser;
import com.yuyh.cavaliers.http.util.StringConverter;
import com.yuyh.library.utils.log.LogUtils;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class HupuForumService {
    public static RestAdapter rest = new RestAdapter.Builder().setEndpoint(BuildConfig.HUPU_FORUM_SERVER).build();
    public static RestAdapter restStr = new RestAdapter.Builder().setEndpoint(BuildConfig.HUPU_FORUM_SERVER).setConverter(new StringConverter()).build();

    public static HupuForumApi api = rest.create(HupuForumApi.class);
    public static HupuForumApi apiStr = restStr.create(HupuForumApi.class);

    /**
     * 获取论坛板块列表
     */
    public static void getAllForums() {
        Map<String, String> params = HupuReqHelper.getRequsetMap();
        String sign = HupuReqHelper.getRequestSign(params);
        apiStr.getForums(sign, params, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                ForumsData data = JsonParser.parseWithGson(ForumsData.class, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
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
    public static void getForumPosts(String fid, String lastTid, int limit, String lastTamp, String type) {
        Map<String, String> params = HupuReqHelper.getRequsetMap();
        params.put("fid", fid);
        params.put("lastTid", lastTid);
        params.put("limit", String.valueOf(limit));
        params.put("isHome", "1");
        params.put("stamp", lastTamp);
        params.put("password", "0");
        params.put("special", "0");
        params.put("type", type);
        String sign = HupuReqHelper.getRequestSign(params);
        apiStr.getForumInfosList(sign, params, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                ForumInfoListData data = JsonParser.parseWithGson(ForumInfoListData.class, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {

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
    public static void addThread(String title, String content, String fid) {
        Map<String, String> params = HupuReqHelper.getRequsetMap();
        params.put("title", title);
        params.put("content", content);
        params.put("fid", fid);
        String sign = HupuReqHelper.getRequestSign(params);
        params.put("sign", sign);
        apiStr.addThread(params, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                LogUtils.i("----" + s);
            }

            @Override
            public void failure(RetrofitError error) {
                LogUtils.e("-----" + error.getMessage());
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
    public static void getThreadInfo(String tid, String fid, int page, String pid, final GetBeanCallback<ThreadsSchemaInfoData> cbk) {
        Map<String, String> params = HupuReqHelper.getRequsetMap();
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
        String sign = HupuReqHelper.getRequestSign(params);
        apiStr.getThreadInfo(sign, params, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                ThreadsSchemaInfoData data = JsonParser.parseWithGson(ThreadsSchemaInfoData.class, jsonStr);
                cbk.onSuccess(data);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }
}
