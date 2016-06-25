package com.yuyh.cavaliers.http.bean.forum;

import java.util.ArrayList;

/**
 * Created by sll on 2015/12/10.
 */
public class ForumInfoListData {
    public ForumInfoListResult result;

    public static class ForumInfoListResult {
        public String stamp;
        public ArrayList<ForumInfo> data;
        public boolean nextPage;
        public int nextDataExists;
    }

    public static class ForumInfo {
        public String tid;
        public String title;
        public String puid;
        public String fid;
        public String replies;
        public String userName;
        public String time;
        public String imgs;
        public int lightReply;
        public ForumsData.Forum forum;
    }

}
