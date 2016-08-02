package com.yuyh.sprintnba.http.bean.news;

import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public class NewsIndex extends Base {


    /**
     * type : news
     * id : 20160603042788
     * column : banner
     * needUpdate : 0
     */

    public List<IndexBean> data;

    public static class IndexBean implements Serializable {
        public String type;
        public String id;
        public String column;
        public String needUpdate;
    }
}
