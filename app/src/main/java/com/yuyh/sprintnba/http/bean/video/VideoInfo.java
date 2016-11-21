package com.yuyh.sprintnba.http.bean.video;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/11/21.
 */
public class VideoInfo {


    /**
     * dltype : 1
     * exem : 0
     * fl : {"cnt":3,"fi":[{"br":64,"cname":"标清;(270P)","fs":1794663,"id":10403,"lmt":0,"name":"sd","sb":1,"sl":1},{"br":650,"cname":"超清;(720P)","fs":7363034,"id":10401,"lmt":0,"name":"shd","sb":1,"sl":0},{"br":235,"cname":"高清;(360P)","fs":3307024,"id":10802,"lmt":0,"name":"hd","sb":1,"sl":0}]}
     * hs : 0
     * ls : 0
     * preview : 42
     * s : o
     * sfl : {"cnt":0}
     * tm : 1479694674
     * vl : {"cnt":1,"vi":[{"br":40,"ch":0,"cl":{"ci":[{"cd":"42.880","cmd5":"e2325f4859f6055b2fe43e310b1edc87","cs":1794663,"idx":1,"keyid":"m0022ect1qs.10403.1"}],"fc":1},"ct":1,"drm":0,"dsb":0,"fclip":1,"fmd5":"e2325f4859f6055b2fe43e310b1edc87","fn":"m0022ect1qs.p403.mp4","fs":1794663,"fst":5,"fvkey":"F03DC991CED43FA2220964602A592123123A1FC6E91250DBD8F36B69002F1C9A96A8632725D859ED1BA6E573F7F42AD6A21E7F343FC4ACB650C2330560AA7FF6A7E71C80ACE89980BCD15E6C2C2851793B8BFB03AC78BEF3","hevc":0,"iflag":1,"level":0,"lnk":"m0022ect1qs","logo":1,"pl":[{"cnt":2,"pd":[{"c":10,"cd":1,"fmt":40001,"fn":"q1","h":45,"r":10,"url":"http://video.qpic.cn/video_caps/0/","w":80},{"c":5,"cd":1,"fmt":40002,"fn":"q2","h":90,"r":5,"url":"http://video.qpic.cn/video_caps/0/","w":160}]}],"share":1,"sp":0,"st":2,"td":"42.88","ti":"【得分】开场首球就是十佳球？利拉德失衡神奇一掷打成2+1","type":1062,"ul":{"ui":[{"dt":2,"dtc":0,"url":"http://124.89.197.24/sports.tc.qq.com/","vt":218},{"dt":2,"dtc":0,"url":"http://vhotwsh.video.qq.com/flv/1/130/","vt":100},{"dt":2,"dtc":0,"url":"http://vhoth.dnion.videocdn.qq.com/flv/1/130/","vt":112},{"dt":2,"dtc":10,"url":"http://video.dispatch.tc.qq.com/87198850/","vt":0}]},"vh":272,"vid":"m0022ect1qs","videotype":4,"vst":2,"vw":480}]}
     */

    public int dltype;
    public int exem;
    /**
     * cnt : 3
     * fi : [{"br":64,"cname":"标清;(270P)","fs":1794663,"id":10403,"lmt":0,"name":"sd","sb":1,"sl":1},{"br":650,"cname":"超清;(720P)","fs":7363034,"id":10401,"lmt":0,"name":"shd","sb":1,"sl":0},{"br":235,"cname":"高清;(360P)","fs":3307024,"id":10802,"lmt":0,"name":"hd","sb":1,"sl":0}]
     */

    public FlBean fl;
    public int hs;
    public int ls;
    public int preview;
    public String s;
    /**
     * cnt : 0
     */

    public SflBean sfl;
    public int tm;
    /**
     * cnt : 1
     * vi : [{"br":40,"ch":0,"cl":{"ci":[{"cd":"42.880","cmd5":"e2325f4859f6055b2fe43e310b1edc87","cs":1794663,"idx":1,"keyid":"m0022ect1qs.10403.1"}],"fc":1},"ct":1,"drm":0,"dsb":0,"fclip":1,"fmd5":"e2325f4859f6055b2fe43e310b1edc87","fn":"m0022ect1qs.p403.mp4","fs":1794663,"fst":5,"fvkey":"F03DC991CED43FA2220964602A592123123A1FC6E91250DBD8F36B69002F1C9A96A8632725D859ED1BA6E573F7F42AD6A21E7F343FC4ACB650C2330560AA7FF6A7E71C80ACE89980BCD15E6C2C2851793B8BFB03AC78BEF3","hevc":0,"iflag":1,"level":0,"lnk":"m0022ect1qs","logo":1,"pl":[{"cnt":2,"pd":[{"c":10,"cd":1,"fmt":40001,"fn":"q1","h":45,"r":10,"url":"http://video.qpic.cn/video_caps/0/","w":80},{"c":5,"cd":1,"fmt":40002,"fn":"q2","h":90,"r":5,"url":"http://video.qpic.cn/video_caps/0/","w":160}]}],"share":1,"sp":0,"st":2,"td":"42.88","ti":"【得分】开场首球就是十佳球？利拉德失衡神奇一掷打成2+1","type":1062,"ul":{"ui":[{"dt":2,"dtc":0,"url":"http://124.89.197.24/sports.tc.qq.com/","vt":218},{"dt":2,"dtc":0,"url":"http://vhotwsh.video.qq.com/flv/1/130/","vt":100},{"dt":2,"dtc":0,"url":"http://vhoth.dnion.videocdn.qq.com/flv/1/130/","vt":112},{"dt":2,"dtc":10,"url":"http://video.dispatch.tc.qq.com/87198850/","vt":0}]},"vh":272,"vid":"m0022ect1qs","videotype":4,"vst":2,"vw":480}]
     */

    public VlBean vl;

    public static class FlBean {
        public int cnt;
        /**
         * br : 64
         * cname : 标清;(270P)
         * fs : 1794663
         * id : 10403
         * lmt : 0
         * name : sd
         * sb : 1
         * sl : 1
         */

        public List<FiBean> fi;
    }

    public static class FiBean {
        public int br;
        public String cname;
        public int fs;
        public int id;
        public int lmt;
        public String name;
        public int sb;
        public int sl;
    }

    public static class SflBean {
        public int cnt;
    }

    public static class VlBean {
        public int cnt;
        /**
         * br : 40
         * ch : 0
         * cl : {"ci":[{"cd":"42.880","cmd5":"e2325f4859f6055b2fe43e310b1edc87","cs":1794663,"idx":1,"keyid":"m0022ect1qs.10403.1"}],"fc":1}
         * ct : 1
         * drm : 0
         * dsb : 0
         * fclip : 1
         * fmd5 : e2325f4859f6055b2fe43e310b1edc87
         * fn : m0022ect1qs.p403.mp4
         * fs : 1794663
         * fst : 5
         * fvkey : F03DC991CED43FA2220964602A592123123A1FC6E91250DBD8F36B69002F1C9A96A8632725D859ED1BA6E573F7F42AD6A21E7F343FC4ACB650C2330560AA7FF6A7E71C80ACE89980BCD15E6C2C2851793B8BFB03AC78BEF3
         * hevc : 0
         * iflag : 1
         * level : 0
         * lnk : m0022ect1qs
         * logo : 1
         * pl : [{"cnt":2,"pd":[{"c":10,"cd":1,"fmt":40001,"fn":"q1","h":45,"r":10,"url":"http://video.qpic.cn/video_caps/0/","w":80},{"c":5,"cd":1,"fmt":40002,"fn":"q2","h":90,"r":5,"url":"http://video.qpic.cn/video_caps/0/","w":160}]}]
         * share : 1
         * sp : 0
         * st : 2
         * td : 42.88
         * ti : 【得分】开场首球就是十佳球？利拉德失衡神奇一掷打成2+1
         * type : 1062
         * ul : {"ui":[{"dt":2,"dtc":0,"url":"http://124.89.197.24/sports.tc.qq.com/","vt":218},{"dt":2,"dtc":0,"url":"http://vhotwsh.video.qq.com/flv/1/130/","vt":100},{"dt":2,"dtc":0,"url":"http://vhoth.dnion.videocdn.qq.com/flv/1/130/","vt":112},{"dt":2,"dtc":10,"url":"http://video.dispatch.tc.qq.com/87198850/","vt":0}]}
         * vh : 272
         * vid : m0022ect1qs
         * videotype : 4
         * vst : 2
         * vw : 480
         */

        public List<ViBean> vi;
    }

    public static class ViBean {
        public int br;
        public int ch;
        /**
         * ci : [{"cd":"42.880","cmd5":"e2325f4859f6055b2fe43e310b1edc87","cs":1794663,"idx":1,"keyid":"m0022ect1qs.10403.1"}]
         * fc : 1
         */

        public ClBean cl;
        public int ct;
        public int drm;
        public int dsb;
        public int fclip;
        public String fmd5;
        public String fn;
        public int fs;
        public int fst;
        public String fvkey;
        public int hevc;
        public int iflag;
        public int level;
        public String lnk;
        public int logo;
        public int share;
        public int sp;
        public int st;
        public String td;
        public String ti;
        public int type;
        public UlBean ul;
        public int vh;
        public String vid;
        public int videotype;
        public int vst;
        public int vw;
        /**
         * cnt : 2
         * pd : [{"c":10,"cd":1,"fmt":40001,"fn":"q1","h":45,"r":10,"url":"http://video.qpic.cn/video_caps/0/","w":80},{"c":5,"cd":1,"fmt":40002,"fn":"q2","h":90,"r":5,"url":"http://video.qpic.cn/video_caps/0/","w":160}]
         */

        public List<PlBean> pl;
    }

    public static class ClBean {
        public int fc;
        /**
         * cd : 42.880
         * cmd5 : e2325f4859f6055b2fe43e310b1edc87
         * cs : 1794663
         * idx : 1
         * keyid : m0022ect1qs.10403.1
         */

        public List<CiBean> ci;
    }

    public static class UlBean {
        /**
         * dt : 2
         * dtc : 0
         * url : http://124.89.197.24/sports.tc.qq.com/
         * vt : 218
         */

        public List<UiBean> ui;
    }

    public static class PlBean {
        public int cnt;
        /**
         * c : 10
         * cd : 1
         * fmt : 40001
         * fn : q1
         * h : 45
         * r : 10
         * url : http://video.qpic.cn/video_caps/0/
         * w : 80
         */

        public List<PdBean> pd;
    }

    public static class CiBean {
        public String cd;
        public String cmd5;
        public int cs;
        public int idx;
        public String keyid;
    }

    public static class UiBean {
        public int dt;
        public int dtc;
        public String url;
        public int vt;
    }

    public static class PdBean {
        public int c;
        public int cd;
        public int fmt;
        public String fn;
        public int h;
        public int r;
        public String url;
        public int w;
    }
}
