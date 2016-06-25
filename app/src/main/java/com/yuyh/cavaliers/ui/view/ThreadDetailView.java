package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface ThreadDetailView extends BaseView {

    void loadContent(int page, List<String> urls);

    void isCollected(boolean isCollected);

    void onToggleFloatingMenu();
}
