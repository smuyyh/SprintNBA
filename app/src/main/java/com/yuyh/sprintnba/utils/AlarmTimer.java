package com.yuyh.sprintnba.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时/定时器
 *
 * @author yuyuhang.
 * @date 16/3/1.
 */
public abstract class AlarmTimer {

    private Timer timer;
    private TimerTask timerTask;
    private int COUNT_DOWN = 60;// 倒计时时间(s)
    private int time = COUNT_DOWN;
    private boolean isRunning = false;

    /**
     * 立即执行计时
     */
    public void start(long periodTime) {
        start(0, periodTime);
    }

    /**
     * 开始执行计时
     *
     * @param delayTime 首次执行的间隔时间(ms)，0：立即执行
     */
    public void start(int delayTime, long periodTime) {
        shutDown();
        isRunning = true;
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    timeout();
                }
            };
        }

        timer = new Timer();
        timer.schedule(timerTask, delayTime, periodTime); // 延迟delayTime秒执行，间隔1秒
    }

    /**
     * 关闭计时管理器
     */
    public synchronized void shutDown() {
        isRunning = false;
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        time = COUNT_DOWN;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 间隔时间到后处理的事件
     */
    public abstract void timeout();
}
