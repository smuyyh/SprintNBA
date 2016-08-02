package com.yuyh.sprintnba.http.bean.forum;

import java.io.Serializable;

public class ThreadsSchemaInfoData implements Serializable {

    /**
     * {
     * "pageSize":1,
     * "page":"1",
     * "client":"miui",
     * "isCollected":0,
     * "url":"http://bbs.mobileapi.hupu.com/1/7.0.8/threads/getThreadDetailInfoH5?tid=16603856&postAuthorPuid=0&pid=0&page=1&fid=85&client=miui&night=0&nopic=0",
     * "share":{
     * "wechat_moments":"不懂就问，骑士下赛季要怎么样增加轮换人数，让主力多休息",
     * "qzone":"不懂就问，骑士下赛季要怎么样增加轮换人数，让主力多休息 http://bbs.hupu.com/16603856.html",
     * "weibo":"不懂就问，骑士下赛季要怎么样增加轮换人数，让主力多休息 http://bbs.hupu.com/16603856.html",
     * "wechat":"不懂就问，骑士下赛季要怎么样增加轮换人数，让主力多休息",
     * "qq":"不懂就问，骑士下赛季要怎么样增加轮换人数，让主力多休息",
     * "img":"http://i3.hoopchina.com.cn/hupuapp/bbs/149/19166149/thread_19166149_20160625152743_816224658.png@400w_1l_60Q",
     * "url":"http://bbs.hupu.com/16603856.html",
     * "summary":"我个人觉得可以把麦克雷和今年选的小新秀翻出来用。"
     * },
     * "authorPuid":"19166149",
     * "fid":"85",
     * "domain_list":[
     * "hoopchina.com.cn",
     * "hupu.com"
     * ]
     * }
     */

    public int pageSize;
    public int page;
    public String client;
    public int isCollected;
    public String url;
    public Share share;
    public String authorPuid;
    public String fid;

    public static class Share implements Serializable {
        public String weibo;
        public String url;
        public String img;
        public String wechat_moments;
        public String qzone;
        public String wechat;
    }
}
