package com.yuyh.sprintnba.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;

import com.yuyh.sprintnba.R;

public class LoadingDialog extends Dialog {
    private static LoadingDialog loadingDialog = null;
    public Animation animation;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static LoadingDialog createDialog(Context context) {
        loadingDialog = new LoadingDialog(context, R.style.CustomProgressDialog);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return loadingDialog;
    }

   /* public void onWindowFocusChanged(boolean hasFocus) {
        if (loadingDialog == null) {
            return;
        }
        ImageView imageView = (ImageView) loadingDialog.findViewById(R.id.ivLoading);
        float curTranslationY = imageView.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY", curTranslationY,
                -imageView.getHeight() / 3, curTranslationY, imageView.getHeight() / 3, curTranslationY);
        animator.setDuration(1500);
        animator.setRepeatMode(Animation.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

    }*/
}
