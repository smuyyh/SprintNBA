package com.yuyh.cavaliers.ui.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.http.bean.match.LiveDetail;
import com.yuyh.cavaliers.ui.adapter.MatchLiveAdapter;
import com.yuyh.cavaliers.ui.presenter.impl.MatchLivePresenter;
import com.yuyh.cavaliers.ui.view.MatchLiveView;

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
    MaterialRefreshLayout materialRefreshLayout;
    @InjectView(R.id.lvMatchLive)
    ListView lvMatchLive;

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
        setContentView(R.layout.fragment_game_live);
        ButterKnife.inject(this, getContentView());
        initData();
    }

    private void initData() {
        adapter = new MatchLiveAdapter(list, mActivity, R.layout.item_list_match_live);
        lvMatchLive.setAdapter(adapter);
        mid = getArguments().getString("mid");
        presenter = new MatchLivePresenter(mActivity, this);
        presenter.getLiveContent(mid);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void addList(List<LiveDetail.LiveDetailData.LiveContent> detail) {
        list.addAll(0, detail);
        adapter.notifyDataSetChanged();
    }
}
