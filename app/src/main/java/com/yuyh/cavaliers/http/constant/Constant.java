package com.yuyh.cavaliers.http.constant;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class Constant {

    public static String Cookie = "";



    public enum NewsType implements Serializable {

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

        POINT("point", "得分"),      // 得分
        REBOUND("rebound", "篮板"),  // 篮板
        ASSIST("assist", "助攻"),    // 助攻
        BLOCK("block", "盖帽"),      // 盖帽
        STEAL("steal", "抢断");      // 抢断

        String type;
        String name;

        StatType(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    public enum TabType {
        EVERYDAY("1", "每日"),// 每日
        FINAL("2", "季后赛"),   // 季后赛
        NORMAL("3", "常规赛");  // 常规赛

        String type;
        String name;

        TabType(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    public enum SortType {
        NEW("1"),
        HOT("2");

        String type;

        SortType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
