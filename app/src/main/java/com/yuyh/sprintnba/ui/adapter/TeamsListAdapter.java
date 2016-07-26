package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.player.Teams;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/24.
 */
public class TeamsListAdapter extends HelperAdapter<Teams.TeamsBean.Team> {

    private OnListItemClickListener listener;

    public TeamsListAdapter(List<Teams.TeamsBean.Team> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }


    public void setOnListItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void HelpConvert(HelperViewHolder viewHolder, final int position, final Teams.TeamsBean.Team team) {
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
