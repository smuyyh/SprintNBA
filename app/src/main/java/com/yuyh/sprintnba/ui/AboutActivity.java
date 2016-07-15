package com.yuyh.sprintnba.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.widget.TextView;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.base.BaseWebActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class AboutActivity extends BaseSwipeBackCompatActivity {

    @InjectView(R.id.tvWeiboUrl)
    TextView tvWeiboUrl;
    @InjectView(R.id.tvBlog)
    TextView tvBlog;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("关于");
        tvWeiboUrl.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvBlog.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick({R.id.tvWeiboUrl, R.id.tvWeiboName})
    public void openWeibo() {
        Intent intent = new Intent(this, BaseWebActivity.class);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, tvWeiboUrl.getText().toString().trim());
        startActivity(intent);
    }

    @OnClick(R.id.tvBlog)
    public void openBlog() {
        Intent intent = new Intent(this, BaseWebActivity.class);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, tvBlog.getText().toString().trim());
        startActivity(intent);
    }
}
