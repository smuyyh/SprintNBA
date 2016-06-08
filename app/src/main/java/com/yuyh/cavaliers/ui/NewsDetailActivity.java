package com.yuyh.cavaliers.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.Request;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.http.constant.Constant;

import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.InjectView;

public class NewsDetailActivity extends BaseSwipeBackCompatActivity {

    public static final String ARTICLE_ID = "arcId";
    public static final String TITLE = "title";

    @InjectView(R.id.llNewsDetail)
    LinearLayout llNewsDetail;

    private LayoutInflater inflate;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initViewsAndEvents() {
        inflate = LayoutInflater.from(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        if (!TextUtils.isEmpty(title))
            setTitle(title);
        else
            setTitle("详细内容");
        String arcId = intent.getStringExtra(ARTICLE_ID);
        if (!TextUtils.isEmpty(arcId)) {
            requestNewsDetail(arcId);
        }
    }

    private void requestNewsDetail(String arcId) {
        Request.getNewsDetail(Constant.NewsType.BANNER, arcId, false, new GetBeanCallback<NewsDetail>() {
            @Override
            public void onSuccess(NewsDetail newsDetail) {
                String imgUrl = newsDetail.imgurl;
//                if (!TextUtils.isEmpty(imgUrl)) {
//                    ImageView iv = new ImageView(mContext);
//                    llNewsDetail.addView(iv);
//                    Glide.with(NewsDetailActivity.this).load(imgUrl).into(iv);
//                }
                List<Map<String, String>> content = newsDetail.content;
                for (Map<String, String> map : content) {
                    Set<String> set = map.keySet();
                    if (set.contains("img")) {
                        String url = map.get("img");
                        if (!TextUtils.isEmpty(url)) {
                            ImageView iv = new ImageView(NewsDetailActivity.this);
                            Glide.with(NewsDetailActivity.this).load(url).into(iv);
                            llNewsDetail.addView(iv);
                        }
                    } else {
                        if (!TextUtils.isEmpty(map.get("text"))) {
                            TextView tv = (TextView) inflate.inflate(R.layout.textview_news_detail, null);
                            tv.append(map.get("text"));
                            llNewsDetail.addView(tv);
                        }
                    }

                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
