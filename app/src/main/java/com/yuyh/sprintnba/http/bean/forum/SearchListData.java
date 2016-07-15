package com.yuyh.sprintnba.http.bean.forum;

import com.yuyh.sprintnba.http.bean.base.BaseError;

import java.util.List;


public class SearchListData {
    public SearchResult result;
    public BaseError error;

    public static class SearchResult {
        public int count;
        public int hasNextPage;
        public String search_title;
        public List<Search> data;
    }

    public static class Search {
        public String id;
        public String addtime;
        public String replies;
        public String lights;
        public String username;
        public String display_type;
        public String fid;
        public String title;
    }

}
