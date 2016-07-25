package com.yuyh.sprintnba.http.bean.cookie;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class Cookie implements Serializable {


    /**
     * code : 1000
     * msg : {"uid":28762565,"tag":"8c294f428b659ce1ee938e801c4d44b5"}
     * jumpUrl : http://passport.hupu.com/pc_index
     */
    public int code;
    /**
     * uid : 28762565
     * tag : 8c294f428b659ce1ee938e801c4d44b5
     */
    public CookieMsg msg;
    public String jumpUrl;

    public static class CookieMsg implements Serializable {
        public int uid;
        public String tag;
    }
}
