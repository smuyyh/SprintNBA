package com.yuyh.sprintnba.http.api.tencent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public interface TencentApi {

    String URL_SUFFIX = "?appver=1.0.2.2&appvid=1.0.2.2&network=WIFI";

    //calendar?teamId=27&year=2016&month=7 // 查询球队赛程
    @GET("/match/calendar")
    Call<String> getMatchCalendar(@Query("teamId") int teamId, @Query("year") int year, @Query("month") int month);

    @GET("/match/listByDate")
    Call<String> getMatchsByData(@Query("date") String date);

    // baseInfo?mid=100000:1468573
    @GET("/match/baseInfo")
    Call<String> getMatchBaseInfo(@Query("mid") String mid);

    // stat?mid=100000:1468573&tabType=3
    @GET("/match/stat")
    Call<String> getMatchStat(@Query("mid") String mid, @Query("tabType") String tabType);

    @GET("/video/matchVideo")
    Call<String> getMatchVideo(@Query("matchId") String mid);

    // baseInfo?mid=100000:1468573
    @GET("/match/textLiveIndex")
    Call<String> getMatchLiveIndex(@Query("mid") String mid);

    @GET("/match/textLiveDetail")
    Call<String> getMatchLiveDetail(@Query("mid") String mid, @Query("ids") String ids);

    @GET("/news/index")
    Call<String> getNewsIndex(@Query("column") String column);

    @GET("/news/item")
    Call<String> getNewsItem(@Query("column") String column, @Query("articleIds") String articleIds);

    @GET("/news/detail")
    Call<String> getNewsDetail(@Query("column") String column, @Query("articleId") String articleId);

    @GET("/player/statsRank")
    Call<String> getStatsRank(@Query("statType") String statType, @Query("num") int num, @Query("tabType") String tabType, @Query("seasonId") String seasonId);

    // rankByDivision // 分区排名
    @GET("/team/rank")
    Call<String> getTeamsRank();

    @GET("/player/list")
    Call<String> getPlayerList();

    @GET("/team/list")
    Call<String> getTeamList();


}
