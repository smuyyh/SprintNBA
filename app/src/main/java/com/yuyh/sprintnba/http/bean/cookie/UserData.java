package com.yuyh.sprintnba.http.bean.cookie;

import com.yuyh.sprintnba.http.bean.base.BaseError;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public class UserData implements Serializable {


    /**
     * uid : 3462967
     * username :
     * nickname : LeBron_Six
     * token : MzQ2Mjk2Nw==|MTQ2NjkyNTMyMg==|21b72606c91185e65c21bfe8457fe611
     * balance : 0
     * hupuDollor_balance : 0
     * follow : {"lids":[1,2,15,16,12,8,14,9,4,6,3,5,7],"tids":{"1":[10],"2":[]}}
     * bind : [{"channel":2,"status":1,"is_bind":0,"bind_name":""},{"channel":1,"status":1,"is_bind":1,"bind_name":"15*******38"},{"channel":3,"status":1,"is_bind":1,"bind_name":""}]
     * show_bind : 0
     * nickname_set_url : http://kanqiu.hupu.com/1/7.0.8/status/setNicknamePage?nickname=LeBron_Six&client=864260028174030
     */

    public LoginResult result;
    /**
     * result : {"uid":"3462967","username":"","nickname":"LeBron_Six","token":"MzQ2Mjk2Nw==|MTQ2NjkyNTMyMg==|21b72606c91185e65c21bfe8457fe611","balance":0,"hupuDollor_balance":0,"follow":{"lids":[1,2,15,16,12,8,14,9,4,6,3,5,7],"tids":{"1":[10],"2":[]}},"bind":[{"channel":2,"status":1,"is_bind":0,"bind_name":""},{"channel":1,"status":1,"is_bind":1,"bind_name":"15*******38"},{"channel":3,"status":1,"is_bind":1,"bind_name":""}],"show_bind":0,"nickname_set_url":"http://kanqiu.hupu.com/1/7.0.8/status/setNicknamePage?nickname=LeBron_Six&client=864260028174030"}
     * is_login : 1
     */

    public int is_login;

    public BaseError error;

    public static class LoginResult implements Serializable {
        public String uid;
        public String username;
        public String nickname;
        public String token;
        public int balance;
        public int hupuDollor_balance;
        /**
         * lids : [1,2,15,16,12,8,14,9,4,6,3,5,7]
         * tids : {"1":[10],"2":[]}
         */

        public int show_bind;
        public String nickname_set_url;
        /**
         * channel : 2
         * status : 1
         * is_bind : 0
         * bind_name :
         */

        public List<BindBean> bind;

        public static class BindBean implements Serializable {
            public int channel;
            public int status;
            public int is_bind;
            public String bind_name;
        }
    }
}
