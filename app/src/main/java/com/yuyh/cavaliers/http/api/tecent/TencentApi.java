package com.yuyh.cavaliers.http.api.tecent;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public interface TencentApi {

    //String NET_WORK = NetworkUtils.getNetworkType(CavsApp.getAppContext()) == NetworkUtils.NetWorkType.Wifi ? "WIFI" : "MOBILE";

    String URL_SUFFIX = "?appver=1.0.2.2&appvid=1.0.2.2&network=WIFI";

    @GET("/match/calendar" + URL_SUFFIX)
    void getMatchCalendar(@Query("teamId") int teamId, @Query("year") int year, @Query("month") int month, Callback<String> resp);

    @GET("/match/listByDate" + URL_SUFFIX)
    void getMatchsByData(@Query("date") String date, Callback<String> resp);

    @GET("/news/index" + URL_SUFFIX)
    void getNewsIndex(@Query("column") String column, Callback<String> resp);

    @GET("/news/item" + URL_SUFFIX)
    void getNewsItem(@Query("column") String column, @Query("articleIds") String articleIds, Callback<String> resp);

    @GET("/news/detail" + URL_SUFFIX)
    void getNewsDetail(@Query("column") String column, @Query("articleId") String articleId, Callback<String> resp);

    @GET("/player/statsRank" + URL_SUFFIX)
    void getStatsRank(@Query("statType") String statType, @Query("num") int num, @Query("tabType") String tabType, @Query("seasonId") String seasonId, Callback<String> resp);

    @GET("/team/rank" + URL_SUFFIX)
    void getTeamsRank(Callback<String> resp);

    @GET("/player/list" + URL_SUFFIX)
    void getPlayerList(Callback<String> resp);

    @GET("/team/list" + URL_SUFFIX)
    void getTeamList(Callback<String> resp);
}
