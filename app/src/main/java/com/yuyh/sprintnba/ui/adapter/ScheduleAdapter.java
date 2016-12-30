package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.match.Matchs;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.sprintnba.utils.ItemAnimHelper;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class ScheduleAdapter extends EasyRVAdapter<Matchs.MatchsDataBean.MatchesBean> {

    private OnListItemClickListener mOnItemClickListener = null;
    private ItemAnimHelper helper = new ItemAnimHelper();

    public ScheduleAdapter(List<Matchs.MatchsDataBean.MatchesBean> data, Context context, int... layoutId) {
        super(context, data, layoutId);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final Matchs.MatchsDataBean.MatchesBean item) {
        Matchs.MatchsDataBean.MatchesBean.MatchInfoBean matchInfo = item.matchInfo;

        SimpleDraweeView ivLeft = viewHolder.getView(R.id.ivLeftTeam);
        ivLeft.setController(FrescoUtils.getController(matchInfo.leftBadge, ivLeft));
        SimpleDraweeView ivRight = viewHolder.getView(R.id.ivRightTeam);
        ivRight.setController(FrescoUtils.getController(matchInfo.rightBadge, ivRight));

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
                broadcasters += str + "/";
            }
        }
        if (broadcasters.length() > 1) {
            broadcasters = broadcasters.substring(0, broadcasters.length() - 1);
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

        helper.showItemAnim(viewHolder.getItemView(), position);
    }
}
