package com.yuyh.cavaliers.http.api.hupu.forum;

import com.yuyh.cavaliers.BuildConfig;
import com.yuyh.cavaliers.http.bean.forum.ForumInfoListData;
import com.yuyh.cavaliers.http.bean.forum.ForumsData;
import com.yuyh.cavaliers.http.util.HupuReqHelper;
import com.yuyh.cavaliers.http.util.JsonParser;
import com.yuyh.cavaliers.http.util.StringConverter;

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


}
