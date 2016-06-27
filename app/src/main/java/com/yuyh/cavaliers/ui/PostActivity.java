package com.yuyh.cavaliers.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.presenter.impl.PostPresenter;
import com.yuyh.cavaliers.ui.view.PostView;

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
    }

    private void initPostType() {
        switch (type) {
            case Constant.TYPE_COMMENT:
                setTitle("评论");
                etSubject.setEnabled(false);
                etSubject.setText("Comment:" + title);
                etContent.setHint("请输入您的评论");
                presenter.checkPermission(Constant.TYPE_COMMENT, fid, tid);
                break;
            case Constant.TYPE_REPLY:
                setTitle("回复");
                etSubject.setEnabled(false);
                etSubject.setText("Reply:" + title);
                etContent.setHint("请输入您的回复");
                presenter.checkPermission(Constant.TYPE_REPLY, fid, tid);
                break;
            case Constant.TYPE_FEEDBACK:
                setTitle("反馈");
                etSubject.setEnabled(false);
                etSubject.setText("Feedback:" + title);
                etContent.setHint("请输入您的反馈信息");
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
            //presenter.post(fid, content, title);
        } else {
            presenter.comment(tid, fid, pid, content);
        }
    }

    @Override
    public void postSuccess() {
        finish();
    }

    @Override
    public void postFailure(String msg) {

    }

    @Override
    public void checkPermissionSuccess(boolean hasPermission, String msg, boolean retry) {
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
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.show();
            }
        }
    }
}
