package com.yuyh.sprintnba.http.bean.news;

import com.google.gson.annotations.SerializedName;
import com.yuyh.sprintnba.http.bean.base.Base;

import java.util.List;
import java.util.Map;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class NewsDetail extends Base {

    public String title;
    @SerializedName("abstract")
    public String abstractX;
    public List<Map<String, String>> content;

    public String url;
    public String imgurl;
    public String imgurl1;
    public String imgurl2;
    public String time;
    public String atype;
    public String commentId;
    public String newsAppId;
}
