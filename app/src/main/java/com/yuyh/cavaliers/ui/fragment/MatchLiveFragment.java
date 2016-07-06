package com.yuyh.cavaliers.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.bean.match.LiveDetail;
import com.yuyh.cavaliers.support.OnLvScrollListener;
import com.yuyh.cavaliers.ui.adapter.MatchLiveAdapter;
import com.yuyh.cavaliers.ui.presenter.impl.MatchLivePresenter;
import com.yuyh.cavaliers.ui.view.MatchLiveView;
import com.yuyh.library.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/5.
 */
public class MatchLiveFragment extends BaseLazyFragment implements MatchLiveView {

    @InjectView(R.id.refresh)
    FrameLayout materialRefreshLayout;
    @InjectView(R.id.snlScrollView)
    ListView lvMatchLive;
    @InjectView(R.id.emptyView)
    View emptyView;

    private int mListViewHeight = 0;

    private List<LiveDetail.LiveDetailData.LiveContent> list = new ArrayList<>();
    private MatchLiveAdapter adapter;

    private MatchLivePresenter presenter;
    private String mid;

    public static MatchLiveFragment newInstance(String mid) {
        Bundle args = new Bundle();
        args.putString("mid", mid);
        MatchLiveFragment fragment = new MatchLiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_match_live);
        ButterKnife.inject(this, getContentView());
        initData();
    }

    private void initData() {
        showLoadingDialog();
        mid = getArguments().getString("mid");
        adapter = new MatchLiveAdapter(list, mActivity, R.layout.item_list_match_live);
        lvMatchLive.setAdapter(adapter);
        lvMatchLive.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    lvMatchLive.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    lvMatchLive.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mListViewHeight = lvMatchLive.getHeight();
                lvMatchLive.setOnScrollListener(new OnLvScrollListener(mListViewHeight) {
                    @Override
                    public void onBottom() {
                        ToastUtils.showSingleToast("到达底部");
                        presenter.getMoreContent();
                    }
                });
            }
        });
        presenter = new MatchLivePresenter(mActivity, this, mid);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void addList(List<LiveDetail.LiveDetailData.LiveContent> detail, boolean front) {
        if (front)
            list.addAll(0, detail);
        else
            list.addAll(detail);
        adapter.notifyDataSetChanged();
        hideLoadingDialog();
    }

    @Override
    public void showError(String message) {
        hideLoadingDialog();
        lvMatchLive.setEmptyView(emptyView);
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        presenter.shutDownTimerTask();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        presenter.shutDownTimerTask();
        presenter.initialized();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        presenter.shutDownTimerTask();
    }
}
