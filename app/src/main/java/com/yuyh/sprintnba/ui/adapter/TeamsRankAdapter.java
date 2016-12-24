package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.player.TeamsRank;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.sprintnba.utils.ItemAnimHelper;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class TeamsRankAdapter extends EasyRVAdapter<TeamsRank.TeamBean> {

    private OnListItemClickListener mOnItemClickListener = null;
    private ItemAnimHelper helper = new ItemAnimHelper();

    public TeamsRankAdapter(List<TeamsRank.TeamBean> data, Context context, int... layoutId) {
        super(context, data, layoutId);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final TeamsRank.TeamBean item) {
        if (item.type == 0) {
            SimpleDraweeView iv = viewHolder.getView(R.id.team_icon);
            iv.setController(FrescoUtils.getController(item.badge, iv));

            viewHolder.setText(R.id.team_name, item.name)
                    .setText(R.id.win, item.win + "")
                    .setText(R.id.lose, item.lose + "")
                    .setText(R.id.win_percent, item.rate)
                    .setText(R.id.difference, item.difference);
        } else {
            viewHolder.setText(R.id.team_name, item.name);
        }

        viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (mOnItemClickListener != null && item.type == 0)
                    mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });

        helper.showItemAnim(viewHolder.getItemView(), position);
    }

    @Override
    public int getLayoutIndex(int position, TeamsRank.TeamBean item) {
        if (item.type == 0)
            return 0;
        else
            return 1;
    }
}
