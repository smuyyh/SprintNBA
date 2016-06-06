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
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                if (!TextUtils.isEmpty(imgUrl)) {
                    ImageView iv = new ImageView(mContext);
                    llNewsDetail.addView(iv);
                    Glide.with(NewsDetailActivity.this).load(imgUrl).into(iv);
                }
                List<String> content = newsDetail.content;
                for (String str : content) {
                    TextView tv = (TextView) inflate.inflate(R.layout.textview_news_detail, null);
                    tv.append(str);
                    llNewsDetail.addView(tv);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
