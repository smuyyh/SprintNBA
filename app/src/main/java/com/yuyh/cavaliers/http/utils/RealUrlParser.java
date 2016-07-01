package com.yuyh.cavaliers.http.utils;

import com.yuyh.cavaliers.http.bean.news.VideoRealUrl;

import java.io.InputStream;

/**
 * @author yuyh.
 * @date 16/7/1.
 */
public interface RealUrlParser {

    VideoRealUrl parse(InputStream is) throws Exception;
}
