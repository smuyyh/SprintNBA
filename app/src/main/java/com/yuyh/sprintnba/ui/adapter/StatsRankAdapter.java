package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.player.StatsRank;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.sprintnba.utils.ItemAnimHelper;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class StatsRankAdapter extends EasyRVAdapter<StatsRank.RankItem> {

    private OnListItemClickListener mOnItemClickListener = null;
    private ItemAnimHelper helper = new ItemAnimHelper();

    public StatsRankAdapter(List<StatsRank.RankItem> data, Context context, int... layoutId) {
        super(context, data, layoutId);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final StatsRank.RankItem item) {
        viewHolder.setText(R.id.tvRank, "NO." + item.serial + "")
                .setText(R.id.tvData, item.value)
                .setText(R.id.tvName, item.playerName);
        SimpleDraweeView ivHead = viewHolder.getView(R.id.ivHead);
        ivHead.setController(FrescoUtils.getController(item.playerIcon, ivHead));
        SimpleDraweeView ivTeam = viewHolder.getView(R.id.ivTeam);
        ivTeam.setController(FrescoUtils.getController(item.teamIcon, ivTeam));
        viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
        helper.showItemAnim(viewHolder.getItemView(), position);
    }
}
