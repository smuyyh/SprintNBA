package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.match.LiveDetail;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * recycleView 的adapter  暂未用到
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchLiveAdapter1 extends HelperAdapter<LiveDetail.LiveDetailData.LiveContent> {

    public MatchLiveAdapter1(List<LiveDetail.LiveDetailData.LiveContent> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, int position, LiveDetail.LiveDetailData.LiveContent item) {
        viewHolder.setText(R.id.tvLiveTime, item.time)
                .setText(R.id.tvLiveTeam, item.teamName)
                .setText(R.id.tvLiveScore, item.leftGoal + ":" + item.rightGoal)
                .setText(R.id.tvLiveContent, item.content);

        final View itemView = viewHolder.getItemView();


        ViewTreeObserver vto = itemView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View line = viewHolder.getView(R.id.viewLine);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
                params.height = itemView.getHeight();
                line.setLayoutParams(params);
            }
        });
    }
}
