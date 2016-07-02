package com.yuyh.cavaliers.http.api.tencent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public interface TencentApi {

    //String NET_WORK = NetworkUtils.getNetworkType(CavsApp.getAppContext()) == NetworkUtils.NetWorkType.Wifi ? "WIFI" : "MOBILE";

    String URL_SUFFIX = "?appver=1.0.2.2&appvid=1.0.2.2&network=WIFI";

    @GET("/match/calendar" + URL_SUFFIX)
    Call<String> getMatchCalendar(@Query("teamId") int teamId, @Query("year") int year, @Query("month") int month);

    @GET("/match/listByDate" + URL_SUFFIX)
    Call<String> getMatchsByData(@Query("date") String date);

    //calendar?teamId=27&year=2016&month=7 // 查询球队赛程

    @GET("/news/index" + URL_SUFFIX)
    Call<String> getNewsIndex(@Query("column") String column);

    @GET("/news/item" + URL_SUFFIX)
    Call<String> getNewsItem(@Query("column") String column, @Query("articleIds") String articleIds);

    @GET("/news/detail" + URL_SUFFIX)
    Call<String> getNewsDetail(@Query("column") String column, @Query("articleId") String articleId);

    @GET("/player/statsRank" + URL_SUFFIX)
    Call<String> getStatsRank(@Query("statType") String statType, @Query("num") int num, @Query("tabType") String tabType, @Query("seasonId") String seasonId);

    // rankByDivision // 分区排名
    @GET("/team/rank" + URL_SUFFIX)
    Call<String> getTeamsRank();

    @GET("/player/list" + URL_SUFFIX)
    Call<String> getPlayerList();

    @GET("/team/list" + URL_SUFFIX)
    Call<String> getTeamList();
}
