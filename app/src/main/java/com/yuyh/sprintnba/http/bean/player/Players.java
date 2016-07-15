package com.yuyh.sprintnba.http.bean.player;

import com.yuyh.sprintnba.http.bean.base.Base;
import com.yuyh.library.view.list.indexablelistview.IndexEntity;

import java.util.List;


/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class Players extends Base {

    /**
     * id : 5071
     * cnName : 昆西-阿西
     * enName : Acy, Quincy
     * capital : A
     * teamId : 23
     * teamName : 国王
     * teamLogo : http://mat1.gtimg.com/sports/nba/logo/1602/23.png
     * teamUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=23
     * jerseyNum : 13
     * position : 大前锋
     * birthStateCountry : Texas
     * birth : 1990-10-06
     * height : 200cm
     * weight : 108kg
     * icon : http://nbachina.qq.com/media/img/players/head/230x185/203112.png
     * detailUrl : http://sports.qq.com/kbsweb/kbsshare/player.htm?ref=nbaapp&pid=5071
     */

    public List<Player> data;

    public static class Player extends IndexEntity {
        public String id;
        // 这边由于字母索引需要依托于IndexEntity的name属性，所以cnName额值就直接给IndexEntity的name属性
        //public String cnName;
        public String enName;
        public String capital;
        public String teamId;
        public String teamName;
        public String teamLogo;
        public String teamUrl;
        public String jerseyNum;
        public String position;
        public String birthStateCountry;
        public String birth;
        public String height;
        public String weight;
        public String icon;
        public String detailUrl;
    }
}
