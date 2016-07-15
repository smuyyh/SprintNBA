package com.yuyh.sprintnba.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.bean.player.Teams;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.presenter.impl.TeamsListPresenterImpl;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.ui.adapter.TeamsListAdapter;
import com.yuyh.sprintnba.ui.view.TeamsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class TeamsListActivity extends BaseSwipeBackCompatActivity implements TeamsView, OnListItemClickListener<Teams.TeamsBean.Team> {

    @InjectView(R.id.lvAllTeam)
    ListView lvAllTeam;

    private TeamsListAdapter adapter;
    private List<Teams.TeamsBean.Team> list = new ArrayList<>();
    private Presenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_all_team;
    }

    @Override
    protected void initViewsAndEvents() {
        showLoadingDialog();
        setTitle("球队列表");
        adapter = new TeamsListAdapter(list, this, R.layout.item_list_teams);
        adapter.setOnListItemClickListener(this);
        lvAllTeam.setAdapter(adapter);
        presenter = new TeamsListPresenterImpl(this, this);
        presenter.initialized();
    }

    @Override
    public void showAllTeams(Teams.TeamsBean bean) {
        list.clear();
        list.addAll(bean.east);
        list.addAll(bean.west);
        adapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
            }
        }, 1000);
    }

    @Override
    public void onItemClick(View view, int position, Teams.TeamsBean.Team data) {
        Intent intent = new Intent(this, BaseWebActivity.class);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_TITLE, data.fullCnName);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, data.detailUrl);
        startActivity(intent);
    }
}
