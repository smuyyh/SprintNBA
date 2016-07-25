package com.yuyh.sprintnba.http.constant;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class NBATeam {

    public enum Area {
        EAST,
        WEST
    }


    public enum Team {
        Hawks(1, "老鹰", "亚特兰大", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/1.png"),
        Celtics(2, "凯尔特人", "波士顿", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/2.png"),
        Pelicans(3, "鹈鹕", "新奥尔良", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/3.png"),
        Bulls(4, "公牛", "芝加哥", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/4.png"),
        Cavaliers(5, "骑士", "克利夫兰", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/5.png"),
        Mavericks(6, "小牛", "达拉斯", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/6.png"),
        Nuggets(7, "掘金", "丹佛", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/7.png"),
        Pistons(8, "活塞", "底特律", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/8.png"),
        Warriors(9, "勇士", "金州", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/9.png"),
        Rockets(10, "火箭", "休斯顿", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/10.png"),
        Pacers(11, "步行者", "印第安纳", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/11.png"),
        Clippers(12, "快船", "洛杉矶", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/12.png"),
        Lakers(13, "湖人", "洛杉矶", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/13.png"),
        Heats(14, "热火", "迈阿密", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/14.png"),
        Bucks(15, "雄鹿", "密尔沃基", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/15.png"),
        Timberwolves(16, "森林狼", "明尼苏达", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/16.png"),
        Nets(17, "篮网", "布鲁克林", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/17.png"),
        Knicks(18, "尼克斯", "纽约", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/18.png"),
        Magic(19, "魔术", "奥兰多", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/19.png"),
        SevenSixers(20, "76人", "费城", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/20.png"),
        Suns(21, "太阳", "菲尼克斯", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/21.png"),
        Blazers(22, "开拓者", "波特兰", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/22.png"),
        Kings(23, "国王", "萨克拉门托", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/23.png"),
        Spurs(24, "马刺", "圣安东尼奥", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/24.png"),
        Thunder(25, "雷霆", "俄克拉荷马", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/25.png"),
        Jazz(26, "爵士", "犹他", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/26.png"),
        Wizards(27, "奇才", "华盛顿", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/27.png"),
        Raptors(28, "猛龙", "多伦多", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/28.png"),
        Grizzlies(29, "灰熊", "孟菲斯", Area.WEST, "http://mat1.gtimg.com/sports/nba/logo/1602/29.png"),
        Bobcats(30, "黄蜂", "夏洛特", Area.EAST, "http://mat1.gtimg.com/sports/nba/logo/1602/30.png");

        int teamId;
        String teamName;
        String city;
        Area area;
        String imgUrl;

        Team(int teamId, String teamName, String city, Area area, String imgUrl) {
            this.teamId = teamId;
            this.teamName = teamName;
            this.city = city;
            this.area = area;
            this.imgUrl = imgUrl;
        }
    }
}
