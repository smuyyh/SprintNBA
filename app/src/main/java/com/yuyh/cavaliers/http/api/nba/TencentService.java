package com.yuyh.cavaliers.http.api.nba;

import com.yuyh.cavaliers.BuildConfig;
import com.yuyh.cavaliers.http.bean.match.MatchCalendar;
import com.yuyh.cavaliers.http.bean.match.Matchs;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.bean.news.NewsIndex;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.http.bean.player.StatsRank;
import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.bean.player.TeamsRank;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.http.util.StringConverter;
import com.yuyh.cavaliers.http.util.JsonParser;
import com.yuyh.library.AppUtils;
import com.yuyh.library.utils.data.ACache;
import com.yuyh.library.utils.log.LogUtils;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public class TencentService {

    public static RestAdapter rest = new RestAdapter.Builder().setEndpoint(BuildConfig.TENCENT_SERVER).build();
    public static RestAdapter restStr = new RestAdapter.Builder().setEndpoint(BuildConfig.TENCENT_SERVER).setConverter(new StringConverter()).build();

    public static TencentApi api = rest.create(TencentApi.class);
    public static TencentApi apiStr = restStr.create(TencentApi.class);


    /**
     * 根据球队Id及年份月份获取赛程
     *
     * @param teamId    球队ID，默认-1为查询所有
     * @param year      年份
     * @param month     月份
     * @param isRefresh 是否重新请求数据
     * @param cbk
     */
    public static void getMatchCalendar(int teamId, int year, int month, boolean isRefresh, final RequestCallback<MatchCalendar> cbk) {
        final String key = "getMatchCalendar" + teamId + year + month;
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            MatchCalendar match = (MatchCalendar) obj;
            cbk.onSuccess(match);
            return;
        }
        apiStr.getMatchCalendar(teamId, year, month, new Callback<String>() {

            @Override
            public void success(String jsonStr, Response response) {
                MatchCalendar match = JsonParser.parseMatchCalendar(jsonStr);
                cbk.onSuccess(match);
                cache.put(key, match);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
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
    public static void getMatchsByDate(String date, boolean isRefresh, final RequestCallback<Matchs> cbk) {
        final String key = "getMatchsByDate" + date;
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            Matchs matchs = (Matchs) obj;
            cbk.onSuccess(matchs);
            return;
        }
        apiStr.getMatchsByData(date, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                LogUtils.d(jsonStr);
                Matchs matchs = JsonParser.parseWithGson(Matchs.class, jsonStr);
                cbk.onSuccess(matchs);
                cache.put(key, matchs);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
            }
        });
    }

    /**
     * 获取所有新闻索引
     *
     * @param isRefresh 是否重新请求数据
     * @param cbk
     */
    public static void getNewsIndex(Constant.NewsType newsType, boolean isRefresh, final RequestCallback<NewsIndex> cbk) {
        final String key = "getNewsIndex" + newsType.getType();
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            NewsIndex index = (NewsIndex) obj;
            cbk.onSuccess(index);
            return;
        }

        apiStr.getNewsIndex(newsType.getType(), new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                NewsIndex index = JsonParser.parseWithGson(NewsIndex.class, jsonStr);
                cbk.onSuccess(index);
                cache.put(key, index);
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
    public static void getNewsItem(Constant.NewsType newsType, String articleIds, boolean isRefresh, final RequestCallback<NewsItem> cbk) {
        final String key = "getNewsItem" + articleIds;
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            NewsItem newsItem = (NewsItem) obj;
            cbk.onSuccess(newsItem);
            return;
        }

        // 由于新闻列表json采用动态key，故无法直接通过Gson解析成对象，这里做自定义解析
        apiStr.getNewsItem(newsType.getType(), articleIds, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                NewsItem newsItem = JsonParser.parseNewsItem(jsonStr);
                cbk.onSuccess(newsItem);
                cache.put(key, newsItem);
                LogUtils.d("resp:" + jsonStr);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
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
    public static void getNewsDetail(Constant.NewsType newsType, String articleId, boolean isRefresh, final RequestCallback<NewsDetail> cbk) {
        final String key = "getNewsDetail" + articleId;
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            NewsDetail detail = (NewsDetail) obj;
            cbk.onSuccess(detail);
            return;
        }
        apiStr.getNewsDetail(newsType.getType(), articleId, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                NewsDetail detail = JsonParser.parseNewsDetail(jsonStr);
                cbk.onSuccess(detail);
                cache.put(key, detail);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
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
    public static void getStatsRank(Constant.StatType statType, int num, Constant.TabType tabType, String seasonId, boolean isRefresh, final RequestCallback<StatsRank> cbk) {
        final String key = "getStatsRank" + statType.getType() + tabType.getType() + seasonId;
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            StatsRank rank = (StatsRank) obj;
            cbk.onSuccess(rank);
            return;
        }

        apiStr.getStatsRank(statType.getType(), num, tabType.getType(), seasonId, new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                StatsRank rank = JsonParser.parseStatsRank(jsonStr);
                cbk.onSuccess(rank);
                cache.put(key, rank);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
            }
        });
    }

    /**
     * 获取球队战绩排名
     *
     * @param cbk
     */
    public static void getTeamsRank(boolean isRefresh, final RequestCallback<TeamsRank> cbk) {
        final String key = "getTeamsRank";
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            TeamsRank rank = (TeamsRank) obj;
            cbk.onSuccess(rank);
            return;
        }

        apiStr.getTeamsRank(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                TeamsRank rank = JsonParser.parseTeamsRank(s);
                if (rank != null) {
                    rank.all = new ArrayList<>();
                    TeamsRank.TeamBean eastTitle = new TeamsRank.TeamBean();
                    eastTitle.type = 1;
                    eastTitle.name = "东部联盟";
                    rank.all.add(eastTitle);
                    rank.all.addAll(rank.east);

                    TeamsRank.TeamBean westTitle = new TeamsRank.TeamBean();
                    westTitle.type = 2;
                    westTitle.name = "西部联盟";
                    rank.all.add(westTitle);
                    rank.all.addAll(rank.west);
                    cbk.onSuccess(rank);
                    cache.put(key, rank);
                } else {
                    cbk.onFailure("数据解析失败");
                    cache.remove(key);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
            }
        });
    }

    /**
     * 获取所有球员信息
     *
     * @param isRefresh 是否重新请求数据
     */
    public static void getPlayerList(boolean isRefresh, final RequestCallback<Players> cbk) {
        final String key = "getPlayerList";
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            Players players = (Players) obj;
            cbk.onSuccess(players);
            return;
        }
        apiStr.getPlayerList(new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                Players players = JsonParser.parsePlayersList(jsonStr);
                cbk.onSuccess(players);
                cache.put(key, players);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
            }
        });
    }

    /**
     * 获取所有球队信息
     *
     * @param isRefresh 是否重新请求数据
     */
    public static void getTeamList(boolean isRefresh, final RequestCallback<Teams> cbk) {

        final String key = "getTeamList";
        final ACache cache = ACache.get(AppUtils.getAppContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            Teams teams = (Teams) obj;
            cbk.onSuccess(teams);
            return;
        }

        apiStr.getTeamList(new Callback<String>() {
            @Override
            public void success(String jsonStr, Response response) {
                Teams teams = JsonParser.parseWithGson(Teams.class, jsonStr);
                cbk.onSuccess(teams);
                cache.put(key, teams);
            }

            @Override
            public void failure(RetrofitError error) {
                cbk.onFailure(error.getMessage());
                cache.remove(key);
            }
        });
    }
}
