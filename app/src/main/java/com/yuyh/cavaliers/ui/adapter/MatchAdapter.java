package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.match.Matchs;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class MatchAdapter extends HelperAdapter<Matchs.MatchsDataBean.MatchesBean> {

    private OnListItemClickListener mOnItemClickListener = null;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public MatchAdapter(List<Matchs.MatchsDataBean.MatchesBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final Matchs.MatchsDataBean.MatchesBean item) {
        Matchs.MatchsDataBean.MatchesBean.MatchInfoBean matchInfo = item.matchInfo;
        String status;
        if (matchInfo.quarter.equals("第4节") && matchInfo.quarter.equals("00:00"))
            status = "已结束";
        else if (matchInfo.quarter.equals("") && matchInfo.quarter.equals("12:00"))
            status = matchInfo.startTime;
        else
            status = matchInfo.quarter + " " + matchInfo.quarter;
        viewHolder.setText(R.id.tvLeftTeam, matchInfo.leftName)
                .setText(R.id.tvRightTeam, matchInfo.rightName)
                .setText(R.id.tvMatchStatus, status)
                .setText(R.id.tvLeftTeamPoint, matchInfo.leftGoal)
                .setText(R.id.tvRightTeamPoint, matchInfo.rightGoal)
                .setText(R.id.tvMatchDesc, matchInfo.matchDesc)
                .setImageUrl(R.id.ivLeftTeam, matchInfo.leftBadge)
                .setImageUrl(R.id.ivRightTeam, matchInfo.rightBadge);

        viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
