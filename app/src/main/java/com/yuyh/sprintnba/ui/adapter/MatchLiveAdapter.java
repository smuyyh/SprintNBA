package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.match.LiveDetail;
import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchLiveAdapter extends HelperAdapter<LiveDetail.LiveDetailData.LiveContent> {

    public MatchLiveAdapter(List<LiveDetail.LiveDetailData.LiveContent> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }

    @Override
    public void HelpConvert(final HelperViewHolder viewHolder, int position, LiveDetail.LiveDetailData.LiveContent item) {
        viewHolder.setText(R.id.tvLiveTime, item.time)
                .setText(R.id.tvLiveTeam, item.teamName)
                .setText(R.id.tvLiveScore, item.leftGoal + ":" + item.rightGoal)
                .setText(R.id.tvLiveContent, item.content);

        final View itemView = viewHolder.getConvertView();
        ViewTreeObserver vto = itemView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 务必移除监听，会多次调用
                if (Build.VERSION.SDK_INT < 16) {
                    itemView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                View line = viewHolder.getView(R.id.viewLine);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
                params.height = itemView.getHeight();
                line.setLayoutParams(params);
            }
        });
    }
}
