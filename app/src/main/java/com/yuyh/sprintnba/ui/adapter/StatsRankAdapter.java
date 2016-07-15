package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.player.StatsRank;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class StatsRankAdapter extends HelperAdapter<StatsRank.RankItem> {

    private OnListItemClickListener mOnItemClickListener = null;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public StatsRankAdapter(List<StatsRank.RankItem> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final StatsRank.RankItem item) {
        viewHolder.setText(R.id.tvRank, "NO." + item.serial + "")
                .setText(R.id.tvData, item.value)
                .setText(R.id.tvName, item.playerName);
        SimpleDraweeView ivHead = viewHolder.getView(R.id.ivHead);
        ivHead.setController(FrescoUtils.getController(Uri.parse(item.playerIcon), ivHead));
        SimpleDraweeView ivTeam = viewHolder.getView(R.id.ivTeam);
        ivTeam.setController(FrescoUtils.getController(Uri.parse(item.teamIcon), ivTeam));
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
