package com.yuyh.cavaliers.event;

/**
 * @author yuyh.
 * @date 2016/7/5.
 */
public class BaseInfoEvent {

    public String leftGoal;
    public String rightGoal;
    public String quarter;
    public String time;

    public BaseInfoEvent(String leftGoal, String rightGoal, String quarter, String time) {
        this.leftGoal = leftGoal;
        this.rightGoal = rightGoal;
        this.quarter = quarter;
        this.time = time;
    }
}
