package com.yuyh.sprintnba.http.bean.match;

import com.google.gson.annotations.SerializedName;
import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class LiveDetail extends Base {

    public LiveDetailData data;

    public static class LiveDetailData implements Serializable {
        public TeamInfo teamInfo;
        public List<LiveContent> detail;
    }

    public static class TeamInfo implements Serializable {
        public String leftName;
        public String rightName;
    }

    public static class LiveContent implements Serializable {
        public String id;
        public String ctype;
        public String content;
        public String type;
        public String quarter;
        public String time;
        public String teamId;
        public String plus;
        public String sendTime;
        public String topIndex;
        public String version;
        public String leftGoal;
        public String rightGoal;
        public String teamName;

        public CommentatorBean commentator;
        public ImageBean image;
        public VideoBean video;
    }

    public static class CommentatorBean {
        public String role;
        public String nick;
        public String logo;
    }

    public static class ImageBean {
        public List<UrlsBean> urls;
    }

    public static class UrlsBean {
        public String imageType;
        public String imageSize;
        public String small;
        public String large;
    }

    public static class VideoBean {
        public String vid;
        public String covers;
        public String area;
        public String langue;
        public String title;
        public String secondtitle;
        public String sectitle;
        public String desc;
        public String type;
        public String type_name;
        public String drm;
        public String duration;
        public String pic;
        public String pic_160x90;
        public String pic_496x280;
        public String playurl;
        public String playright;
        public String state;
        public String src;
        public String mtime;
        public String checkuptime;
        public String sync_cover;
        public String is_trailer;
        public String view_all;
        public String view_tod;
        public String view_yed;
        public String year;
        public String author;
        public String copyright_id;
        public String copyright;
        public String cmscheck;
        public String competitionid;
        public String matchid;
        public String weishi;
        public String ugc;
        public String imgurl;
        public String imgurl2;
        @SerializedName("abstract")
        public String abstractX;
        public String url;

    }
}
