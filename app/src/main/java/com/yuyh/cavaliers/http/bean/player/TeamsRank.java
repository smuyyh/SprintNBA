package com.yuyh.cavaliers.http.bean.player;

import com.yuyh.cavaliers.http.bean.base.Base;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class TeamsRank extends Base {


    public List<TeamBean> east;
    public List<TeamBean> west;

    public static class TeamBean {
        public String teamId;
        public String name;
        public String badge;
        public String serial;
        public String color;
        public String detailUrl;

        public int win;
        public int lose;
        public String rate;
        public String difference; //胜场差
    }
}
