package com.yuyh.cavaliers.http.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuyh.cavaliers.http.bean.base.Base;
import com.yuyh.cavaliers.http.bean.match.MatchCalendar;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.http.bean.player.StatsRank;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class JsonParser {

    public static String parseBase(Base base, String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        String data = "{}";
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            if (entry.getKey().equals("code")) {
                base.setCode(Integer.parseInt(entry.getValue().toString()));
            } else if (entry.getKey().equals("version")) {
                base.setVersion(entry.getValue().toString());
            } else {
                data = entry.getValue().toString();
            }
        }
        return data;
    }

    public static <T> T parseWithGson(Class<T> classOfT, String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, classOfT);
    }

    public static MatchCalendar parseMatchCalendar(String jsonStr) {
        MatchCalendar match = new MatchCalendar();
        String dataStr = JsonParser.parseBase(match, jsonStr);
        JSONObject data = JSON.parseObject(dataStr);
        MatchCalendar.MatchCalendarBean bean = new MatchCalendar.MatchCalendarBean();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getKey().equals("startTime")) {
                bean.setStartTime(entry.getValue().toString());
            } else if (entry.getKey().equals("endTime")) {
                bean.setEndTime(entry.getValue().toString());
            } else if (entry.getKey().equals("matchNum")) {
                String matchNumStr = entry.getValue().toString();
                Map<Integer, Integer> matchNum = new HashMap<>();
                JSONObject matchNumObj = JSON.parseObject(matchNumStr);
                for (Map.Entry<String, Object> item : matchNumObj.entrySet()) {
                    matchNum.put(Integer.parseInt(item.getKey()), Integer.parseInt(item.getValue().toString()));
                }
                bean.setMatchNum(matchNum);
            }
        }
        match.setData(bean);
        return match;
    }

    public static NewsItem parseNewsItem(String jsonStr) {
        NewsItem newsItem = new NewsItem();
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            if (entry.getKey().equals("code")) {
                newsItem.setCode(Integer.parseInt(entry.getValue().toString()));
            } else if (entry.getKey().equals("version")) {
                newsItem.setVersion(entry.getValue().toString());
            } else if (entry.getKey().equals("data")) {
                JSONObject data = JSON.parseObject(entry.getValue().toString());
                List<NewsItem.NewsItemBean> list = new ArrayList<NewsItem.NewsItemBean>();
                for (Map.Entry<String, Object> item : data.entrySet()) {
                    Gson gson = new Gson();
                    NewsItem.NewsItemBean bean = gson.fromJson(item.getValue().toString(), NewsItem.NewsItemBean.class);
                    bean.setIndex(item.getKey());
                    list.add(bean);
                    Log.i("---", "---" + bean.getIndex() + " " + bean.getImgurl());
                }
                newsItem.setData(list);
            }
        }
        return newsItem;
    }

    public static NewsDetail parseNewsDetail(String jsonStr) {
        NewsDetail detail = new NewsDetail();
        String dataStr = JsonParser.parseBase(detail, jsonStr);
        JSONObject data = JSON.parseObject(dataStr);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getKey().equals("title")) {
                detail.title = entry.getValue().toString();
            } else if (entry.getKey().equals("abstract")) {
                detail.abstractX = entry.getValue().toString();
            } else if (entry.getKey().equals("content")) {
                String contentStr = entry.getValue().toString();

                try {
                    List<String> list = new LinkedList<>();
                    JSONArray jsonArray = new JSONArray(contentStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        org.json.JSONObject item = jsonArray.getJSONObject(i); // 得到每个对象
                        if (item.get("type").equals("text")) {
                            list.add(item.getString("info"));
                        }
                    }
                    detail.content = list;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (entry.getKey().equals("url")) {
                detail.url = entry.getValue().toString();
            } else if (entry.getKey().equals("imgurl")) {
                detail.imgurl = entry.getValue().toString();
            } else if (entry.getKey().equals("imgurl1")) {
                detail.imgurl1 = entry.getValue().toString();
            } else if (entry.getKey().equals("imgurl2")) {
                detail.imgurl2 = entry.getValue().toString();
            } else if (entry.getKey().equals("pub_time")) {
                detail.time = entry.getValue().toString();
            } else if (entry.getKey().equals("atype")) {
                detail.atype = entry.getValue().toString();
            } else if (entry.getKey().equals("commentId")) {
                detail.commentId = entry.getValue().toString();
            } else {
                detail.newsAppId = entry.getValue().toString();
            }
        }
        return detail;
    }

    public static StatsRank parseStatsRank(String jsonStr) {
        StatsRank rank = new StatsRank();
        String dataStr = JsonParser.parseBase(rank, jsonStr);
        JSONObject data = JSON.parseObject(dataStr);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            rank.statType = entry.getKey();
            String rankStr = entry.getValue().toString();
            Gson gson = new Gson();
            rank.rankList = gson.fromJson(rankStr, new TypeToken<List<StatsRank.RankItem>>() {
            }.getType());
        }
        return rank;
    }
}
