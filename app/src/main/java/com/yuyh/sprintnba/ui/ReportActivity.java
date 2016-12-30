package com.yuyh.sprintnba.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.yuyh.library.view.list.NoScrollListView;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.ui.adapter.ReportAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.ReportPresenterImpl;
import com.yuyh.sprintnba.ui.view.ReportView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class ReportActivity extends BaseSwipeBackCompatActivity implements ReportView {

    public static final String INTENT_PID = "pid";
    public static final String INTENT_TID = "tid";

    public static void start(Context context, String pid, String tid) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra(ReportActivity.INTENT_TID, tid);
        intent.putExtra(ReportActivity.INTENT_PID, pid);
        context.startActivity(intent);
    }

    @InjectView(R.id.lvTypes)
    NoScrollListView lvTypes;
    @InjectView(R.id.etContent)
    EditText etContent;

    private String tid;
    private String pid;

    private ReportAdapter adapter;
    private List<String> list = new ArrayList<>();

    private int type = 1;
    private ReportPresenterImpl presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_report;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("举报");
        pid = getIntent().getStringExtra(INTENT_PID);
        tid = getIntent().getStringExtra(INTENT_TID);
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

        presenter = new ReportPresenterImpl(this, this);
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
