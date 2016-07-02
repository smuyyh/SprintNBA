package com.yuyh.cavaliers.http.bean.match;

import com.yuyh.cavaliers.http.bean.base.Base;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class LiveIndex extends Base {

    Index data;

    public static class Index{
        List<String> index;
    }
}
