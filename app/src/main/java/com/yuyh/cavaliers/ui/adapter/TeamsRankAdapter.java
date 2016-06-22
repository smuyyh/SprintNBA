package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.player.TeamsRank;
import com.yuyh.cavaliers.recycleview.OnRecyclerViewItemClickListener;
import com.yuyh.library.view.image.CircleImageView;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class TeamsRankAdapter extends HelperAdapter<TeamsRank.TeamBean> {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public TeamsRankAdapter(List<TeamsRank.TeamBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final TeamsRank.TeamBean item) {
        if (item.type == 0) {
            Picasso.with(mContext).load(item.badge)
                    .placeholder(R.drawable.nba_default)
                    .error(R.drawable.nba_default)
                    .resize(80, 80)
                    .into((CircleImageView) viewHolder.getView(R.id.team_icon));
            viewHolder.setText(R.id.team_name, item.name)
                    .setText(R.id.win, item.win + "")
                    .setText(R.id.lose, item.lose + "")
                    .setText(R.id.win_percent, item.rate)
                    .setText(R.id.difference, item.difference);
        } else {
            viewHolder.setText(R.id.team_name, item.name);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public int checkLayoutIndex(TeamsRank.TeamBean item, int position) {
        if (item.type == 0)
            return 0;
        else
            return 1;
    }
}
