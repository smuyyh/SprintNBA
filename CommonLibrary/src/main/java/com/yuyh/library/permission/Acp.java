package com.yuyh.library.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Acp {

    private static Acp mInstance;
    private AcpManager mAcpManager;

    public static Acp getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Acp(context);
        }
        return mInstance;
    }

    private Acp(Context context) {
        mAcpManager = new AcpManager(context.getApplicationContext());
    }

    /**
     * 开始请求权限
     *
     * @param options
     * @param acpListener
     */
    public void request(AcpOptions options, AcpListener acpListener) {
        if (options == null) new NullPointerException("AcpOptions is null...");
        if (acpListener == null) new NullPointerException("AcpListener is null...");
        mAcpManager.request(options, acpListener);
    }

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mAcpManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAcpManager.onActivityResult(requestCode, resultCode, data);
    }

    void requestPermissions(Activity activity) {
        mAcpManager.requestPermissions(activity);
    }
}
