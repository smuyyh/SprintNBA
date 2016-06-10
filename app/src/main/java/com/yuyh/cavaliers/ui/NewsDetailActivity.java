package com.yuyh.cavaliers.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.Request;
import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.library.utils.io.FileUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.library.view.common.Info;
import com.yuyh.library.view.image.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.InjectView;
import butterknife.OnClick;

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
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSaveDialog();
                return false;
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

    @OnClick(R.id.ivBrowser)
    public void dismissPhotoView() {
        mBg.startAnimation(out);
        mPhotoView.animaTo(mInfo, new Runnable() {
            @Override
            public void run() {
                mParent.setVisibility(View.GONE);
            }
        });
    }

    private void showSaveDialog() {
        final String[] stringItems = {"保存图片", "微信分享"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, mPhotoView);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPhotoView.setDrawingCacheEnabled(true);
                final Bitmap bmp = Bitmap.createBitmap(mPhotoView.getDrawingCache());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (saveImageToGallery(bmp)) {
                            Looper.prepare();
                            ToastUtils.showToast("保存图片成功");
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            ToastUtils.showToast("保存图片失败");
                            Looper.loop();
                        }
                    }
                }).start();

                mPhotoView.setDrawingCacheEnabled(false);
                dialog.dismiss();
            }
        });
    }

    public boolean saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Calaliers/images");
        if (!appDir.exists()) {
            FileUtils.createDir(appDir.getAbsolutePath());
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        return true;
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
