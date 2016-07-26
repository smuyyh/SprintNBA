package com.yuyh.sprintnba.ui;

import android.view.View;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseAppCompatActivity;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.sprintnba.widget.photodraweeview.OnPhotoTapListener;
import com.yuyh.sprintnba.widget.photodraweeview.PhotoDraweeView;

import butterknife.InjectView;

public class ImagePreViewActivity extends BaseAppCompatActivity {

    public static final String INTENT_URL = "url";

    @InjectView(R.id.pdvImage)
    PhotoDraweeView pdvImage;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_image_pre_view;
    }

    @Override
    protected void initViewsAndEvents() {
        String url = getIntent().getStringExtra(INTENT_URL);
        pdvImage.setController(FrescoUtils.getPreController(url, pdvImage));
        pdvImage.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });
        pdvImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }
}
