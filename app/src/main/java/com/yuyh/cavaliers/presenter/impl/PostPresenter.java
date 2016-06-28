package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.api.hupu.forum.HupuForumService;
import com.yuyh.cavaliers.http.bean.base.BaseData;
import com.yuyh.cavaliers.http.bean.forum.AddReplyData;
import com.yuyh.cavaliers.http.bean.forum.PermissionData;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.ui.view.PostView;
import com.yuyh.library.utils.toast.ToastUtils;

/**
 * @author yuyh.
 * @date 2016/6/27.
 */
public class PostPresenter {

    private Context context;
    private PostView postView;

    private String fid;
    private String tid;
    private String pid;

    public PostPresenter(Context context, PostView postView) {
        this.context = context;
        this.postView = postView;
    }

    /**
     * 检查是否有评论帖子的权限
     */
    public void checkPermission(int type, String fid, String tid) {
        HupuForumService.checkPermission(fid, tid, type == Constant.TYPE_POST ? "threadPublish" : "threadReply",
                new RequestCallback<PermissionData>() {
                    @Override
                    public void onSuccess(PermissionData permissionData) {
                        if (permissionData != null) {
                            if (permissionData.error != null) {
                                postView.checkPermissionSuccess(false, permissionData.error.code, permissionData.error.msg, false);
                            } else {
                                postView.checkPermissionSuccess(true, permissionData.error.code, "", false);
                            }
                        } else {
                            postView.checkPermissionSuccess(false, permissionData.error.code, "获取评论权限失败，请重试", true);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        postView.checkPermissionSuccess(false, 0, "获取评论权限失败，请重试", true);
                    }
                });
    }

    public void comment(String tid, String fid, String pid, String content) {
        HupuForumService.addReplyByApp(tid, fid, pid, content, new RequestCallback<AddReplyData>() {
            @Override
            public void onSuccess(AddReplyData baseData) {
                if (baseData != null) {
                    if (baseData.error != null) {
                        ToastUtils.showSingleToast("发送失败：" + baseData.error.msg);
                    } else if (baseData.status == 200) {
                        ToastUtils.showSingleToast("发送成功");
                        postView.postSuccess();
                    } else {
                        ToastUtils.showSingleToast("发送失败");
                    }
                } else {
                    ToastUtils.showSingleToast("发送失败,请检查网络");
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtils.showSingleToast("发送失败,请检查网络");
            }
        });
    }

    public void post(String fid, String content, String title) {
        HupuForumService.addThread(title, content, fid, new RequestCallback<BaseData>() {
            @Override
            public void onSuccess(BaseData baseData) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
