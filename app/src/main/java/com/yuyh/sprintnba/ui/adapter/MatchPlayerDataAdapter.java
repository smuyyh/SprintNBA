package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.library.utils.DimenUtils;
import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/5.
 */
public class MatchPlayerDataAdapter extends HelperAdapter<MatchStat.MatchStatInfo.StatsBean.PlayerStats> {

    private LayoutInflater inflater;
    private LinearLayout.LayoutParams params;

    public MatchPlayerDataAdapter(List<MatchStat.MatchStatInfo.StatsBean.PlayerStats> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
        inflater = LayoutInflater.from(context);
        params = new LinearLayout.LayoutParams(DimenUtils.dpToPxInt(40), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void HelpConvert(HelperViewHolder viewHolder, int position, MatchStat.MatchStatInfo.StatsBean.PlayerStats item) {
        LinearLayout llPlayerDataItem = viewHolder.getView(R.id.llPlayerDataItem);
        if (item.head != null && !item.head.isEmpty()) {
            List<String> head = item.head;
            viewHolder.setText(R.id.tvMatchPlayer, head.get(0));
            viewHolder.setInVisible(R.id.ivIsFirst);
            for (int i = 2; i < head.size(); i++) {
                TextView tv = (TextView) inflater.inflate(R.layout.tab_match_point, null);
                tv.setText(head.get(i));
                tv.setLayoutParams(params);
                llPlayerDataItem.addView(tv);
            }
        } else if (item.row != null) {
            List<String> row = item.row;
            viewHolder.setText(R.id.tvMatchPlayer, row.get(0));
            if (row.get(1).equals("是"))
                viewHolder.setVisible(R.id.ivIsFirst, true);
            for (int i = 2; i < row.size(); i++) {
                TextView tv = (TextView) inflater.inflate(R.layout.tab_match_point, null);
                tv.setText(row.get(i));
                tv.setLayoutParams(params);
                llPlayerDataItem.addView(tv);
            }
        }
    }
}
