package com.yuyh.cavaliers.http.constant;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class Constant {
    public enum NewsType implements Serializable{

        BANNER("banner"),      //头条
        NEWS("news"),          //新闻
        VIDEO("videos"),     //视频集锦
        DEPTH("depth"),        //十佳球/五佳球
        HIGHLIGHT("highlight");//赛场花絮

        String type;

        NewsType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum StatType {

        POINT("point"),      // 得分
        REBOUND("rebound"),  // 篮板
        ASSIST("assist"),    // 助攻
        BLOCK("block"),      // 盖帽
        STEAL("steal");      // 抢断

        String type;

        StatType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum TabType {
        EVERYDAY("1"),// 每日
        FINAL("2"),   // 季后赛
        NORMAL("3");  // 常规赛

        String type;

        TabType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
