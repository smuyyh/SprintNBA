package com.yuyh.sprintnba.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.sprintnba.BuildConfig;
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

    public static void start(Context context) {
        context.startActivity(new Intent(context, MatchVideoLiveListActivity.class));
    }

    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.initialized();
            }
        });

    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showSingleToast(msg);
        hideLoadingDialog();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLiveList(final List<VideoLiveInfo> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showSourceList(final List<VideoLiveSource> list) {

        if (list != null && list.size() == 1) {
            BaseWebActivity.start(mContext, list.get(0).link, list.get(0).name, false, false);
            return;
        } else if (list == null || list.isEmpty()) {
            return;
        }


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
                        if (links[which].startsWith("/")) {
                            links[which] = BuildConfig.TMIAAO_SERVER + links[which];
                        }
                        BaseWebActivity.start(mContext, links[which], names[which], false, false);
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
