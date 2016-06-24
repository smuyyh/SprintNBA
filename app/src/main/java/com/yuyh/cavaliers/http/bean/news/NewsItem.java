package com.yuyh.cavaliers.http.bean.news;

import com.google.gson.annotations.SerializedName;
import com.yuyh.cavaliers.http.bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public class NewsItem extends Base {

    /**
     * 20160603041445 : {"atype":"0","title":"詹皇：今天的比赛属于利文斯顿","abstract":"骑士的板凳输出乏力，仅得10分，勇士则多点开花。","imgurl":"http://inews.gtimg.com/newsapp_ls/0/333422118_640470/0","imgurl2":"http://inews.gtimg.com/newsapp_ls/0/333422118_150120/0","newsId":"20160603041445","url":"http://nbachina.qq.com/a/20160603/041445.htm","commentId":"1422192316","pub_time":"2016-06-03 15:02","column":"news","vid":"","duration":"","img_count":"0","images_3":[],"footer":""}
     */

    private List<NewsItemBean> data;

    /**
     * version : c020d3303db6179626d1f8c7fc77acfd
     */

    public static class NewsItemBean implements Serializable{

        /**
         * atype : 1
         * title : 高清-科比拍摄电影宣传片 花絮
         * abstract : 高清-科比拍摄《捉鬼敢死队》宣传片 花絮
         * imgurl : http://inews.gtimg.com/newsapp_ls/0/333429524_640470/0
         * imgurl2 : http://inews.gtimg.com/newsapp_ls/0/333429524_150120/0
         * newsId : 20160603042501
         * url : http://nbachina.qq.com/a/20160603/042501.htm
         * commentId : 1422206477
         * pub_time : 2016-06-03 15:15
         * column : news
         * vid :
         * duration :
         * img_count : 14
         * images_3 : ["http://inews.gtimg.com/newsapp_ls/0/333429524_200160/0","http://inews.gtimg.com/newsapp_ls/0/333429525_200160/0","http://inews.gtimg.com/newsapp_ls/0/333429526_200160/0"]
         * footer : 14图
         */
        private String index;
        private String atype;
        private String title;
        @SerializedName("abstract")
        private String abstractX;
        private String imgurl;
        private String imgurl2;
        private String newsId;
        private String url;
        private String commentId;
        private String pub_time;
        private String column;
        private String vid;
        private String duration;
        private String img_count;
        private String footer;
        private List<String> images_3;

        public NewsItemBean(String imgurl, String title, String pub_time) {
            this.imgurl = imgurl;
            this.title = title;
            this.pub_time = pub_time;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getAtype() {
            return atype;
        }

        public void setAtype(String atype) {
            this.atype = atype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getImgurl2() {
            return imgurl2;
        }

        public void setImgurl2(String imgurl2) {
            this.imgurl2 = imgurl2;
        }

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getImg_count() {
            return img_count;
        }

        public void setImg_count(String img_count) {
            this.img_count = img_count;
        }

        public String getFooter() {
            return footer;
        }

        public void setFooter(String footer) {
            this.footer = footer;
        }

        public List<String> getImages_3() {
            return images_3;
        }

        public void setImages_3(List<String> images_3) {
            this.images_3 = images_3;
        }
    }

    public List<NewsItemBean> getData() {
        return data;
    }

    public void setData(List<NewsItemBean> data) {
        this.data = data;
    }
}
