package com.yuyh.sprintnba.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.sprintnba.widget.ImageLoadProgressBar;
import com.yuyh.sprintnba.widget.photodraweeview.OnPhotoTapListener;
import com.yuyh.sprintnba.widget.photodraweeview.PhotoDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 2016/7/26.
 */
public class ImagePreFragment extends BaseLazyFragment {

    public static final String INTENT_URL = "url";

    @InjectView(R.id.pdvImage)
    PhotoDraweeView pdvImage;

    private String url;

    public static ImagePreFragment newInstance(String url) {
        ImagePreFragment mFragment = new ImagePreFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_URL, url);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_image_pre_view);
        ButterKnife.inject(this, getContentView());

        url = getArguments().getString("url");
        LogUtils.i("img url = " + url);

        initImage();
    }

    private void initImage() {
        PipelineDraweeControllerBuilder controller = FrescoUtils.getPreController(url, pdvImage);
        controller.setControllerListener(listener);
        final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setProgressBarImage(new ImageLoadProgressBar(new ImageLoadProgressBar.OnLevelChangeListener() {
                    @Override
                    public void onChange(int level) {
                        if (level >= 100) {
                            hideLoadingDialog();
                        }
                    }
                }, Color.parseColor("#90CCCCCC"))).build();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        pdvImage.setHierarchy(hierarchy);
        pdvImage.setController(controller.build());
        pdvImage.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (mActivity != null)
                    mActivity.finish();
            }
        });
        pdvImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    private BaseControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>() {

        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            LogUtils.i("onFailure:" + throwable.getMessage());
            ToastUtils.showSingleToast("图片加载失败");
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            LogUtils.i("onFinalImageSet");
            if (imageInfo == null) {
                return;
            }
            pdvImage.update(imageInfo.getWidth(), imageInfo.getHeight());
        }

        @Override
        public void onSubmit(String id, Object callerContext) {
            super.onSubmit(id, callerContext);
            LogUtils.i("onSubmit");
        }
    };
}
