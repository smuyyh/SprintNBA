package com.yuyh.sprintnba.http.bean.match;

import com.yuyh.sprintnba.http.bean.base.Base;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class LiveIndex extends Base{

    public Index data;

    public static class Index{
        public List<String> index;
    }
}
