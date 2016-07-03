package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;

import com.yuyh.cavaliers.http.bean.match.MatchStat;
import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.List;

/**
 * 比赛数据->比分
 *
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchDataPointAdapter extends HelperAdapter<MatchStat.MatchStatInfo.StatsBean.Goals> {

    public MatchDataPointAdapter(List<MatchStat.MatchStatInfo.StatsBean.Goals> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }

    @Override
    public void HelpConvert(HelperViewHolder viewHolder, int position, MatchStat.MatchStatInfo.StatsBean.Goals item) {
    }
}
