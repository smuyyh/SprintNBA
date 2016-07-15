package com.yuyh.sprintnba.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.bean.player.Players;
import com.yuyh.sprintnba.ui.adapter.PlayersAdapter;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.presenter.impl.PlayersListPresenterImpl;
import com.yuyh.sprintnba.ui.view.PlayersView;
import com.yuyh.library.utils.toast.ToastUtils;
import com.yuyh.library.view.list.indexablelistview.IndexEntity;
import com.yuyh.library.view.list.indexablelistview.IndexableStickyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class PlayerListActivity extends BaseSwipeBackCompatActivity implements PlayersView, IndexableStickyListView.OnItemContentClickListener {

    @InjectView(R.id.indexListView)
    IndexableStickyListView lvAllTeam;
    @InjectView(R.id.searchview)
    SearchView mSearchView;

    private PlayersAdapter playersAdapter;
    private List<Players.Player> list = new ArrayList<>();
    private Presenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_all_players;
    }

    @Override
    protected void initViewsAndEvents() {
        showLoadingDialog();
        setTitle("球员列表");
        playersAdapter = new PlayersAdapter(this);
        lvAllTeam.setAdapter(playersAdapter);
        lvAllTeam.setOnItemContentClickListener(this);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 委托处理搜索
                lvAllTeam.searchTextChange(newText);
                return true;
            }
        });
        presenter = new PlayersListPresenterImpl(this, this);
        presenter.initialized();
    }

    @Override
    public void showAllPlayers(List<Players.Player> data) {
        list.clear();
        list.addAll(data);

        //adapter.notifyDataSetChanged();
        lvAllTeam.bindDatas(list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1300);
    }

    @Override
    public void failure(String msg) {
        ToastUtils.showSingleLongToast("加载数据失败");
    }

    @Override
    public void onItemClick(View v, IndexEntity indexEntity) {
        Players.Player data = (Players.Player) indexEntity;
        Intent intent = new Intent(this, BaseWebActivity.class);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, data.enName);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, data.detailUrl);
        startActivity(intent);
    }
}
