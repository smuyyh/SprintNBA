package com.yuyh.sprintnba.http.bean.match;

import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchBaseInfo extends Base {


    public BaseInfo data;
    /**
     * leftId : 25
     * leftName : 雷霆
     * leftBadge : http://mat1.gtimg.com/sports/nba/logo/1602/25.png
     * leftGoal : 0
     * leftUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=25
     * rightId : 6
     * rightName : 小牛
     * rightBadge : http://mat1.gtimg.com/sports/nba/logo/1602/6.png
     * rightGoal : 0
     * rightUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=6
     * matchPeriod : 0
     * matchType : 2
     * quarterDesc :  12:00
     * startDate : 07月02日
     * startHour : 21:00
     * venue : 美航中心
     * desc : 夏季联赛
     * hasLiveText : 1
     * updateFrequency : 1800
     * leftWins : 55
     * leftLosses : 27
     * rightWins : 42
     * rightLosses : 40
     */

    public static class BaseInfo implements Serializable {
        public String leftId;
        public String leftName;
        public String leftBadge;
        public String leftGoal;
        public String leftUrl;
        public String rightId;
        public String rightName;
        public String rightBadge;
        public String rightGoal;
        public String rightUrl;
        public String matchPeriod;
        public String matchType;
        public String quarterDesc;
        public String startDate;
        public String startHour;
        public String venue;
        public String desc;
        public String hasLiveText;
        public String updateFrequency;
        public String leftWins;
        public String leftLosses;
        public String rightWins;
        public String rightLosses;
    }
}
