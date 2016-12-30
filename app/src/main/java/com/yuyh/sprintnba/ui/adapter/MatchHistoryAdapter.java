package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.match.MatchStat;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/4.
 */
public class MatchHistoryAdapter extends EasyLVAdapter<MatchStat.VS> {

    private int primaryColor;
    private int secondaryColor;

    public MatchHistoryAdapter(List<MatchStat.VS> mList, Context context, int... layoutIds) {
        super(context, mList, layoutIds);
        primaryColor = ContextCompat.getColor(context, R.color.primary_text);
        secondaryColor = ContextCompat.getColor(context, R.color.secondary_text);
    }

    @Override
    public void convert(EasyLVHolder viewHolder, int position, MatchStat.VS item) {
        viewHolder.setText(R.id.tvMatchRecentLeft, item.leftName)
                .setText(R.id.tvMatchRecentRight, item.rightName)
                .setText(R.id.tvMatchRecentTime, item.startTime + "  " + item.matchDesc);
        viewHolder.setText(R.id.tvMatchRecentLeftPoint, item.leftGoal + "")
                .setText(R.id.tvMatchRecentRightPoint, item.rightGoal + "");
        if (item.leftGoal > item.rightGoal) {
            viewHolder.setTextColor(R.id.tvMatchRecentLeft, primaryColor);
            viewHolder.setTextColor(R.id.tvMatchRecentLeftPoint, primaryColor);
            viewHolder.setTextColor(R.id.tvMatchRecentRight, secondaryColor);
            viewHolder.setTextColor(R.id.tvMatchRecentRightPoint, secondaryColor);
        } else {
            viewHolder.setTextColor(R.id.tvMatchRecentLeft, secondaryColor);
            viewHolder.setTextColor(R.id.tvMatchRecentLeftPoint, secondaryColor);
            viewHolder.setTextColor(R.id.tvMatchRecentRight, primaryColor);
            viewHolder.setTextColor(R.id.tvMatchRecentRightPoint, primaryColor);
        }
    }
}
