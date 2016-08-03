package com.yuyh.library.permission;

import java.util.List;

/**
 * Created by hupei on 2016/4/26.
 */
public interface AcpListener {
    /**
     *同意
     */
    void onGranted();

    /**
     * 拒绝
     */
    void onDenied(List<String> permissions);
}
