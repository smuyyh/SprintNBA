package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;

import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.ImagePreView;

/**
 * @author yuyh.
 * @date 2016/7/26.
 */
public class ImagePreViewPresenter implements Presenter {

    private Context context;
    private ImagePreView preView;

    public ImagePreViewPresenter(Context context, ImagePreView preView) {
        this.context = context;
        this.preView = preView;
    }

    @Override
    public void initialized() {

    }
}
