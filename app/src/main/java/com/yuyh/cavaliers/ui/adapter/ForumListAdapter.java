package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.forum.ForumsData;
import com.yuyh.cavaliers.support.OnListItemClickListener;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ForumListAdapter extends HelperAdapter<ForumsData.Forum> {

    private OnListItemClickListener listener;

    public ForumListAdapter(List<ForumsData.Forum> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }

    @Override
    protected void HelperBindData(HelperViewHolder viewHolder, final int position, final ForumsData.Forum item) {
        if (!item.fid.equals("0")) {
            viewHolder.setText(R.id.tvTeamFullName, item.name)
                    .setImageUrl(R.id.ivTeamLogo, item.logo);
            viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v, position, item);
                }
            });
        } else {
            viewHolder.setText(R.id.tvForumName, item.name);
        }
    }

    public void setOnListItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int checkLayoutIndex(ForumsData.Forum item, int position) {
        if (item.fid.equals("0"))
            return 1;
        else
            return 0;
    }
}
