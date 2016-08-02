package com.yuyh.sprintnba.http.bean.match;

import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class LiveDetail extends Base {

    public LiveDetailData data;

    public static class LiveDetailData implements Serializable {
        public TeamInfo teamInfo;
        public List<LiveContent> detail;

        public static class TeamInfo implements Serializable {
            public String leftName;
            public String rightName;
        }

        public static class LiveContent implements Serializable {
            public String id;
            public String ctype;
            public String content;
            public String type;
            public String quarter;
            public String time;
            public String teamId;
            public String plus;
            public String sendTime;
            public String topIndex;
            public String version;
            public String leftGoal;
            public String rightGoal;
            public String teamName;

            /**
             *   "ctype":"2",
                 "content":" 布鲁 突破上篮：命中（9分）助攻：艾谢丽（1次助攻）",
                 "type":"1",
                 "quarter":"第1节",
                 "time":"07:23",
                 "teamId":"6",
                 "plus":"+2",
                 "sendTime":"2016-07-02 21:08:46",
                 "topIndex":"0",
                 "version":"3512017050",
                 "leftGoal":"5",
                 "rightGoal":"9",
                 "teamName":"小牛"
             */
        }
    }
}
