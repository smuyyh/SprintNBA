package com.yuyh.cavaliers.http.bean.match;

import com.yuyh.cavaliers.http.bean.base.Base;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class MatchCalendar extends Base {

    /**
     * startTime : 1384012800
     * endTime : 1467216000
     * matchNum :{"1":"1",2:"5"...}
     */

    private MatchCalendarBean data;

    public MatchCalendarBean getData() {
        return data;
    }

    public void setData(MatchCalendarBean data) {
        this.data = data;
    }

    public static class MatchCalendarBean implements Serializable {
        private String startTime;
        private String endTime;
        private Map<Integer, Integer> matchNum;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Map<Integer, Integer> getMatchNum() {
            return matchNum;
        }

        public void setMatchNum(Map<Integer, Integer> matchNum) {
            this.matchNum = matchNum;
        }
    }

}
