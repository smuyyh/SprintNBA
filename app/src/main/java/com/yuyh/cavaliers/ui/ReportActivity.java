package com.yuyh.cavaliers.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.presenter.ReportPresenter;
import com.yuyh.cavaliers.ui.adapter.ReportAdapter;
import com.yuyh.cavaliers.ui.view.ReportView;
import com.yuyh.library.view.list.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class ReportActivity extends BaseSwipeBackCompatActivity implements ReportView {


    @InjectView(R.id.lvTypes)
    NoScrollListView lvTypes;
    @InjectView(R.id.etContent)
    EditText etContent;

    private String tid;
    private String pid;

    private ReportAdapter adapter;
    private List<String> list = new ArrayList<>();

    private int type = 1;
    private ReportPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_report;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("举报");
        pid = getIntent().getStringExtra("pid");
        tid = getIntent().getStringExtra("tid");
        adapter = new ReportAdapter(list, this, R.layout.item_list_report);
        lvTypes.setAdapter(adapter);
        lvTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
                type = position + 1;
            }
        });

        presenter = new ReportPresenter(this, this);
        presenter.initialized();
    }

    @OnClick(R.id.btCommit)
    void btCommitClick() {
        presenter.submitReports(tid, pid, String.valueOf(type), etContent.getText().toString());
    }

    @Override
    public void showType(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void reportSuccess() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }
}
