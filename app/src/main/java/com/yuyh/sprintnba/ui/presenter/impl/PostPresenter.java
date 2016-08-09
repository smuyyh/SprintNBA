package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.hupu.forum.HupuForumService;
import com.yuyh.sprintnba.http.bean.base.BaseData;
import com.yuyh.sprintnba.http.bean.bmob.Feedback;
import com.yuyh.sprintnba.http.bean.forum.AddReplyData;
import com.yuyh.sprintnba.http.bean.forum.PermissionData;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.ui.view.PostView;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.library.utils.toast.ToastUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
                                postView.checkPermissionSuccess(true, 0, "", false);
                            }
                        } else {
                            postView.checkPermissionSuccess(false, 0, "获取评论权限失败，请重试", true);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        postView.checkPermissionSuccess(false, 0, "获取评论权限失败，请重试", true);
                    }
                });
    }

    /**
     * 评论
     *
     * @param tid
     * @param fid
     * @param pid
     * @param content
     */
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

    /**
     * 发布帖子
     *
     * @param fid
     * @param content
     * @param title
     */
    public void post(String fid, String content, String title) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            ToastUtils.showSingleToast("标题和内容不能为空哦~");
            return;
        }
        HupuForumService.addThread(title, content, fid, new RequestCallback<BaseData>() {
            @Override
            public void onSuccess(BaseData baseData) {
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

    /**
     * 提交反馈
     *
     * @param title
     * @param content
     */
    public void feedback(String title, String content) {
        if (!(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))) {
            ToastUtils.showSingleToast("标题和内容至少填一项哦~");
            return;
        }
        postView.showLoadding();
        Feedback feedback = new Feedback();
        feedback.setTitle(title);
        feedback.setMessage(content);
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.showSingleToast("成功提交反馈");
                    postView.feedbackSuccess();
                    postView.hideLoadding();
                } else {
                    ToastUtils.showSingleToast("提交失败");
                    LogUtils.i("bmob失败：" + e.getMessage() + "," + e.getErrorCode());
                    postView.hideLoadding();
                }
            }
        });
    }
}
