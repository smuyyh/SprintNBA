package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;

import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.hupu.forum.HupuForumService;
import com.yuyh.sprintnba.http.bean.base.BaseData;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.ReportView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/28.
 */
public class ReportPresenterImpl implements Presenter {

    private Context context;
    private ReportView reportView;

    public ReportPresenterImpl(Context context, ReportView reportView) {
        this.context = context;
        this.reportView = reportView;
    }

    @Override
    public void initialized() {
        List<String> list = new ArrayList<String>() {{
            add("\u5e7f\u544a\u6216\u5783\u573e\u5185\u5bb9");
            add("\u8272\u60c5\u66b4\u9732\u5185\u5bb9");
            add("\u653f\u6cbb\u654f\u611f\u8bdd\u9898");
            add("\u4eba\u8eab\u653b\u51fb\u7b49\u6076\u610f\u884c\u4e3a");
        }};
        reportView.showType(list);
    }

    public void submitReports(String tid, String pid, String type, String content) {
        reportView.showLoading("举报中");
        HupuForumService.submitReports(tid, pid, type, content, new RequestCallback<BaseData>() {
            @Override
            public void onSuccess(BaseData baseData) {
                if (baseData != null) {
                    if (baseData.error != null) {
                        reportView.showError(baseData.error.msg);
                    } else {
                        reportView.reportSuccess();
                    }
                }
                reportView.hideLoading();
            }

            @Override
            public void onFailure(String message) {
                reportView.hideLoading();
            }
        });
    }
}
