package com.yuyh.sprintnba.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.yuyh.library.utils.DimenUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.event.RefreshCompleteEvent;
import com.yuyh.sprintnba.event.RefreshEvent;
import com.yuyh.sprintnba.http.bean.video.MatchVideo;
import com.yuyh.sprintnba.support.SpaceItemDecoration;
import com.yuyh.sprintnba.support.SupportRecyclerView;
import com.yuyh.sprintnba.ui.adapter.MatchVideoAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.MatchVideoPresenter;
import com.yuyh.sprintnba.ui.view.MatchVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 2016/12/30.
 */
public class MatchVideoFragment extends BaseLazyFragment implements MatchVideoView {

    public static final String BUNDLE_MID = "mid";

    public static Fragment newInstance(String mid) {
        MatchVideoFragment fragment = new MatchVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MID, mid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @InjectView(R.id.snlScrollView)
    SupportRecyclerView recyclerView;

    private MatchVideoPresenter mPresenter;

    private MatchVideoAdapter mAdapter;
    private List<MatchVideo.VideoBean> mList = new ArrayList<>();

    private boolean isVisibleToUser; // 是否可见。可见才进行刷新

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_match_video);
        EventBus.getDefault().register(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dpToPxInt(5)));

        mPresenter = new MatchVideoPresenter(this, getArguments().getString(BUNDLE_MID));
        mPresenter.initialized();
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
        recyclerView.setEmptyView(findViewById(R.id.emptyView));
    }

    @Override
    public void showMatchVideo(List<MatchVideo.VideoBean> list) {
        if (mAdapter == null) {
            mAdapter = new MatchVideoAdapter(mActivity, mList);
            recyclerView.setAdapter(mAdapter);
        }

        mAdapter.clear();
        mAdapter.addAll(list);

        EventBus.getDefault().post(new RefreshCompleteEvent());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && mActivity != null) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        if (isVisibleToUser) {
            mPresenter.initialized();
        }
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        EventBus.getDefault().unregister(this);
    }
}
