package com.yuyh.cavaliers.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.Request;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.library.view.common.Info;
import com.yuyh.library.view.image.PhotoView;

import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.InjectView;

public class NewsDetailActivity extends BaseSwipeBackCompatActivity {

    public static final String ARTICLE_ID = "arcId";
    public static final String TITLE = "title";

    @InjectView(R.id.llNewsDetail)
    LinearLayout llNewsDetail;
    @InjectView(R.id.tvNewsDetailTitle)
    TextView tvNewsDetailTitle;
    @InjectView(R.id.tvNewsDetailTime)
    TextView tvNewsDetailTime;

    @InjectView(R.id.ivBrowser)
    PhotoView mPhotoView;
    @InjectView(R.id.flParent)
    View mParent;
    @InjectView(R.id.bg)
    View mBg;

    Info mInfo;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

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
        setTitle("详细新闻");
        String arcId = intent.getStringExtra(ARTICLE_ID);
        if (!TextUtils.isEmpty(arcId)) {
            requestNewsDetail(arcId);
        }
        initPhotoView();
    }

    private void initPhotoView() {
        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mPhotoView.setScaleType(ImageView.ScaleType.FIT_START);
        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        mParent.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void requestNewsDetail(String arcId) {
        Request.getNewsDetail(Constant.NewsType.BANNER, arcId, false, new GetBeanCallback<NewsDetail>() {
            @Override
            public void onSuccess(NewsDetail newsDetail) {
                tvNewsDetailTime.setText(newsDetail.time);
                tvNewsDetailTitle.setText(newsDetail.title);
                List<Map<String, String>> content = newsDetail.content;
                for (Map<String, String> map : content) {
                    Set<String> set = map.keySet();
                    if (set.contains("img")) {
                        final String url = map.get("img");
                        if (!TextUtils.isEmpty(url)) {
                            PhotoView iv = (PhotoView) inflate.inflate(R.layout.imageview_news_detail, null);
                            Glide.with(NewsDetailActivity.this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
                            llNewsDetail.addView(iv);
                            //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
                            //params.width = DimenUtils.getScreenWidth();
                            //params.height = DimenUtils.getScreenWidth();
                            //iv.setLayoutParams(params);
                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mInfo = ((PhotoView) v).getInfo();
                                    Glide.with(NewsDetailActivity.this).load(url).into(mPhotoView);
                                    mBg.startAnimation(in);
                                    mBg.setVisibility(View.VISIBLE);
                                    mParent.setVisibility(View.VISIBLE);
                                    mPhotoView.animaFrom(mInfo);
                                }
                            });
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

    @Override
    public void onBackPressed() {
        if (mParent.getVisibility() == View.VISIBLE) {
            mBg.startAnimation(out);
            mPhotoView.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    mParent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
