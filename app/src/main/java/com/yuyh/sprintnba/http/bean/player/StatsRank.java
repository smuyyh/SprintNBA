package com.yuyh.sprintnba.http.bean.player;

import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class StatsRank extends Base {

    public String statType;
    public List<RankItem> rankList;

    public static class RankItem implements Serializable {

        /**
         * serial : 25
         * playerId : 4612
         * playerUrl : http://sports.qq.com/kbsweb/kbsshare/player.htm?ref=nbaapp&pid=4612
         * playerName : 斯蒂芬-库里
         * playerEnName : Stephen Curry
         * playerIcon : http://nbachina.qq.com/media/img/players/head/230x185/201939.png
         * jerseyNum : 30
         * teamId : 9
         * teamUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=9
         * teamName : 勇士
         * teamIcon : http://mat1.gtimg.com/sports/nba/logo/1602/9.png
         * value : 0
         */

        public String serial;
        public String playerId;
        public String playerUrl;
        public String playerName;
        public String playerEnName;
        public String playerIcon;
        public String jerseyNum;
        public String teamId;
        public String teamUrl;
        public String teamName;
        public String teamIcon;
        public String value;
    }

}
