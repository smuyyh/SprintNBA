package com.yuyh.sprintnba.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.ui.presenter.impl.PostPresenter;
import com.yuyh.sprintnba.ui.view.PostView;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class PostActivity extends BaseSwipeBackCompatActivity implements PostView {

    public static final String INTENT_TITLE = "title";
    public static final String INTENT_TYPE = "type";
    public static final String INTENT_FID = "fid";
    public static final String INTENT_TID = "tid";
    public static final String INTENT_PID = "pid";

    private int type;
    private String fid;
    private String tid;
    private String pid;
    private String title;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(PostActivity.INTENT_TYPE, type);
        context.startActivity(intent);
    }

    public static void start(Context context, String title, int type, String fid, String tid, String pid) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(PostActivity.INTENT_TITLE, title);
        intent.putExtra(PostActivity.INTENT_TYPE, type);
        intent.putExtra(PostActivity.INTENT_FID, fid);
        intent.putExtra(PostActivity.INTENT_TID, tid);
        intent.putExtra(PostActivity.INTENT_PID, String.valueOf(pid));
        context.startActivity(intent);
    }

    public static void startForResult(Activity activity, String title, int type, String fid, String tid, String pid, int reqCode) {
        Intent intent = new Intent(activity, PostActivity.class);
        intent.putExtra(PostActivity.INTENT_TITLE, title);
        intent.putExtra(PostActivity.INTENT_TYPE, type);
        intent.putExtra(PostActivity.INTENT_FID, fid);
        intent.putExtra(PostActivity.INTENT_TID, tid);
        intent.putExtra(PostActivity.INTENT_PID, String.valueOf(pid));
        activity.startActivityForResult(intent, reqCode);
    }

    @InjectView(R.id.etSubject)
    EditText etSubject;
    @InjectView(R.id.etContent)
    EditText etContent;

    private NormalDialog dialog;
    private PostPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_post;
    }

    @Override
    protected void initViewsAndEvents() {
        Intent intent = getIntent();
        type = intent.getIntExtra(INTENT_TYPE, Constant.TYPE_COMMENT);
        fid = intent.getStringExtra(INTENT_FID);
        tid = intent.getStringExtra(INTENT_TID);
        pid = intent.getStringExtra(INTENT_PID);
        title = intent.getStringExtra(INTENT_TITLE);
        presenter = new PostPresenter(this, this);
        initPostType();
        invalidateOptionsMenu();
    }

    private void initPostType() {
        switch (type) {
            case Constant.TYPE_COMMENT:
                setTitle("评论");
                etSubject.setEnabled(false);
                etSubject.setText("引用:" + title);
                etContent.setHint("请输入您的评论");
                etContent.requestFocus();
                presenter.checkPermission(Constant.TYPE_COMMENT, fid, tid);
                break;
            case Constant.TYPE_REPLY:
                setTitle("回复");
                etSubject.setEnabled(false);
                etSubject.setText("引用:" + title);
                etContent.setHint("请输入您的回复");
                etContent.requestFocus();
                presenter.checkPermission(Constant.TYPE_REPLY, fid, tid);
                break;
            case Constant.TYPE_FEEDBACK:
                setTitle("反馈");
                break;
            case Constant.TYPE_AT:
                break;
            case Constant.TYPE_QUOTE:
                break;
            case Constant.TYPE_POST:
            default:
                setTitle("发新帖");
                presenter.checkPermission(Constant.TYPE_POST, fid, tid);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post, menu);
        if (type == Constant.TYPE_FEEDBACK) {
            MenuItem actionCamera = menu.findItem(R.id.action_camera);
            actionCamera.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                send();
                break;
            case R.id.action_camera:
                break;
            default:
                break;
        }
        return true;
    }

    private void send() {
        String content = etContent.getText().toString();
        if (type == Constant.TYPE_POST) {
            String title = etSubject.getText().toString();
            presenter.post(fid, content, title);
        } else if (type == Constant.TYPE_FEEDBACK) {
            presenter.feedback(etSubject.getText().toString(), content);
        } else {
            presenter.comment(tid, fid, pid, content);
        }
    }

    @Override
    public void showLoadding() {
        showLoadingDialog();
    }

    @Override
    public void hideLoadding() {
        hideLoadingDialog();
    }

    @Override
    public void postSuccess() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void postFailure(String msg) {

    }

    @Override
    public void feedbackSuccess() {
        finish();
    }

    @Override
    public void checkPermissionSuccess(boolean hasPermission, final int code, String msg, boolean retry) {
        if (!hasPermission) {
            dialog = new NormalDialog(this).isTitleShow(false).content(msg)
                    .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                    .btnNum(1);
            if (retry) {
                dialog.btnText("再试一次").setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        presenter.checkPermission(type, fid, tid);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {
                dialog.btnText("确定").setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if (code == 4) {
                            dialog.dismiss();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 500);
                    }
                });
                dialog.show();
            }
        }
    }
}
