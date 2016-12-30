package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.sprintnba.utils.FrescoUtils;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchLMaxPlayerdapter extends EasyLVAdapter<MatchStat.MaxPlayers> {

    public MatchLMaxPlayerdapter(List<MatchStat.MaxPlayers> mList, Context context, int... layoutIds) {
        super(context, mList, layoutIds);
    }

    @Override
    public void convert(EasyLVHolder viewHolder, int position, MatchStat.MaxPlayers item) {
        viewHolder.setText(R.id.tvLeftPlayerName, item.leftPlayer.name)
                .setText(R.id.tvLeftPlayerType, item.leftPlayer.position + "  #" + item.rightPlayer.jerseyNum)
                .setText(R.id.tvRightPlayerName, item.rightPlayer.name)
                .setText(R.id.tvRightPlayerType, item.rightPlayer.position + "  #" + item.rightPlayer.jerseyNum)
                .setText(R.id.tvType, item.text);

        SimpleDraweeView ivLeft = viewHolder.getView(R.id.ivLeftPlayerIcon);
        ivLeft.setController(FrescoUtils.getController(item.leftPlayer.icon, ivLeft));
        SimpleDraweeView ivRight = viewHolder.getView(R.id.ivRightPlayerIcon);
        ivRight.setController(FrescoUtils.getController(item.rightPlayer.icon, ivRight));
    }
}
