package com.yuyh.sprintnba.ui.view;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface ThreadDetailView{

    void showError(String msg);

    void loadContent(int page, List<String> urls);

    void isCollected(boolean isCollected);

    void onToggleFloatingMenu();

    void goPost(String title);

    void goReport();
}
