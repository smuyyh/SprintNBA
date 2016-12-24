package com.yuyh.sprintnba.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.bean.video.VideoLiveInfo;
import com.yuyh.sprintnba.http.bean.video.VideoLiveSource;
import com.yuyh.sprintnba.ui.adapter.VideoLiveAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.MatchVideoLivePresenter;
import com.yuyh.sprintnba.ui.view.MatchVideoLiveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 2016/12/23.
 */
public class MatchVideoLiveListActivity extends BaseSwipeBackCompatActivity
        implements MatchVideoLiveView, AdapterView.OnItemClickListener {

    @InjectView(R.id.lvLive)
    ListView lvLive;

    private MatchVideoLivePresenter presenter;

    private List<VideoLiveInfo> mList = new ArrayList<>();
    private VideoLiveAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_match_live_list;
    }

    @Override
    protected void initViewsAndEvents() {

        setTitle("直播列表");

        mAdapter = new VideoLiveAdapter(this, mList);
        lvLive.setAdapter(mAdapter);
        lvLive.setOnItemClickListener(this);

        presenter = new MatchVideoLivePresenter(this, this);
        presenter.initialized();

    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showSingleToast(msg);
    }

    @Override
    public void showLiveList(final List<VideoLiveInfo> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
    }

    @Override
    public void showSourceList(final List<VideoLiveSource> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        final String[] links = new String[list.size()];
        final String[] names = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            links[i] = list.get(i).link;
            names[i] = list.get(i).name;
        }

        builder.setTitle("请选择直播源")
                .setItems(names, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(mContext, BaseWebActivity.class);
                        intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, names[which]);
                        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, links[which]);
                        intent.putExtra(BaseWebActivity.BUNDLE_KEY_SHOW_BOTTOM_BAR, false);
                        startActivity(intent);

                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        VideoLiveInfo info = (VideoLiveInfo) mAdapter.getItem(position);
        presenter.getSourceList(info.link);
    }
}
