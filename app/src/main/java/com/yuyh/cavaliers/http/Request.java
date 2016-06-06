package com.yuyh.cavaliers.http;

import com.yuyh.cavaliers.BuildConfig;
import com.yuyh.cavaliers.http.bean.match.MatchCalendar;
import com.yuyh.cavaliers.http.bean.match.Matchs;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.bean.news.NewsIndex;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.http.bean.player.StatsRank;
import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.http.retrofit.StringConverter;
import com.yuyh.cavaliers.http.util.JsonParser;
import com.yuyh.library.utils.data.PrefsUtils;
import com.yuyh.library.utils.log.LogUtils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public class Request {

    public static RestAdapter rest = new RestAdapter.Builder().setEndpoint(BuildConfig.URL_SERVER).build();
    public static RestAdapter restStr = new RestAdapter.Builder().setEndpoint(BuildConfig.URL_SERVER).setConverter(new StringConverter()).build();

    public static CavsApi api = rest.create(CavsApi.class);
    public static CavsApi apiStr = restStr.create(CavsApi.class);


    /**
     * 根据球队Id及年份月份获取赛程
     *
     * @param teamId    球队ID，默认-1为查询所有
     * @param year      年份
     * @param month     月份
     * @param isRefresh 是否重新请求数据
     * @param cbk
     */
    public static void getMatchCalendar(int teamId, int year, int month, boolean isRefresh, final GetBeanCallback<MatchCalendar> cbk) {
        final String key = "getMatchCalendar" + teamId + year + month;
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                MatchCalendar match = JsonParser.parseMatchCalendar(jsonStr);
                cbk.onSuccess(match);
                return;
            }
        }
        apiStr.getMatchCalendar(teamId, year, month, new Callback<String>() {

            @Override
            public void success(String jsonStr, Response response) {
                MatchCalendar match = JsonParser.parseMatchCalendar(jsonStr);
                cbk.onSuccess(match);
                prefsUtils.put(key, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }

    /**
     * 根据日期查询赛程
     *
     * @param date      日期。格式：YYYY-MM-DD
     * @param isRefresh 是否重新请求数据
     * @param cbk
     */
    public static void getMatchsByDate(String date, boolean isRefresh, final GetBeanCallback<Matchs> cbk) {
        final String key = "getMatchsByDate" + date;
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                Matchs matchs = JsonParser.parseWithGson(Matchs.class, jsonStr);
                cbk.onSuccess(matchs);
                return;
            }
        }
        api.getMatchsByData(date, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                Matchs matchs = JsonParser.parseWithGson(Matchs.class, jsonStr);
                cbk.onSuccess(matchs);
                prefsUtils.put(key, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }

    /**
     * 获取所有新闻索引
     *
     * @param isRefresh 是否重新请求数据
     * @param cbk
     */
    public static void getNewsIndex(Constant.NewsType newsType, boolean isRefresh, final GetBeanCallback<NewsIndex> cbk) {
        final String key = "getNewsIndex" + newsType.getType();
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                NewsIndex index = JsonParser.parseWithGson(NewsIndex.class, jsonStr);
                cbk.onSuccess(index);
                return;
            }
        }
        apiStr.getNewsIndex(newsType.getType(), new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                NewsIndex index = JsonParser.parseWithGson(NewsIndex.class, jsonStr);
                cbk.onSuccess(index);
                prefsUtils.put(key, jsonStr);
                LogUtils.d(jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }

    /**
     * 根据索引获取新闻列表
     *
     * @param articleIds 索引值。多个索引以“,”分隔
     * @param isRefresh  是否重新请求数据
     * @param cbk
     */
    public static void getNewsItem(Constant.NewsType newsType, String articleIds, boolean isRefresh, final GetBeanCallback<NewsItem> cbk) {
        final String key = "getNewsItem" + articleIds;
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                NewsItem newsItem = JsonParser.parseNewsItem(jsonStr);
                cbk.onSuccess(newsItem);
                LogUtils.d("sp:" + jsonStr);
                return;
            }
        }
        // 由于新闻列表json采用动态key，故无法直接通过Gson解析成对象，这里做自定义解析
        apiStr.getNewsItem(newsType.getType(), articleIds, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                NewsItem newsItem = JsonParser.parseNewsItem(jsonStr);
                cbk.onSuccess(newsItem);
                prefsUtils.put(key, jsonStr);
                LogUtils.d("resp:" + jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }

    /**
     * 获取新闻的详细内容
     *
     * @param newsType  文章类型
     * @param articleId 文章id
     * @param isRefresh 是否重新请求数据
     * @param cbk
     */
    public static void getNewsDetail(Constant.NewsType newsType, String articleId, boolean isRefresh, final GetBeanCallback<NewsDetail> cbk) {
        final String key = "getNewsDetail" + articleId;
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                NewsDetail detail = JsonParser.parseNewsDetail(jsonStr);
                cbk.onSuccess(detail);
                return;
            }
        }
        apiStr.getNewsDetail(newsType.getType(), articleId, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                NewsDetail detail = JsonParser.parseNewsDetail(jsonStr);
                cbk.onSuccess(detail);
                prefsUtils.put(key, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }

    /**
     * 获取NBA数据排名
     *
     * @param statType  数据类型。{@See Contast#StatType}
     * @param num       查询数据条数。建议25
     * @param tabType   比赛类型。
     * @param seasonId  赛季。格式：YYYY
     * @param isRefresh 是否重新请求数据
     * @param cbk
     */
    public static void getStatsRank(Constant.StatType statType, int num, Constant.TabType tabType, String seasonId, boolean isRefresh, final GetBeanCallback<StatsRank> cbk) {
        final String key = "getStatsRank" + statType.getType() + tabType.getType() + seasonId;
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                StatsRank rank = JsonParser.parseStatsRank(jsonStr);
                cbk.onSuccess(rank);
                return;
            }
        }

        apiStr.getStatsRank(statType.getType(), num, tabType.getType(), seasonId, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                StatsRank rank = JsonParser.parseStatsRank(jsonStr);
                cbk.onSuccess(rank);
                prefsUtils.put(key, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
            }
        });
    }

    /**
     * 获取所有球员信息
     *
     * @param isRefresh 是否重新请求数据
     */
    public static void getPlayerList(boolean isRefresh, final GetBeanCallback<Players> cbk) {
        final String key = "getPlayerList";
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                Players players = JsonParser.parseWithGson(Players.class, jsonStr);
                cbk.onSuccess(players);
                return;
            }
        }
        apiStr.getPlayerList(new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                Players players = JsonParser.parseWithGson(Players.class, jsonStr);
                cbk.onSuccess(players);
                prefsUtils.put(key, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    /**
     * 获取所有球队信息
     *
     * @param isRefresh 是否重新请求数据
     */
    public static void getTeamList(boolean isRefresh, final GetBeanCallback<Teams> cbk) {

        final String key = "getPlayerList";
        final PrefsUtils prefsUtils = new PrefsUtils();
        if (!isRefresh) {
            String jsonStr = prefsUtils.get(key, "");
            if (jsonStr.length() > 1) {
                Teams teams = JsonParser.parseWithGson(Teams.class, jsonStr);
                cbk.onSuccess(teams);
                return;
            }
        }

        apiStr.getTeamList(new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                Teams teams = JsonParser.parseWithGson(Teams.class, jsonStr);
                cbk.onSuccess(teams);
                prefsUtils.put(key, jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
