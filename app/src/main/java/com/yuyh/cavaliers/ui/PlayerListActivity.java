package com.yuyh.cavaliers.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.base.BaseWebActivity;
import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.presenter.impl.PlayersListPresenterImpl;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.ui.adapter.PlayersListAdapter;
import com.yuyh.cavaliers.ui.view.PlayersView;
import com.yuyh.library.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class PlayerListActivity extends BaseSwipeBackCompatActivity implements PlayersView, OnListItemClickListener<Players.Player> {

    @InjectView(R.id.lvAllTeam)
    ListView lvAllTeam;

    private PlayersListAdapter adapter;
    private List<Players.Player> list = new ArrayList<>();
    private Presenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_all_team;
    }

    @Override
    protected void initViewsAndEvents() {
        showLoadingDialog();
        setTitle("球员列表");
        adapter = new PlayersListAdapter(list, this, R.layout.list_item_teams);
        adapter.setOnListItemClickListener(this);
        lvAllTeam.setAdapter(adapter);
        presenter = new PlayersListPresenterImpl(this, this);
        presenter.initialized();
    }

    @Override
    public void showAllPlayers(List<Players.Player> data) {
        list.clear();
        list.addAll(data);
        adapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1000);
    }

    @Override
    public void failure(String msg) {
        ToastUtils.showSingleLongToast("加载数据失败");
    }

    @Override
    public void onItemClick(View view, int position, Players.Player data) {
        Intent intent = new Intent(this, BaseWebActivity.class);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, data.enName);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, data.detailUrl);
        startActivity(intent);
    }
}
