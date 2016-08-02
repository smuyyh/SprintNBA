package com.yuyh.sprintnba.http.bean.forum;

import java.io.Serializable;
import java.util.ArrayList;

public class ThreadListData implements Serializable {
    public ThreadListResult result;

    public static class ThreadListResult implements Serializable {
        public String stamp;
        public ArrayList<ThreadInfo> data;
        public boolean nextPage;
        public int nextDataExists;
    }

    public static class ThreadInfo implements Serializable {
        public String tid;
        public String title;
        public String puid;
        public String fid;
        public String replies;
        public String userName;
        public String time;
        //public String imgs;
        public int lightReply;
        public ForumsData.Forum forum;
    }

}
