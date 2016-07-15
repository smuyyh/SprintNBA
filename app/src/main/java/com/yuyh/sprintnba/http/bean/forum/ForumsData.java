package com.yuyh.sprintnba.http.bean.forum;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ForumsData implements Serializable {
    public ArrayList<ForumsResult> data;

    public static class ForumsResult implements Serializable {
        public String fid;
        public String name;
        public ArrayList<Forums> sub;
    }

    public static class Forums implements Serializable {
        public ArrayList<Forum> data;
        public int weight;
        public String name;
    }

    public static class Forum implements Serializable {
        public Long id;
        public String fid;
        public String name;
        public String logo;
        public String description;
        public String backImg;
        public String forumId;
        public String categoryName;
        public Integer weight;
    }

}
