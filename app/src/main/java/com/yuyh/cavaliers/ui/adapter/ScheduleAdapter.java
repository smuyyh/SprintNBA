package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.match.Matchs;
import com.yuyh.cavaliers.support.NoDoubleClickListener;
import com.yuyh.cavaliers.support.OnListItemClickListener;
import com.yuyh.cavaliers.utils.FrescoUtils;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class ScheduleAdapter extends HelperAdapter<Matchs.MatchsDataBean.MatchesBean> {

    private OnListItemClickListener mOnItemClickListener = null;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public ScheduleAdapter(List<Matchs.MatchsDataBean.MatchesBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final Matchs.MatchsDataBean.MatchesBean item) {
        Matchs.MatchsDataBean.MatchesBean.MatchInfoBean matchInfo = item.matchInfo;

        SimpleDraweeView ivLeft = viewHolder.getView(R.id.ivLeftTeam);
        ivLeft.setController(FrescoUtils.getController(Uri.parse(matchInfo.leftBadge), ivLeft));
        SimpleDraweeView ivRight = viewHolder.getView(R.id.ivRightTeam);
        ivRight.setController(FrescoUtils.getController(Uri.parse(matchInfo.rightBadge), ivRight));

        String status;
        if (((matchInfo.quarter.contains("第4节") || matchInfo.quarter.contains("加时")) && !matchInfo.leftGoal.equals(matchInfo.rightGoal))
                && matchInfo.quarterTime.contains("00:00")) {
            status = "已结束";
        } else if (matchInfo.quarter.equals("") && matchInfo.quarterTime.equals("12:00")) {
            status = matchInfo.startTime;
        } else {
            status = matchInfo.quarter + " " + matchInfo.quarterTime;
        }
        String broadcasters = "";
        if (matchInfo.broadcasters != null) {
            for (String str : matchInfo.broadcasters) {
                broadcasters += str;
            }
        }
        viewHolder.setText(R.id.tvLeftTeam, matchInfo.leftName)
                .setText(R.id.tvRightTeam, matchInfo.rightName)
                .setText(R.id.tvMatchStatus, status)
                .setText(R.id.tvLeftTeamPoint, matchInfo.leftGoal)
                .setText(R.id.tvRightTeamPoint, matchInfo.rightGoal)
                .setText(R.id.tvMatchDesc, matchInfo.matchDesc)
                .setText(R.id.tvBroadcasters, broadcasters);

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
