package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.forum.ThreadListData;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ThreadListAdapter extends HelperAdapter<ThreadListData.ThreadInfo> {


    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public ThreadListAdapter(List<ThreadListData.ThreadInfo> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(HelperViewHolder viewHolder, int position, ThreadListData.ThreadInfo item) {

        viewHolder.setText(R.id.tvTitle, item.title)
                .setText(R.id.tvReply, item.replies)
                .setText(R.id.tvSingleTime, item.forum == null ? item.time : item.forum.name)
                .setVisible(R.id.grid, false);
        if (item.lightReply > 0) {
            viewHolder.setVisible(R.id.tvLight, true)
                    .setText(R.id.tvLight, item.lightReply + "");
        } else {
            viewHolder.setVisible(R.id.tvLight, false);
        }
    }
}
