package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.bean.match.MatchStat;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/5.
 */
public class MatchPlayerDataAdapter extends EasyLVAdapter<MatchStat.PlayerStats> {

    private LayoutInflater inflater;
    private LinearLayout.LayoutParams params;

    public MatchPlayerDataAdapter(List<MatchStat.PlayerStats> mList, Context context) {
        super(context, mList, R.layout.item_list_match_player);
        inflater = LayoutInflater.from(context);
        params = new LinearLayout.LayoutParams(DimenUtils.dpToPxInt(40), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void convert(EasyLVHolder viewHolder, int position, final MatchStat.PlayerStats item) {
        LinearLayout llPlayerDataItem = viewHolder.getView(R.id.llPlayerDataItem);

        if (llPlayerDataItem.getChildCount() > 2) {
            llPlayerDataItem.removeViews(2, llPlayerDataItem.getChildCount()-2);
        }

        if (item.head != null && !item.head.isEmpty()) {
            List<String> head = item.head;
            viewHolder.setText(R.id.tvMatchPlayer, head.get(0));
            viewHolder.setVisible(R.id.ivIsFirst, View.INVISIBLE);
            for (int i = 2; i < head.size(); i++) {
                TextView tv = (TextView) inflater.inflate(R.layout.tab_match_point, null);
                tv.setText(head.get(i));
                tv.setLayoutParams(params);
                llPlayerDataItem.addView(tv);
            }
        } else if (item.row != null) {
            List<String> row = item.row;
            viewHolder.setText(R.id.tvMatchPlayer, row.get(0));
            if ("是".equals(row.get(1)))
                viewHolder.setVisible(R.id.ivIsFirst, true);
            for (int i = 2; i < row.size(); i++) {
                TextView tv = (TextView) inflater.inflate(R.layout.tab_match_point, null);
                tv.setText(row.get(i));
                tv.setLayoutParams(params);
                llPlayerDataItem.addView(tv);
            }

            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseWebActivity.start(mContext, item.detailUrl, item.row.get(0), true, true);
                }
            });
        }
    }
}
