package com.yuyh.sprintnba.http.bean.player;

import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class Teams extends Base{

    public TeamsBean data;

    public static class TeamsBean implements Serializable {
        /**
         * teamId : 6
         * teamName : 小牛
         * fullCnName : 达拉斯小牛
         * logo : http://mat1.gtimg.com/sports/nba/logo/1602/6.png
         * detailUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=6
         * city : 达拉斯
         */

        public List<Team> west;

        public List<Team> east;

        public static class Team implements Serializable {
            public String teamId;
            public String teamName;
            public String fullCnName;
            public String logo;
            public String detailUrl;
            public String city;
        }
    }
}
