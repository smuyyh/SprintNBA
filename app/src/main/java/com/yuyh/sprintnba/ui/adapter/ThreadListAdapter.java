package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.forum.ThreadListData;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class ThreadListAdapter extends HelperAdapter<ThreadListData.ThreadInfo> {

    private OnListItemClickListener listener;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public ThreadListAdapter(List<ThreadListData.ThreadInfo> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final ThreadListData.ThreadInfo item) {

        TextView tv = viewHolder.getView(R.id.tvTitle);
        tv.setText(Html.fromHtml(item.title));
        viewHolder.setText(R.id.tvReply, item.replies)
                .setText(R.id.tvSingleTime, item.forum == null ? item.time : item.forum.name)
                .setVisible(R.id.grid, false);
        if (item.lightReply > 0) {
            viewHolder.setVisible(R.id.tvLight, true)
                    .setText(R.id.tvLight, item.lightReply + "");
        } else {
            viewHolder.setVisible(R.id.tvLight, false);
        }
        viewHolder.setVisible(R.id.tvSingleTime, true);
        viewHolder.setVisible(R.id.tvSummary, false);
        viewHolder.setVisible(R.id.grid, false);
        showItemAnim(viewHolder.getItemView(), position);
        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
    }

    public void setOnItemClickListener(OnListItemClickListener listener) {
        this.listener = listener;
    }

    private int mLastPosition = -1;

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }
}
