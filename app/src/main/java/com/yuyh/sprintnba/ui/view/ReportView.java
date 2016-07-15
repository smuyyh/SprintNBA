package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/28.
 */
public interface ReportView extends BaseView {

    void showType(List<String> list);

    void reportSuccess();
}
