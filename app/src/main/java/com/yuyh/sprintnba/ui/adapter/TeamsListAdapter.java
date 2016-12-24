package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.player.Teams;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/24.
 */
public class TeamsListAdapter extends EasyLVAdapter<Teams.TeamsBean.Team> {

    private OnListItemClickListener listener;

    public TeamsListAdapter(List<Teams.TeamsBean.Team> mList, Context context, int... layoutIds) {
        super(context, mList, layoutIds);
    }


    public void setOnListItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void convert(EasyLVHolder viewHolder, final int position, final Teams.TeamsBean.Team team) {
        viewHolder.setText(R.id.tvTeamFullName, team.fullCnName);
        SimpleDraweeView iv = viewHolder.getView(R.id.ivTeamLogo);
        iv.setController(FrescoUtils.getController(team.logo, iv));
        viewHolder.getConvertView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (listener != null)
                    listener.onItemClick(view, position, team);
            }
        });
    }
}
