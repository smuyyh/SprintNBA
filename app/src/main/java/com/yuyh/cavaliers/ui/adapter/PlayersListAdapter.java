package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/24.
 */
public class PlayersListAdapter extends HelperAdapter<Players.Player> {

    private OnListItemClickListener listener;

    public PlayersListAdapter(List<Players.Player> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }


    public void setOnListItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void HelpConvert(HelperViewHolder viewHolder, final int position, final Players.Player player) {
        viewHolder.setImageUrl(R.id.ivTeamLogo, player.teamLogo)
                .setText(R.id.tvTeamFullName, player.cnName);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(v, position, player);
            }
        });
    }
}
