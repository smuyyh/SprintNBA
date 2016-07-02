package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.match.MatchStat;
import com.yuyh.cavaliers.utils.FrescoUtils;
import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchDetailAdapter extends HelperAdapter<MatchStat.MatchStatInfo.StatsBean.MaxPlayers> {

    public MatchDetailAdapter(List<MatchStat.MatchStatInfo.StatsBean.MaxPlayers> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }

    @Override
    public void HelpConvert(HelperViewHolder viewHolder, int position, MatchStat.MatchStatInfo.StatsBean.MaxPlayers item) {
        viewHolder.setText(R.id.tvLeftPlayerName, item.leftPlayer.name)
                .setText(R.id.tvLeftPlayerType, item.leftPlayer.position + "  #" + item.rightPlayer.jerseyNum)
                .setText(R.id.tvRightPlayerName, item.rightPlayer.name)
                .setText(R.id.tvRightPlayerType, item.rightPlayer.position + "  #" + item.rightPlayer.jerseyNum)
                .setText(R.id.tvType, item.text);

        SimpleDraweeView ivLeft = viewHolder.getView(R.id.ivLeftPlayerIcon);
        ivLeft.setController(FrescoUtils.getController(Uri.parse(item.leftPlayer.icon), ivLeft));
        SimpleDraweeView ivRight = viewHolder.getView(R.id.ivRightPlayerIcon);
        ivRight.setController(FrescoUtils.getController(Uri.parse(item.rightPlayer.icon), ivRight));
    }
}
