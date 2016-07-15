package com.yuyh.sprintnba.utils;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * @author yuyh.
 * @date 16/7/1.
 */
public class FrescoUtils {

    /**
     * 渐进式加载
     *
     * @param uri
     * @param draweeView
     * @return
     */
    public static PipelineDraweeController getController(Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        return controller;
    }

}
